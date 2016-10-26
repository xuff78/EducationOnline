package com.education.online.util;


import com.alibaba.fastjson.JSON;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.CourseEvaluate;
import com.education.online.bean.CourseExtm;
import com.education.online.bean.CreatUserInfo;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.bean.JsonMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/7.
 */
public class JsonUtil {

    public static JsonMessage getJsonMessage(String jsonStr){
        JsonMessage jsonMsg=new JsonMessage();
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull("return_code"))
                jsonMsg.setCode(json.getString("return_code"));
            if(!json.isNull("return_message"))
                jsonMsg.setMsg(json.getString("return_message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonMsg;
    }

    public static String getJsonData(String jsonStr) {
        String data="";
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull("data"))
                data=json.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String getString(String jsonStr, String key) {
        String data="";
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull(key))
                data=json.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static void addJsonData(JSONObject jsonStr, String key, Object value) {

        try {
            if (key != null && value != null) {
                jsonStr.put(key.toUpperCase(), value).toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static int getJsonInt(String jsonStr, String key) {
        int data=0;
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull(key))
                data=json.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean getJsonBoolean(String jsonStr, String key) {
        boolean data=false;
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull(key))
                data=json.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static CourseDetailBean getCourseDetail (String jsonStr) throws JSONException {
        CourseDetailBean courseDetailBean = new CourseDetailBean();
        List<CourseExtm> courseExtms = new ArrayList<>();
        CourseEvaluate courseEvaluate = new CourseEvaluate();
        CreatUserInfo creatUserInfo = new CreatUserInfo();
        List<EvaluateBean> evaluateBeans = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        if (!jsonObject.isNull("course_id"))
        courseDetailBean.setCourse_id(jsonObject.getString("course_id"));
        if(!jsonObject.isNull("course_name"))
            courseDetailBean.setCourse_name(jsonObject.getString("course_name"));
        if(!jsonObject.isNull("subject_name"))
            courseDetailBean.setSubject_name(jsonObject.getString("subject_name"));
        if(!jsonObject.isNull("img"))
            courseDetailBean.setImg(jsonObject.getString("img"));
        if(!jsonObject.isNull("original_price"))
            courseDetailBean.setOriginal_price(jsonObject.getString("original_price"));
        if(!jsonObject.isNull("price"))
            courseDetailBean.setPrice(jsonObject.getString("price"));
        if(!jsonObject.isNull("follow"))
            courseDetailBean.setFollow(jsonObject.getString("follow"));
        if(!jsonObject.isNull("min_follow"))
            courseDetailBean.setMin_follow(jsonObject.getString("min_follow"));
        if(!jsonObject.isNull("max_follow"))
            courseDetailBean.setMax_follow(jsonObject.getString("max_follow"));
        if(!jsonObject.isNull("plan"))
            courseDetailBean.setPlan(jsonObject.getString("plan"));
        if(!jsonObject.isNull("refund"))
            courseDetailBean.setRefund(jsonObject.getString("refund"));
        if(!jsonObject.isNull("transfer"))
            courseDetailBean.setTransfer(jsonObject.getString("transfer"));
        if(!jsonObject.isNull("introduction"))
            courseDetailBean.setIntroduction(jsonObject.getString("introduction"));
        if(!jsonObject.isNull("hot"))
            courseDetailBean.setHot(jsonObject.getString("hot"));
        if(!jsonObject.isNull("course_type"))
            courseDetailBean.setCourse_type(jsonObject.getString("course_type"));
        if(!jsonObject.isNull("user_code"))
            courseDetailBean.setUsercode(jsonObject.getString("user_code"));
        if(!jsonObject.isNull("is_collection"))
            courseDetailBean.setIs_collection(jsonObject.getString("is_collection"));
        if(!jsonObject.isNull("user_info")) {
            JSONObject jsonObject1 = jsonObject.getJSONObject("user_info");
            if (!jsonObject1.isNull("user_name"))
                creatUserInfo.setUser_name(jsonObject1.getString("user_name"));
            if (!jsonObject1.isNull("introduction"))
                creatUserInfo.setIntroduction(jsonObject1.getString("introduction"));
            if (!jsonObject1.isNull("avatar "))
                creatUserInfo.setAvatar(jsonObject1.getString("avatar "));
            if (!jsonObject1.isNull("evaluate_count"))
                creatUserInfo.setEvaluate_count(jsonObject1.getString("evaluate_count"));
            if (!jsonObject1.isNull("average"))
                creatUserInfo.setAverage(jsonObject1.getString("average"));
            courseDetailBean.setUser_info(creatUserInfo);
        }
        if (!jsonObject.isNull("course_extm"))
        {
            JSONArray jsonArray = jsonObject.getJSONArray("course_extm");
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject item = jsonArray.getJSONObject(i);
                CourseExtm courseExtm = new CourseExtm();
                if(!item.isNull("courseware_date"))
                courseExtm.setCourseware_date(item.getString("courseware_date"));
                if(!item.isNull("url"))
                    courseExtm.setUrl(item.getString("url"));
                if(!item.isNull("state"))
                    courseExtm.setState(item.getString("state"));
                courseExtms.add(courseExtm);

            }
            courseDetailBean.setCourse_extm(courseExtms);

        }
        if(!jsonObject.isNull("course_evaluate"))
        {
            JSONObject jsonObject1 = jsonObject.getJSONObject("course_evaluate");
            if(!jsonObject1.isNull("total"))
                courseEvaluate.setTotal("total");
            if(!jsonObject1.isNull("page_total"))
                courseEvaluate.setPage_total("page-total");
            if(!jsonObject1.isNull("current_page"))
                courseEvaluate.setCurrent_page("current_page");
            if(!jsonObject1.isNull("evaluate")){
                JSONArray jsonArray = jsonObject1.getJSONArray("evaluate");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    EvaluateBean evaluateBean = new EvaluateBean();
                    if (!item.isNull("info"))
                        evaluateBean.setInfo(item.getString("info"));
                    if (!item.isNull("star"))
                        evaluateBean.setStar(item.getString("star"));
                    if (!item.isNull("evaluate_date"))
                        evaluateBean.setEvaluate_date(item.getString("evaluate_date"));
                    if (!item.isNull("user_name"))
                        evaluateBean.setUser_name(item.getString("user_name"));
                    if (!item.isNull("avatar"))
                        evaluateBean.setAvatar(item.getString("avatar"));
                    evaluateBeans.add(evaluateBean);
                }
                courseEvaluate.setEvaluate(evaluateBeans);

            }
            courseDetailBean.setCourse_evaluate(courseEvaluate);

        }
        return courseDetailBean;

    }

    public static EvaluateListBean getEvaluateList(String jsonStr) throws JSONException {
        EvaluateListBean  evaluateListBean = new EvaluateListBean();
        List<EvaluateBean> evaluateBeanList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        if(!jsonObject.isNull("average"));
        evaluateListBean.setAverage(jsonObject.getString("average"));
        if(!jsonObject.isNull("evaluate_details")) {
            JSONObject jsonObject1 = jsonObject.getJSONObject("evaluate_details");
            if(!jsonObject1.isNull("page_total"))
                evaluateListBean.setPagetotal(jsonObject1.getString("page_total"));
            if (!jsonObject1.isNull("total"))
                evaluateListBean.setTotal(jsonObject1.getString("total"));
            if(!jsonObject1.isNull("current_page"))
                evaluateListBean.setCurrent_page(jsonObject1.getString("current_page"));
            if (!jsonObject1.isNull("evaluate"))
            {
                JSONArray jsonArray = jsonObject1.getJSONArray("evaluate");
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject item = jsonArray.getJSONObject(i);
                    EvaluateBean evaluateBean = new EvaluateBean();
                    if (!item.isNull("info"))
                        evaluateBean.setInfo(item.getString("info"));
                    if (!item.isNull("star"))
                        evaluateBean.setStar(item.getString("star"));
                    if (!item.isNull("evaluate_date"))
                        evaluateBean.setEvaluate_date(item.getString("evaluate_date"));
                    if (!item.isNull("user_name"))
                        evaluateBean.setUser_name(item.getString("user_name"));
                    if (!item.isNull("avatar"))
                        evaluateBean.setAvatar(item.getString("avatar"));
                    evaluateBeanList.add(evaluateBean);
                }
                evaluateListBean.setEvaluateList(evaluateBeanList);
            }
        }

        return evaluateListBean;

    }

//    public static ArrayList<RainBean> getRainInfo(String jsonStr) {
//        ArrayList<RainBean> infos=new ArrayList<>();
//        try {
//                JSONArray array = new JSONArray(jsonStr);
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject item = array.getJSONObject(i);
//                    RainBean bean = new RainBean();
//                    if (!item.isNull("name"))
//                        bean.setName(item.getString("name"));
//                    if (!item.isNull("h1"))
//                        bean.setHour1(item.getString("h1"));
//                    if (!item.isNull("h3"))
//                        bean.setHour3(item.getString("h3"));
//                    if (!item.isNull("h12"))
//                        bean.setHour12(item.getString("h12"));
//                    if (!item.isNull("h24"))
//                        bean.setHour24(item.getString("h24"));
//                    if (!item.isNull("area"))
//                        bean.setArea(item.getString("area"));
//                    infos.add(bean);
//                }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return infos;
//    }
//
//    public static ArrayList<ReportBean> getMSRecords(String jsonStr) {
//        ArrayList<ReportBean> infos=new ArrayList<>();
//        try {
//            JSONObject obj=new JSONObject(jsonStr);
//            if(!obj.isNull("data")) {
//                JSONArray array = obj.getJSONArray("data");
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject item = array.getJSONObject(i);
//                    ReportBean bean = new ReportBean();
//                    if (!item.isNull("ID"))
//                        bean.setID(item.getString("ID"));
//                    if (!item.isNull("KS_ID"))
//                        bean.setKS_ID(item.getString("KS_ID"));
//                    if (!item.isNull("DXS_ID"))
//                        bean.setKS_ID(item.getString("DXS_ID"));
//                    if (!item.isNull("DZYJ_ID"))
//                        bean.setKS_ID(item.getString("DZYJ_ID"));
//                    if (!item.isNull("BXBQ_ID"))
//                        bean.setKS_ID(item.getString("BXBQ_ID"));
//                    if (!item.isNull("DATETIME"))
//                        bean.setDATETIME(item.getString("DATETIME"));
//                    if (!item.isNull("MS"))
//                        bean.setMS(item.getString("MS"));
//                    if (!item.isNull("TYPE"))
//                        bean.setTYPE(item.getString("TYPE"));
//                    if (!item.isNull("PATH"))
//                        bean.setPATH(item.getString("PATH"));
//                    infos.add(bean);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return infos;
//    }
//
//    public static ArrayList<Map<String, String>> getDataMap(String jsonStr) {
//        ArrayList<Map<String, String>> infos=new ArrayList<>();
//        try {
//            JSONObject obj=new JSONObject(jsonStr);
//            if(!obj.isNull("data")) {
//                JSONArray array = obj.getJSONArray("data");
//                for (int i = 0; i < array.length(); i++) {
//                    Map<String, String> infomap=new HashMap<>();
//                    JSONObject item = array.getJSONObject(i);
//                    Iterator<String> keys=item.keys();
//                    while (keys.hasNext()){
//                        String key=keys.next();
//                        String value=item.getString(key);
//                        if(!value.equals("null"))
//                            infomap.put(key, value);
//                        else
//                            infomap.put(key, "");
//                    }
//                    infos.add(infomap);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return infos;
//    }
//
//    public static ArrayList<CateInfo> getBookList(String jsonStr) {
//        ArrayList<CateInfo> infos=new ArrayList<>();
//        try {
//                JSONArray array = new JSONArray(jsonStr);
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject item = array.getJSONObject(i);
//                    CateInfo bean = new CateInfo();
//                    if (!item.isNull("catelog"))
//                        bean.setCatelog(item.getString("catelog"));
//                    if (!item.isNull("files")){
//                        JSONArray subarray = item.getJSONArray("files");
//                        ArrayList<PopupInfoItem> books=new ArrayList<>();
//                        for (int j=0;j<subarray.length();j++){
//                            JSONObject subItem = subarray.getJSONObject(j);
//                            if (!subItem.isNull("name")&&!subItem.isNull("url")) {
//                                PopupInfoItem book = new PopupInfoItem(subItem.getString("name"), subItem.getString("url"));
//                                books.add(book);
//                            }
//                        }
//                        bean.setInfos(books);
//                    }
//                    infos.add(bean);
//                }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return infos;
//    }
}
