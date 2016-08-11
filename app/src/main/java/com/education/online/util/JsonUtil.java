package com.education.online.util;


import com.education.online.bean.JsonMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/7.
 */
public class JsonUtil {

    public static JsonMessage getJsonMessage(String jsonStr){
        JsonMessage jsonMsg=new JsonMessage();
        try {
            JSONObject json=new JSONObject(jsonStr);
            if(!json.isNull("code"))
                jsonMsg.setCode(json.getString("code"));
            if(!json.isNull("error"))
                jsonMsg.setMsg(json.getString("error"));
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
