package com.education.online.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by Administrator on 2016/10/8.
 */
public class LeanSignatureUtil {


    public static String sign(Context context, String url_api, Map<String, String> params) {
        String url = url_api;
        params.put("deviceid", getDeviceid(context));
        params.put("imsi", getImsi(context));
        params.put("imei", getImei(context));
        params.put("ua", getUA(context));
        params.put("nonce", UUID.randomUUID().toString());
        params.put("timestamp", "" + System.currentTimeMillis() / 1000);

        encode(params);
        Map sortedParams = MapUtil.sort(params);
        String paramString = URLUtil.map2string(sortedParams);

        try {
            LogUtil.i("TestDemo", "param1---->" + paramString);
            String e = URLEncoder.encode(url.toLowerCase(), "utf-8") + URLEncoder.encode(paramString, "utf-8");
            e = e.replaceAll("\\+", "%20");
            e = e.replaceAll("\\!", "%21");
            e = e.replaceAll("\\~", "%7E");
            e = e.replaceAll("\\*", "%2A");

            String app_secret = SharedPreferencesUtil.getString(context, "app_secret");
            String signature = HmacSha.getSignature(e,app_secret);
            signature = URLEncoder.encode(signature, "utf-8");


            params.put("sign", signature);
            LogUtil.i("sign", signature);
            LogUtil.i("TestDemo", url + params.toString());

            StringBuffer sb = new StringBuffer();
            Iterator var14 = params.entrySet().iterator();

            while(var14.hasNext()) {
                Map.Entry entry = (Map.Entry)var14.next();
                sb.append("&");
                sb.append((String)entry.getKey());
                sb.append("=");
                sb.append((String)entry.getValue());
            }

            LogUtil.i("TestDemo", url + sb.toString().replaceFirst("&", "?"));
        } catch (Throwable var15) {
            LogUtil.e("GLBS.error", "获取signature失败", var15);
        }

        String content= URLUtil.map2string(params);
        LogUtil.i("TestDemo", "content---->" + content);
        return content;
    }

    public static RequestBody signOkhttp(Context context, String url_api, Map<String, String> params) {
        String url = url_api;
        params.put("deviceid", getDeviceid(context));
        params.put("imsi", getImsi(context));
        params.put("imei", getImei(context));
        params.put("ua", getUA(context));
        params.put("nonce", UUID.randomUUID().toString());
        params.put("timestamp", "" + System.currentTimeMillis() / 1000);

        encode(params);
        Map sortedParams = MapUtil.sort(params);
        String paramString = URLUtil.map2string(sortedParams);

        try {
            LogUtil.i("TestDemo", "param1---->" + paramString);
            String e = URLEncoder.encode(url.toLowerCase(), "utf-8") + URLEncoder.encode(paramString, "utf-8");
            e = e.replaceAll("\\+", "%20");
            e = e.replaceAll("\\!", "%21");
            e = e.replaceAll("\\~", "%7E");
            e = e.replaceAll("\\*", "%2A");

            String app_secret = SharedPreferencesUtil.getString(context, "app_secret");
            String signature = HmacSha.getSignature(e,app_secret);
            signature = URLEncoder.encode(signature, "utf-8");


            params.put("sign", signature);
            LogUtil.i("sign", signature);
            LogUtil.i("TestDemo", url + params.toString());

            StringBuffer sb = new StringBuffer();
            Iterator var14 = params.entrySet().iterator();

            while(var14.hasNext()) {
                Map.Entry entry = (Map.Entry)var14.next();
                sb.append("&");
                sb.append((String)entry.getKey());
                sb.append("=");
                sb.append((String)entry.getValue());
            }

            LogUtil.i("TestDemo", url + sb.toString().replaceFirst("&", "?"));
        } catch (Throwable var15) {
            LogUtil.e("GLBS.error", "获取signature失败", var15);
        }

        String content= URLUtil.map2string(params);
        LogUtil.i("TestDemo", "content---->" + content);
        FormBody.Builder formBody = new FormBody.Builder();
        for (String name:params.keySet()) {
            formBody.add(name, params.get(name));
        }
        return formBody.build();
    }

    public static void encode(final Map<String, String> params) {
        final String UTF8 = "utf-8";
        Iterator<Map.Entry<String, String>> it;
        Map.Entry<String, String> kv;
        String k, v;
        for (it = params.entrySet().iterator(); it.hasNext();) {
            kv = it.next();
            k = kv.getKey();
            v = kv.getValue();
            LogUtil.i("TestDemo", "param4---->" + k + " * " + v);
            try {
                v = URLEncoder.encode(v, UTF8);
                v = v.replaceAll("\\+", "%20");
                v = v.replaceAll("\\!", "%21");
                v = v.replaceAll("\\~", "%7E");
                v = v.replaceAll("\\*", "%2A");
                // LogUtil.i("TestDemo", "param4---->" + v) ;
                params.put(k, v);
            } catch (Exception e) {
                throw new RuntimeException("编码参数失败，请检查参数是否合法", e);
            }
        }
    }

    public static String getImsi(Context context) {
        String imsi = SharedPreferencesUtil.getIMSI(context);
        if(imsi.equals(SharedPreferencesUtil.FAILURE_STRING) && imsi.length() > 0) {
            TelephonyManager telMng = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            if(telMng.getSimState() ==  TelephonyManager.SIM_STATE_READY) {
                imsi = telMng.getSubscriberId();
                SharedPreferencesUtil.setIMSI(context, imsi);
            }
        }

        return imsi;
    }

    public static String getImei(Context context) {
        String imei = SharedPreferencesUtil.getIMEI(context);
        if(imei.equals(SharedPreferencesUtil.FAILURE_STRING) && imei.length() > 0) {
            TelephonyManager telMng = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telMng.getDeviceId();
            SharedPreferencesUtil.setIMEI(context, imei);
        }

        return imei;
    }

    public static String getDeviceid(Context context) {
        String deviceid = SharedPreferencesUtil.getString(context, "deviceid");
        if (deviceid.equals(SharedPreferencesUtil.FAILURE_STRING)||deviceid.length()==0) {
            deviceid = UUID.randomUUID().toString()+"0000";
            SharedPreferencesUtil.setString(context, "deviceid", deviceid);
        }
        return deviceid;
    }

    public static String getUA(Context context) {
        String ua = "";
        StringBuilder builder = new StringBuilder();
        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        builder.append(Build.BRAND).append("^").append(Build.MODEL).append("^")
                .append("Android").append("^").append(Build.VERSION.RELEASE)
                .append("^").append(tm.getDeviceId()).append("^")
                .append(tm.getSubscriberId()).append("^").append(versionCode);
        ua = builder.toString();
        LogUtil.i("TestDemo", "ua---->" + ua);
        return ua;

    }
}
