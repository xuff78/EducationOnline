/**
 *  Author :  hmg25
 *  Description :
 */
package com.education.online.http;

import android.app.Activity;

import com.education.online.util.Constant;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.SignatureUtil;

import java.util.HashMap;


/**
 * hmg25's android Type
 * 
 * @author Administrator
 * 
 */
public class HttpHandler extends Handle {

	private Activity mContext;

	public HttpHandler(Activity mContext, CallBack mCallBack) {
		super(mContext, mCallBack);
		this.mContext = mContext;
	}

	public void init(String sc) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("pixel", sc);
		requestPost(Constant.Method.init, paramMap, true);
	}

	protected void requestPost(String method, HashMap paramMap, boolean showDialog) {
		String url= SharedPreferencesUtil.getString(mContext, Constant.Url_API);
		SignatureUtil.sign(mContext, url, method, paramMap);
		new HttpAsyncTask(mContext, this, showDialog)
				.execute(url, method, paramMap, 1);
	}
}
