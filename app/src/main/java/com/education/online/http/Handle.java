package com.education.online.http;

import android.content.Context;

import com.education.online.util.ToastUtils;

import org.json.JSONException;


/**
 * 业务逻辑处理类，负责联网的调用，及对数据的处理。此超类中封装了对联网模块的调用。
 */
public abstract class Handle implements HttpCallback {
	
	private static final String TAG = "Handle";

	/**
	 * 用于在HttpAsyncTask类中显示UI（显示进度条）
	 */
	protected Context mContext;

	/**
	 * 回调对象
	 */
	protected CallBack mCallBack;

	/**
	 * UI界面用于区分不同请求的页面请求id
	 */
	protected int mUiReqId;

	/**
	 * 唯一构造函数，仅包内子类可见
	 * @param context
	 *            Context对象
	 * @param callback
	 *            HandleCallback对象
	 */
	protected Handle(Context context, CallBack callback) {
		mContext = context;
		mCallBack = callback;
	}
	
	/**
	 * 处理请求返回的结果
	 */
	public void httpCallback(String reqUrl, String result) throws JSONException {
		if(result.equals(GlbsNet.HTTP_ERROR_MESSAGE)) {
			ToastUtils.displayTextShort(mContext, result);
			mCallBack.onHTTPException(reqUrl,result);
		}else
			mCallBack.onSuccess(reqUrl,result);

	}
}
