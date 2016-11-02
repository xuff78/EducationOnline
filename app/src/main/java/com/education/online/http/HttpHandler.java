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

	/**
	 *
	 * @param status 课程状态 （over-已完结 underway-未完结）
	 * @param course_type  课程类型（courseware-课件 video-视频 live-直播）
	 * @param subject_id
	 * @param key_word
	 * @param is_free 是否免费 yes-是 no-不是
	 * @param usercode
	 * @param sort  排序（hot-热度 course_id-课程ID sort_order-教师设置排序值正序 evaluate-评价 price-价格）
	 * @param page_size
     * @param page
     */
	public void getCourseList(String status, String course_type, String subject_id, String key_word, String is_free, String usercode,
							  String sort, String page_size, String page, int query_type, String start_date, String end_date) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("status", status);
		if(course_type!=null)
			paramMap.put("course_type", course_type);
		if(subject_id!=null)
			paramMap.put("subject_id", subject_id);
		if(key_word!=null)
			paramMap.put("key_word", key_word);
		if(is_free!=null)
			paramMap.put("is_free", is_free);
		if(usercode!=null)
			paramMap.put("usercode", usercode);
		if(sort!=null)
			paramMap.put("sort", sort);
		if(page_size!=null)
			paramMap.put("page_size", page_size);
		if(page!=null)
			paramMap.put("page", page);
		if(start_date!=null)
			paramMap.put("start_date", start_date);
		if(end_date!=null)
			paramMap.put("end_date", end_date);
		if(query_type==0)
			paramMap.put("query_type", "course");
		else if(query_type==1){
			paramMap.put("query_type", "teacher");
		}
		requestPostEdu(Method.getCourseList, paramMap, true);
	}

	public void updateSortList(String course_ids) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("course_ids", course_ids);
		requestPostEdu(Method.updateSortList, paramMap, true);
	}

	public void deleteCourse(String course_id) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("course_id", course_id);
		requestPostEdu(Method.deleteCourse, paramMap, true);
	}

	public void getValidateDetails(String validate_type) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("validate_type", validate_type);
		requestPostEdu(Method.getValidateDetails, paramMap, true);
	}

	public void updateValidate(String pic_urls, String name, String id_number) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("pic_urls", pic_urls);
		if(name!=null)
			paramMap.put("name", name);
		if(id_number!=null)
			paramMap.put("id_number", id_number);
		requestPostEdu(Method.updateValidate, paramMap, true);
	}

	public void getValidateView() {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		requestPostEdu(Method.getValidateView, paramMap, true);
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

	public void regist(String phone, String password,String identity,String nickname,String gender,String avatar,String subject_id)
	{
		HashMap<String, String> paraMap = new HashMap<>();
		paraMap.put("phone",phone);
		paraMap.put("password",password);
		paraMap.put("identity",identity);
		paraMap.put("nickname",nickname);
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

	public  void getEvaluate(String usercode,String star,int page){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("usercode",usercode);
		if(star!=null)
			paraMap.put("star",star);
		paraMap.put("page_size","20");
		paraMap.put("page", String.valueOf(page));
		requestPostEdu(Method.getEvaluate,paraMap,false);
	}

	public  void getEvaluateOthers(String page){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("page_size","20");
		paraMap.put("page",page);
		requestPostEdu(Method.getEvaluateOthers,paraMap,true);
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

	public void getCourseDetail( String course_id)
	{
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("course_id",course_id);
		requestPostEdu(Method.getCourseDtail,paraMap,true);
	}

	public void getEvaluateList(String course_id,String star, String page_size,String page)
	{
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("course_id",course_id);
		paraMap.put("star",star);
		paraMap.put("page_size",page_size);
		paraMap.put("page",page);
		requestPostEdu(Method.getEvaluateList,paraMap,true);
	}
	public void addCollection (String course_id){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("course_id",course_id);
		requestPostEdu(Method.addCollection,paraMap,true);
	}

	public void addAttention (String user_code){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("user_code",user_code);
		requestPostEdu(Method.addAttention,paraMap,true);
	}
	public void evaluate(String course_id,String star,String evaluate_info,String is_secret ){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("course_id",course_id);
		paraMap.put("star",star);
		paraMap.put("evaluate_info",evaluate_info);
		paraMap.put("is_secret",is_secret);
		requestPostEdu(Method.evaluate,paraMap,true);

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
