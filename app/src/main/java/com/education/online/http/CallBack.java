package com.education.online.http;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.education.online.act.FirstPage;
import com.education.online.act.MainPage;
import com.education.online.act.Mine.MyOrderUser;
import com.education.online.act.login.LoginActivity;
import com.education.online.act.order.GetVeriCode;
import com.education.online.act.order.SubmitOrder;
import com.education.online.bean.JsonMessage;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;


/**
 * 回调接口，使用OA系统的程序进行实现
 * 
 */
public class CallBack {
	public static final String TAG = "CallBack";
	
	Activity mContext;
	private Dialog dialog;
	
	public CallBack(Activity con) {
		// TODO Auto-generated constructor stub
		mContext=con;
	}
	
	/**
	 * 成功回调函数
	 * 
	 * @param jsonMessage 回调成功字符串信息
	 */
	public void onSuccess(String method, String jsonMessage) throws JSONException {
//		LogUtil.e(TAG, "onSuccess:"+jsonMessage);
		if(mContext!=null&&!mContext.isFinishing()) {
			JsonMessage msg = JsonUtil.getJsonMessage(jsonMessage);
			if (msg.getCode() == null) {
				oServerException(method, jsonMessage);
			} else if (msg.getCode().equals("0"))
				doSuccess(method, JsonUtil.getJsonData(jsonMessage));
			else if (msg.getCode().equals("-232030")) {
				ToastUtils.displayTextShort(mContext, "会话失效，请重新登录");
				Intent i = new Intent(mContext, MainPage.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("Login", true);
				mContext.startActivity(i);
			} else if (msg.getCode().equals("-232000")){
				DialogUtil.showConfirmDialog(mContext, msg.getCode(), msg.getMsg(), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						mContext.startActivity(new Intent(mContext, GetVeriCode.class));
					}
				});
			}else{
				onFailure(method, msg, JsonUtil.getJsonData(jsonMessage));
			}
		}
	}

	public void doSuccess(String method, String jsonData) throws JSONException {
	}
	
	/**
	 * 失败回调函数
	 *
	 * @param jsonMessage 回调失败字符串信息
	 */
	public void onFailure(String method, JsonMessage jsonMessage, String json){
//		LogUtil.e(TAG, "onFailure:" + jsonMessage);
		DialogUtil.showInfoDailog(mContext, jsonMessage.getCode(), jsonMessage.getMsg());
	}
	
	/**
	 * 处理HTTP网络连接异常
	 * @param jsonMessage
	 */
	public void onHTTPException(String method, String jsonMessage){
//		LogUtil.e(TAG, "onHTTPException:"+jsonMessage);
		if(mContext!=null&&!mContext.isFinishing())
		DialogUtil.showInfoDailog(mContext, "提示", GlbsNet.HTTP_ERROR_MESSAGE);
	}

	/**
	 * 处理HTTP网络连接异常
	 * @param jsonMessage
	 */
	public void oServerException(String method, String jsonMessage){
//		LogUtil.e(TAG, "onHTTPException:"+jsonMessage);
		DialogUtil.showInfoDailog(mContext, "提示", "服务器内部错误，请稍后再试");
	}
	
	/**
	 * 处理未登陆事件
	 * @param jsonMessage
	 */
	public void onUnLogin(String jsonMessage){
		LogUtil.e(TAG, "onUnLogin:"+jsonMessage);
	}
	
	/**
	 * 没有sim卡，或sim无法使用的情况下触发的事件
	 * @param jsonMessage
	 */
	public void onUnSIM(String jsonMessage){
		LogUtil.e(TAG, "onUnSIM:"+jsonMessage);
	}
	
	/**
	 * 取消后的回调
	 */
	public void onCancel(){
		LogUtil.e(TAG, "onCancel");
	}
}
