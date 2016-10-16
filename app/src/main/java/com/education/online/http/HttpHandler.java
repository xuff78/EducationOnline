/**
 *  Author :  hmg25
 *  Description :
 */
package com.education.online.http;

import android.app.Activity;

import com.education.online.util.Constant;
import com.education.online.util.LeanSignatureUtil;
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

//	public void init(String sc) {
//		HashMap<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("pixel", sc);
//		requestPost(Constant.Method.init, paramMap, true);
//	}

	public void login(String phone, String password) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("phone", phone);
		paramMap.put("password", password);
		requestPostUser(Method.Login, paramMap, true);
	}

	public void getSubjectList() {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		requestPostEdu(Method.getSubjectList, paramMap, true);
	}

	public void getHomepage() {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		requestPostEdu(Method.getHomePage, paramMap, true);
	}


	public void regist(String phone, String password,String identity)
	{
		HashMap<String, String> paraMap = new HashMap<>();
		paraMap.put("phone",phone);
		paraMap.put("password",password);
		paraMap.put("identity",identity);
		requestPostUser(Method.Regist,paraMap,true);
	}
	public  void update(String name,String gender,String avatar){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("name",name);
		paraMap.put("gender",gender);
		paraMap.put("avatar",avatar);
		requestPostUser(Method.Update,paraMap,true);

	}

	protected void requestPostUser(String method, HashMap paramMap, boolean showDialog) {
		String body= LeanSignatureUtil.sign(mContext, Constant.API_Url_User+method, paramMap); //这个是加密过程，可以不看
		new HttpAsyncTask(mContext, this, showDialog).execute(Constant.API_Url_User+method, method, body, 1);
	}

	protected void requestPostEdu(String method, HashMap paramMap, boolean showDialog) {
		String body= LeanSignatureUtil.sign(mContext, Constant.API_Url_Service+method, paramMap); //这个是加密过程，可以不看
		new HttpAsyncTask(mContext, this, showDialog).execute(Constant.API_Url_Service+method, method, body, 1);
	}


//	protected void requestPost(String method, HashMap paramMap, boolean showDialog) {
//		String url= SharedPreferencesUtil.getString(mContext, Constant.Url_API);
//		String body=SignatureUtil.sign(mContext, url, method, paramMap); //这个是加密过程，可以不看
//		new HttpAsyncTask(mContext, this, showDialog)
//				.execute(url, method, body, 1);
//	}
}
