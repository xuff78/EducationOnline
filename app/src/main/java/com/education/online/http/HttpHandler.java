/**
 *  Author :  hmg25
 *  Description :
 */
package com.education.online.http;

import android.app.Activity;

import com.education.online.bean.AddClassBean;
import com.education.online.bean.CourseFilter;
import com.education.online.util.Constant;
import com.education.online.util.LeanSignatureUtil;
import com.education.online.util.SharedPreferencesUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

	public void AskOrAnswer(String qa_type,String subject_id,String name,String introduction, String img,String integral,String question_id ){
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		if(qa_type!=null)
			paramMap.put("qa_type",qa_type);
		if(qa_type!=null)
			paramMap.put("subject_id",subject_id);
		if(qa_type!=null)
			paramMap.put("name",name);
		if(qa_type!=null)
			paramMap.put("introduction",introduction);
		if(qa_type!=null)
			paramMap.put("img",img);
		if(qa_type!=null)
			paramMap.put("integral",integral);
		if(qa_type!=null)
			paramMap.put("question_id",question_id);
		requestPostEdu(Method.askOrAnswer, paramMap, true);
	}

	public void getCourseList(CourseFilter filter){

		try {
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
			Class userCla = (Class) filter.getClass();
			Field[] fs = userCla.getDeclaredFields();
//			java.lang.reflect.Method[] methods = userCla.getMethods();
			for(int i = 0 ; i < fs.length; i++){
				Field f = fs[i];
				f.setAccessible(true); //设置些属性是可以访问的
				Object val = f.get(filter);
				if(val!=null) {
//					String type = f.getType().toString();//得到此属性的类型
//					if (type.endsWith("int") || type.endsWith("Integer")) {
//
//					} else if (type.endsWith("String")) {
//
//					}
					paramMap.put(f.getName(), String.valueOf(val));
				}
			}
			requestPostEdu(Method.getCourseList, paramMap, true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
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

	public void getlistByDate(String course_date) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("course_date", course_date);
		requestPostEdu(Method.getlistByDate, paramMap, true);
	}

	public void submitOrder(String course_id, String remark) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("course_id", course_id);
		if(remark!=null&&remark.length()>0)
			paramMap.put("remark", remark);
		requestPostEdu(Method.submitOrder, paramMap, true);
	}

	public void getOrderDetail(String order_number) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("order_number", order_number);
		requestPostEdu(Method.getOrderDetail, paramMap, true);
	}

	public void getOrderList(String course_type, int page, String status) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		if(course_type!=null)
			paramMap.put("course_type", course_type);
		if(status!=null)
			paramMap.put("status", status);
		paramMap.put("page_size","20");
		paramMap.put("page",page+"");
		requestPostEdu(Method.getOrderList, paramMap, true);
	}


	public void dispose(String url, String media_type, String dispose_type) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paramMap.put("media_type", media_type);
		paramMap.put("dispose_type", dispose_type);
		paramMap.put("url", url);
		requestPostEdu(Method.dispose, paramMap, true);
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

	public  void getEvaluateOthers(int page){
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("page_size","20");
		paraMap.put("page",page+"");
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

		if(addClassBean.getName().length()>0)
		paraMap.put("name",addClassBean.getName());
		if(addClassBean.getCourse_type().length()>0)
		paraMap.put("course_type",addClassBean.getCourse_type());
		if(addClassBean.getSubject_id().length()>0)
		paraMap.put("subject_id",addClassBean.getSubject_id());
		if(addClassBean.getOriginal_price().length()>0)
		paraMap.put("original_price",addClassBean.getOriginal_price());
		if(addClassBean.getPrice().length()>0)
		paraMap.put("price",addClassBean.getPrice());
		if(addClassBean.getImg().length()>0)
		paraMap.put("img",addClassBean.getImg());
		if(addClassBean.getCourse_url().length()>0)
		paraMap.put("course_url",addClassBean.getCourse_url());
		if(addClassBean.getIntroduction().length()>0)
		paraMap.put("introduction",addClassBean.getIntroduction());
		if(addClassBean.getMin_follow().length()>0)
		paraMap.put("min_follow",addClassBean.getMin_follow());
		if(addClassBean.getMax_follow().length()>0)
		paraMap.put("max_follow",addClassBean.getMax_follow());
		if(addClassBean.getRefund().length()>0)
		paraMap.put("refund",addClassBean.getRefund());
		if(addClassBean.getTransfer().length()>0)
		paraMap.put("transfer",addClassBean.getTransfer());
		if(addClassBean.getPlan().length()>0)
		paraMap.put("plan",addClassBean.getPlan());
		if(addClassBean.getCourseware_time().length()>0)
		paraMap.put("courseware_time",addClassBean.getCourseware_time());
		if(addClassBean.getTime_len().length()>0)
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

	public void getCourseCollections( String course_type, int page)
	{
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("course_type",course_type);
		paraMap.put("page_size","20");
		paraMap.put("page",page+"");
		requestPostEdu(Method.getCourseCollections,paraMap,true);
	}

	public void getEvaluateList(String course_id,String star, String page_size,String page)
	{
		HashMap<String, String > paraMap =new HashMap<>();
		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		paraMap.put("course_id",course_id);
		if(star!=null&&star.length()>0)
			paraMap.put("star",star);
		if(page_size.length()>0)
		paraMap.put("page_size",page_size);
		if(page.length()>0)
		paraMap.put("page",page);
		requestPostEdu(Method.getEvaluateList,paraMap,false);
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
		if(user_code.length()>0)
		paraMap.put("usercode",user_code);
		requestPostEdu(Method.addAttention,paraMap,true);
	}
	public void evaluate(String course_id,String star,String evaluate_info,String is_secret ){
		HashMap<String, String > paraMap =new HashMap<>();

		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		if(course_id.length()>0)
		paraMap.put("course_id",course_id);
		if(star.length()>0)
		paraMap.put("star",star);
		if(evaluate_info.length()>0)
		paraMap.put("evaluate_info",evaluate_info);
		if(is_secret.length()>0)
		paraMap.put("is_secret",is_secret);
		requestPostEdu(Method.evaluate,paraMap,true);

	}
	public  void getQuestionList(String query_type,String status,String subject_id,String page_size,String page){
		HashMap<String, String > paraMap =new HashMap<>();

		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		if(query_type.length()>0)
			paraMap.put("query_type",query_type);
		if(status.length()>0)
			paraMap.put("status",status);
		if(subject_id.length()>0)
			paraMap.put("subject_id",subject_id);
		if(page_size.length()>0)
			paraMap.put("page_size",page_size);
		if(page.length()>0)
			paraMap.put("page",page);
		requestPostEdu(Method.getQuestionList,paraMap,true);

	}

	public  void getAnswerList(String question_id,String page_size,String page){
		HashMap<String, String > paraMap =new HashMap<>();

		paraMap.put("sessionid",SharedPreferencesUtil.getSessionid(mContext));
		if(question_id.length()>0)
			paraMap.put("question_id",question_id);
		if(page_size.length()>0)
			paraMap.put("page_size",page_size);
		if(page.length()>0)
			paraMap.put("page",page);
		requestPostEdu(Method.getAnswerList,paraMap,true);
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
