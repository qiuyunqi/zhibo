package com.zhibo.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;

import com.tls.tls_sigature.tls_sigature;
import com.tls.tls_sigature.tls_sigature.GenTLSSignatureResult;

public class HttpsClient {
	public static final String privStr = "-----BEGIN PRIVATE KEY-----\n" + "MIGEAgEAMBAGByqGSM49AgEGBSuBBAAKBG0wawIBAQQg+QRO4P++H7ixJj/LVdGm\n" + "0pG1aIb6rWpsdJErR0B6xJehRANCAATHSgrFbTysaRfgq3iaU5XRz+KgMgkFTw+Y\n" + "+X5eUkjgpeiAuuC7kUil4iZpPJEV8yzYk4hUDH4+Yc9l48vHSTQ3\n" + "-----END PRIVATE KEY-----";

	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * post方式请求服务器(https协议)
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            参数
	 * @param charset
	 *            编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static byte[] post(String url, String content, String charset) throws NoSuchAlgorithmException, KeyManagementException, IOException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setDoOutput(true);
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes(charset));
		// 刷新、关闭
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			return outStream.toByteArray();
		}
		return null;
	}

	public static void main(String[] args) {
		new HttpsClient().createGroup();
		new HttpsClient().getMemberInfo();
	}

	// 创建聊天室群组
	public void createGroup() {
		String url = "https://console.tim.qq.com/v4/group_open_http_svc/create_group?";
		String sdkappid = "1400016780";// 应用ID
		String identifier = "admin1";// 管理员账号
		String jsonResult = "";
		GenTLSSignatureResult result = null;
		try {
			result = tls_sigature.GenTLSSignatureEx(Long.valueOf(sdkappid), identifier, privStr);
			url += "usersig=" + result.urlSig;
			// url +=
			// "usersig=eJx1j0FPgzAUgO-8CtIrxhRou2GyA8MtIQIJsrl4aiot8zFhlXW4afzvKi6Ri*-6fXnfex*WbdtolRTXoiz3x9Zwc9YK2Tc2mmKPoKs-rjVILgz3Ozlwl2CMXRZ43shSJw2d4qIyqvu16IRN8c*MLJCqNVDBxRGygXa85SB3fOj9HzrAdoDpIo-i5cmB90m8TPKoPLNstxVOU9*uipeQBqXzEDy-uuvHvmsroCGEWSwTmvprM2eO0cU8JfdvedQvyF189LOnerPf1H3QAu7z2WyUNNCoy0HfP1GCGUXWp-UFps1Xhg__";
			url += "&identifier=" + identifier;
			url += "&sdkappid=" + sdkappid;

			double rand1 = new Random().nextDouble();
			String random1 = new String(rand1 + "").substring(2, 18);
			double rand2 = new Random().nextDouble();
			String random2 = new String(rand2 + "").substring(2, 18);
			String random = random1 + random2;

			url += "&random=" + random;
			url += "&contenttype=json";

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Owner_Account", "admin1");
			jsonObject.put("Type", "AVChatRoom");
			jsonObject.put("Name", "hhr360");
			jsonResult = new String(HttpsClient.post(url, jsonObject.toString(), "UTF-8"), "UTF-8");
			System.out.println("jsonResult===>" + jsonResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取群组成员信息
	public void getMemberInfo() {
		try {
			String sdkAppID = "1400016780";// 应用ID
			String identifier = "admin1";// 管理员账号
			String avChatRoomId = "@TGS#aV5JEHGE5";// 群ID
			String url = "https://console.tim.qq.com/v4/group_open_http_svc/get_group_member_info?";
			GenTLSSignatureResult resultGroup = tls_sigature.GenTLSSignatureEx(Long.valueOf(sdkAppID), identifier, privStr);
			url += "usersig=" + resultGroup.urlSig;
			System.out.println(resultGroup.urlSig);

			url += "&identifier=" + identifier;
			url += "&sdkappid=" + sdkAppID;

			double rand1 = new Random().nextDouble();
			String random1 = new String(rand1 + "").substring(2, 14);
			double rand2 = new Random().nextDouble();
			String random2 = new String(rand2 + "").substring(2, 14);
			double rand3 = new Random().nextDouble();
			String random3 = new String(rand3 + "").substring(2, 10);
			String random = random1 + random2 + random3;

			url += "&random=" + random;
			url += "&contenttype=json";

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("GroupId", avChatRoomId);
			jsonObject.put("MemberInfoFilter", new String[] { "Role" });
			String jsonResult = new String(HttpsClient.post(url, jsonObject.toString(), "UTF-8"), "UTF-8");
			System.out.println(jsonResult);
			JSONObject obj = new JSONObject(jsonResult);
			System.out.println(obj.get("MemberList"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
