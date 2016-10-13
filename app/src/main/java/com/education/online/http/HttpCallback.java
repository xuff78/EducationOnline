/**
 * 
 */
package com.education.online.http;


import org.json.JSONException;

/**
 * Http连接回调类，在使用HttpAsyncTask类进行网络连接后，需要实现此接口，将联网获得的数据返回给调用者。
 * 
 * @author hurray
 * @see HttpAsyncTask
 * 
 */
public interface HttpCallback {

	/**
	 * HttpAysnTask中使用到的回调方法，将数据返回给调用者。 <br>
	 * 当请求参数不合法时，返回的result[0]为"-1";<br>O
	 * 当联网失败时，返回的result[0]为"-2";<br>
	 * 其他result[0]的值均有服务器端定义并返回的。<br>
	 * 
	 * @param reqUrl
	 *            请求的URL，调用者在获取数据后来进行区分，从而做出不同处理
	 * @param result
	 *            服务器返回的数据，字符串经split处理后的结果，其中数组的第一个元素代表连接返回码，"0"为正常
	 */
	void httpCallback(String reqUrl, String result) throws JSONException;
}
