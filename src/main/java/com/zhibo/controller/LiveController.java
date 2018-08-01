package com.zhibo.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.tls.tls_sigature.tls_sigature;
import com.tls.tls_sigature.tls_sigature.GenTLSSignatureResult;
import com.zhibo.model.FuUser;
import com.zhibo.model.SysConfig;
import com.zhibo.service.SysConfigService;
import com.zhibo.util.Property;
import com.zhibo.util.ReplaceUtil;
import com.zhibo.util.WebUtil;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

@Controller
@RequestMapping("/live")
public class LiveController {
	private static final Log logger = LogFactory.getLog(LiveController.class);

	@Resource
	private SysConfigService sysConfigService;

	/**
	 * pc端首页
	 */
	@RequestMapping(value = "/index", produces = "text/html;charset=UTF-8")
	public String index(HttpServletRequest request, ModelMap modelMap) {
		String returnJsp = "";
		try {
			FuUser fuUser = (FuUser) request.getSession().getAttribute("fuUser");
			String accountMode = Property.getProperty("IM_accountMode");// 账号模式
			String sdkAppID = Property.getProperty("IM_sdkAppID");// 应用ID
			String accountType = Property.getProperty("IM_accountType");// 账户类型
			String avChatRoomId = Property.getProperty("IM_avChatRoomId");// 房间群ID
			String identifier = "152****3580";// (fuUser.getNickName() == null ? fuUser.getPhone().substring(0, 4) + "***" + fuUser.getPhone().substring(7, 11) : fuUser.getNickName());// 当前用户账号
			String identifierNick = "qyq";// fuUser.getNickName();// 当前用户昵称
			GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(Long.valueOf(sdkAppID), identifier, Property.getProperty("IM_privStr"));
			String userSig = result.urlSig;// 当前用户签名
			String headurl = "";// fuUser.getUserAvatar();// 当前用户默认头像
			modelMap.put("accountMode", accountMode);
			modelMap.put("sdkAppID", sdkAppID);
			modelMap.put("accountType", accountType);
			modelMap.put("avChatRoomId", avChatRoomId);
			modelMap.put("identifier", identifier);
			modelMap.put("identifierNick", identifierNick);
			modelMap.put("userSig", userSig);
			modelMap.put("headurl", headurl);

			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			OperatingSystem os = userAgent.getOperatingSystem();
			String osType = os.getName();
			logger.info("osType====>" + osType);
			if (osType.contains("Android") || osType.contains("iPhone") || osType.contains("iPad")) {// 移动端
				returnJsp = "live/wap";
			} else {// PC端
				SysConfig sysConfig = sysConfigService.getByCode("004");
				modelMap.put("qidaIntegral", sysConfig.getValue().intValue());
				SysConfig isPulish = sysConfigService.getByCode("005");
				modelMap.put("isPulish", isPulish.getValue().intValue());
				returnJsp = "live/index";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnJsp;
	}

	@RequestMapping(value = "/setCookie", produces = "text/html;charset=UTF-8")
	public String setCookie(HttpServletRequest request, HttpServletResponse response) {
		try {
			Cookie ticket = new Cookie("token_name", request.getParameter("ticket"));
			ticket.setPath("/");
			ticket.setMaxAge(Integer.parseInt(request.getParameter("expiry")));
			response.addCookie(ticket);

			String gotoURL = request.getParameter("gotoURL");
			if (gotoURL != null) {
				response.sendRedirect(gotoURL);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "/logout", produces = "text/html;charset=UTF-8")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			WebUtil.addCookie(response, "user_token", "", 1);
			WebUtil.addCookie(response, "token_name", "", 0);
			request.getSession().removeAttribute("fuUser");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/live/index";
	}

	/**
	 * 过滤脏字
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkText", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkText(String content) {
		try {
			// 过滤脏词
			if (content != null && content != "") {
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
				String[] keys = (String[]) webApplicationContext.getServletContext().getAttribute("keys");
				content = ReplaceUtil.replaceCheck(keys, content);
				return content;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
