package com.zhibo.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class WriteJsonUtil {

	
	/**
	 * 输出字符串
	 * 
	 * @param message
	 * @throws Exception
	 */
	public static void writeJson(HttpServletResponse response, Object obj) throws Exception {
		response.setContentType("text/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache"); //取消浏览器缓存
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.flush();
		out.close();
	}
}
