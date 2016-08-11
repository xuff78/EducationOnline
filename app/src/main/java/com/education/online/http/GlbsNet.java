package com.education.online.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiManager;
import android.text.TextUtils;


import com.education.online.util.LogUtil;
import com.education.online.util.URLUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;


/**
 * 网络连接管理类：判断网络状态、管理网络连接、发起GET和POST请求。
 * 
 * @author heyongbo@infohold.com.cn
 */

public final class GlbsNet {

	public static final String HTTP_ERROR_MESSAGE = "抱歉，您的网络无法连通，请您检查网络设置后重试。";

	private static ConnectivityManager sConnManager;

	private static long CONNECTION_TIMEOUT = 40;// 设置超时时间，秒单位

	/**
	 * 向指定URI，发起一个POST请求，并返回服务器响应的json串。
	 *
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数集
	 * @return 返回服务器响应的json串。网络异常时，返回{@code null}。
	 */
	public static String doPost(String uri, HashMap<String, String> params) {
		InputStreamReader isr = null;
		BufferedReader br = null;
		HttpURLConnection urlConn = null;
		String result="";
		try {
			urlConn = getURLConnection(uri);
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			//urlConn.setInstanceFollowRedirects(true);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			urlConn.connect();
			//DataOutputStream流
			DataOutputStream out=new DataOutputStream(urlConn.getOutputStream());
			//要上传的参数
			String content= URLUtil.map2string(params);
			out.writeBytes(content);
			//刷新、关闭
			out.flush();
			out.close();

			//得到读取的内容（流）
			result=getJsonStringFromGZIP(urlConn);
			LogUtil.d("TestDemo", result);
		} catch (Exception e) {
			result=HTTP_ERROR_MESSAGE;
			LogUtil.e("NetError", e.getMessage());
		} finally {
			try {
				if (isr != null)
					isr.close();
				if (br != null)
					br.close();
				if(urlConn!=null)
					urlConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static HttpURLConnection getURLConnection(String url) throws Exception {
		String proxyHost = Proxy.getDefaultHost();
		if (proxyHost != null) {
			java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
					new InetSocketAddress(Proxy.getDefaultHost(),
							Proxy.getDefaultPort()));

			return (HttpURLConnection) new URL(url).openConnection(p);

		} else {
			return (HttpURLConnection) new URL(url).openConnection();
		}
	}

	private static boolean isWapNetwork() {
		final String proxyHost = Proxy.getDefaultHost();
		return !TextUtils.isEmpty(proxyHost);
	}

	public static boolean isUsedWifi(Context con) {
        WifiManager wifiManager = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);
        boolean isUsedWifi = false;// wifiManager.isWifiEnabled();
        ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(
            Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            isUsedWifi = true;
        }
        if (isUsedWifi && wifiManager.isWifiEnabled()) {
            return true;
        } else {
            return false;
        }
    }

	private static String getJsonStringFromGZIP( HttpURLConnection  response) {
		String jsonString = "";

		try {
			InputStream e = response.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(e);
			bis.mark(2);
			byte[] header = new byte[2];
			int result = bis.read(header);
			bis.reset();
			int headerData = getShort(header);
			Object e1;
			if(result != -1 && headerData == 8075) {
				LogUtil.d("HttpTask", " use GZIPInputStream  ");
				e1 = new GZIPInputStream(bis);
			} else {
				LogUtil.d("HttpTask", " not use GZIPInputStream");
				e1 = bis;
			}

			InputStreamReader reader = new InputStreamReader((InputStream)e1, "utf-8");
			char[] data = new char[100];
			StringBuffer sb = new StringBuffer();

			int readSize;
			while((readSize = reader.read(data)) > 0) {
				sb.append(data, 0, readSize);
			}

			jsonString = sb.toString();
			bis.close();
			reader.close();
		} catch (Exception var11) {
			LogUtil.e("HttpTask", var11.toString(), var11);
		}

		LogUtil.d("HttpTask", "getJsonStringFromGZIP net output : " + jsonString);
		return jsonString;
	}

	private static int getShort(byte[] data) {
		return data[0] << 8 | data[1] & 255;
	}

	public static String doGet(String uri) {
		HttpURLConnection urlConn = null;
		String result="";
		try {
			urlConn = getURLConnection(uri);
			urlConn.setRequestMethod("GET");
			//urlConn.setInstanceFollowRedirects(true);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			urlConn.connect();

			//得到读取的内容（流）
			result=getJsonStringFromGZIP(urlConn);
			LogUtil.d("TestDemo", result);
		} catch (Exception e) {
			result=HTTP_ERROR_MESSAGE;
			LogUtil.e("NetError", e.getMessage());
		} finally {
			if(urlConn!=null)
				urlConn.disconnect();
		}

		return result;
	}

	public static String doDelete(String uri) {
		HttpURLConnection urlConn = null;
		String result="";
		try {
			urlConn = getURLConnection(uri);
			urlConn.setRequestMethod("DELETE");
			//urlConn.setInstanceFollowRedirects(true);
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			urlConn.connect();

			//得到读取的内容（流）
			result=getJsonStringFromGZIP(urlConn);
			LogUtil.d("TestDemo", result);
		} catch (Exception e) {
			result=HTTP_ERROR_MESSAGE;
			LogUtil.e("NetError", e.getMessage());
		} finally {
			if(urlConn!=null)
				urlConn.disconnect();
		}

		return result;
	}

	/**
	 * 网络中断监听。
	 */
	public interface OnNetDisconnListener {
		/**
		 * 当网络中断时，执行此方法。
		 */
		void onNetDisconnected();
	}

	public static String doPostNew(String uri, String content){  // If you want to use post method to hit server

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setHeader("Content-Type", "application/json");
		HttpResponse response = null;
		String result = "";
		try {
			StringEntity entity = new StringEntity(content, HTTP.UTF_8);
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			HttpEntity entity1 = response.getEntity();
			result = EntityUtils.toString(entity1);
			return result;
			//Toast.makeText(MainPage.this, result, Toast.LENGTH_LONG).show();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}