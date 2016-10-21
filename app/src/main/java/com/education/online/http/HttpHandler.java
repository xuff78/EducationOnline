/**
 *  Author :  hmg25
 *  Description :
 */
package com.education.online.http;

import android.app.Activity;

import com.education.online.bean.AddClassBean;
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

	public void getInterestList() {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		requestPostEdu(Method.getInterestList, paramMap, true);
	}

	public void editInterest(String subject_ids) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("subject_ids",subject_ids);
		requestPostEdu(Method.editInterest, paramMap, true);
	}

	public void getUserInfo(String usercode) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("usercode",usercode);
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		requestPostEdu(Method.getUserInfo, paramMap, true);
	}

	public void regist(String phone, String password,String identity,String nickname,String name,String gender,String avatar,String subject_id)
	{
		HashMap<String, String> paraMap = new HashMap<>();
		paraMap.put("phone",phone);
		paraMap.put("password",password);
		paraMap.put("identity",identity);
		paraMap.put("nickname",nickname);
		paraMap.put("name",name);
		paraMap.put("gender",gender);
		paraMap.put("avatar",avatar);
		paraMap.put("subject_id",subject_id);
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

	public  void update(String gender,String avatar,String nickname, String birthday){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
//		paraMap.put("name",name);
		paraMap.put("gender",gender);
		paraMap.put("avatar",avatar);
		paraMap.put("nickname",nickname);
		paraMap.put("birthday",birthday);
		requestPostUser(Method.Update,paraMap,true);

	}


	public  void updateTeacher(String subject_id,String work_time,String specialty,String edu_bg,String school
			,String unit,String about_teacher,String introduction,String experience,String tags){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		if(subject_id.length()>0)
			paraMap.put("subject_id",subject_id);
		if(work_time.length()>0)
			paraMap.put("work_time",work_time);
		if(specialty.length()>0)
			paraMap.put("specialty",specialty);
		if(edu_bg.length()>0)
			paraMap.put("edu_bg",edu_bg);
		if(school.length()>0)
			paraMap.put("school",school);
		if(unit.length()>0)
			paraMap.put("unit",unit);
		if(about_teacher.length()>0)
			paraMap.put("about_teacher",about_teacher);
		if(introduction.length()>0)
			paraMap.put("introduction",introduction);
		if(experience.length()>0)
			paraMap.put("experience",experience);
		if(tags.length()>0)
			paraMap.put("tags",tags);
		requestPostEdu(Method.updateTeacher,paraMap,true);

	}

	public void addClass(AddClassBean addClassBean){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("name",addClassBean.getName());
		paraMap.put("course_type",addClassBean.getCourse_type());
		paraMap.put("subject_id",addClassBean.getSubject_id());
		paraMap.put("original_price",addClassBean.getOriginal_price());
		paraMap.put("price",addClassBean.getPrice());
		paraMap.put("img",addClassBean.getImg());
		paraMap.put("course_url",addClassBean.getCourse_url());
		paraMap.put("introduction",addClassBean.getIntroduction());
		paraMap.put("min_follow",addClassBean.getMin_follow());
		paraMap.put("max_follow",addClassBean.getMax_follow());
		paraMap.put("refund",addClassBean.getRefund());
		paraMap.put("transfer",addClassBean.getTransfer());
		paraMap.put("plan",addClassBean.getPlan());
		paraMap.put("courseware_time",addClassBean.getCourseware_time());
		paraMap.put("time_len",addClassBean.getTime_len());
		requestPostEdu(Method.addClass,paraMap,true);

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
