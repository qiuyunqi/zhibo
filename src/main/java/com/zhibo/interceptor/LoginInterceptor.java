package com.zhibo.interceptor;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhibo.model.FuUser;
import com.zhibo.service.FuUserService;
import com.zhibo.util.Property;
import com.zhibo.util.WebUtil;

public class LoginInterceptor implements HandlerInterceptor {
	@Resource
	private FuUserService fuUserService;

	public static String COOKIE_NAME = "token_name";
	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 完成页面的render后调用
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {

	}

	/**
	 * 在调用controller具体方法后拦截
	 */

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在调用controller具体方法前调用
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUrl = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = request.getContextPath();
		String privUrl = requestUrl.substring(contextPath.length());
		if (excludeUrls != null && excludeUrls.contains(privUrl)) {
			return true;
		}

		String gotoURL = request.getParameter("gotoURL");
		if (requestUrl.equals(path + "/live/logout")) {
			gotoURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/live/index";
		}
		if (gotoURL == null) {
			gotoURL = request.getRequestURL().toString();
		}

		String URL = Property.getProperty("SSO_SERVICE") + "?action=preLogin&setCookieURL=" + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/live/setCookie&gotoURL=" + gotoURL;

		Cookie ticket = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(COOKIE_NAME)) {
					ticket = cookie;
					break;
				}
			}

		if (requestUrl.equals(path + "/live/logout")) {
			request.getSession().removeAttribute("fuUser");
			WebUtil.addCookie(response, COOKIE_NAME, "", 0);
			return doLogout(request, response, ticket, URL);
		}

		FuUser fuUser = (FuUser) request.getSession().getAttribute("fuUser");
		if (fuUser != null) {
			return true;
		}

		if (requestUrl.equals(path + "/live/setCookie")) {
			return true;
		} else if (ticket != null) {
			return authCookie(request, response, ticket, URL);
		} else {
			response.sendRedirect(URL);
			return false;
		}
	}

	private boolean doLogout(HttpServletRequest request, HttpServletResponse response, Cookie ticket, String url) throws JSONException, IOException {
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new NameValuePair("action", "logout");
		params[1] = new NameValuePair(COOKIE_NAME, ticket.getValue());
		try {
			post(request, response, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.sendRedirect(url);
		}
		return false;
	}

	private boolean authCookie(HttpServletRequest request, HttpServletResponse response, Cookie ticket, String url) throws IOException {
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new NameValuePair("action", "authTicket");
		params[1] = new NameValuePair(COOKIE_NAME, ticket.getValue());
		try {
			JSONObject result = post(request, response, params);
			if (result.getBoolean("error")) {
				response.sendRedirect(url);
				return false;
			} else {
				FuUser fuUser = fuUserService.get((long)(result.getInt("userId")));
				request.getSession().setAttribute("fuUser", fuUser);
				return true;
			}
		} catch (JSONException e) {
			response.setCharacterEncoding("UTF-8");
			response.sendRedirect(url);
			throw new RuntimeException(e);
		}

	}

	private JSONObject post(HttpServletRequest request, HttpServletResponse response, NameValuePair[] params) throws HttpException, IOException, JSONException {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(Property.getProperty("SSO_SERVICE"));
		postMethod.addParameters(params);
		switch (httpClient.executeMethod(postMethod)) {
		case HttpStatus.SC_OK:
			return new JSONObject(postMethod.getResponseBodyAsString());
		default:
			// 其它处理
			return null;
		}
	}

}
