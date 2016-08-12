
package com.education.online.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public final class SignatureUtil {


    public static String sign(Context context, String url_api, String method, Map<String, String> params) {
        String url = url_api;
        String appkey = SharedPreferencesUtil.getString(context, "app_key");
        params.put("deviceid", SharedPreferencesUtil.getString(context, "deviceid"));
        params.put("imsi", getImsi(context));
        params.put("imei", getImei(context));
        params.put("appkey", appkey);
        params.put("channel_id", SharedPreferencesUtil.getString(context, "app_channel"));
        params.put("method", method);
        String api_version = XmlParse.getPapers(context, method, "method");
        if(api_version.equals(method)) {
            api_version = "1";
        }

        params.put("api_version", api_version);
        params.put("ua", getUA(context));
        params.put("nonce", UUID.randomUUID().toString());
        params.put("timestamp", "" + System.currentTimeMillis() / 1000L);
        encode(params);
        Map sortedParams = MapUtil.sort(params);

        try {
            String paramString = URLUtil.map2string(sortedParams);
            LogUtil.i("TestDemo", "param1---->" + paramString);
            String e = URLEncoder.encode(url.toLowerCase(), "utf-8") + URLEncoder.encode(paramString, "utf-8");
            e = e.replaceAll("\\+", "%20");
            e = e.replaceAll("\\!", "%21");
            e = e.replaceAll("\\~", "%7E");
            e = e.replaceAll("\\*", "%2A");
            LogUtil.i("TestDemo", "baseString---->" + e);
            String app_secret = "";
            if(appkey.equals("600019") && sortedParams.containsKey("sessionid")) {
                app_secret = SharedPreferencesUtil.getString(context, "app_secret_login");
            } else {
                app_secret = SharedPreferencesUtil.getString(context, "app_secret");
            }

            String signature = URLEncoder.encode(HmacSha.getSignature(e, app_secret), "utf-8");
            params.put("sign", signature);
            LogUtil.i("totp", "app_secret: " + app_secret);
            LogUtil.i("TestDemo", url + params.toString());
            StringBuffer sb = new StringBuffer();
            Iterator var14 = params.entrySet().iterator();

            while(var14.hasNext()) {
                Entry entry = (Entry)var14.next();
                sb.append("&");
                sb.append((String)entry.getKey());
                sb.append("=");
                sb.append((String)entry.getValue());
            }

            LogUtil.i("TestDemo", url + sb.toString().replaceFirst("&", "?"));
        } catch (Throwable var15) {
            LogUtil.e("GLBS.error", "获取signature失败", var15);
        }

        return url_api + params.toString();
    }

    public static void encode(Map<String, String> params) {
        String UTF8 = "utf-8";
        Iterator it = params.entrySet().iterator();

        while(it.hasNext()) {
            Entry kv = (Entry)it.next();
            String k = (String)kv.getKey();
            String v = (String)kv.getValue();
            LogUtil.i("TestDemo", "param4---->" + k + " * " + v);

            try {
                v = URLEncoder.encode(v, "utf-8");
                v = v.replaceAll("\\+", "%20");
                v = v.replaceAll("\\!", "%21");
                v = v.replaceAll("\\~", "%7E");
                v = v.replaceAll("\\*", "%2A");
                params.put(k, v);
            } catch (Exception var7) {
                throw new RuntimeException("编码参数失败，请检查参数是否合法", var7);
            }
        }

    }

    public static String getImsi(Context context) {
        String imsi = SharedPreferencesUtil.getIMSI(context);
        if(imsi.equals(SharedPreferencesUtil.FAILURE_STRING) && imsi.length() > 0) {
            TelephonyManager telMng = (TelephonyManager)context.getSystemService("phone");
            if(telMng.getSimState() == 5) {
                imsi = telMng.getSubscriberId();
                SharedPreferencesUtil.setIMEI(context, imsi);
            }
        }

        return imsi;
    }

    public static String getImei(Context context) {
        String imei = SharedPreferencesUtil.getIMEI(context);
        if(imei.equals(SharedPreferencesUtil.FAILURE_STRING) && imei.length() > 0) {
            TelephonyManager telMng = (TelephonyManager)context.getSystemService("phone");
            imei = telMng.getDeviceId();
            SharedPreferencesUtil.setIMEI(context, imei);
        }

        return imei;
    }

    public static String getUA(Context context) {
        String ua = "";
        StringBuilder builder = new StringBuilder();
        TelephonyManager tm = (TelephonyManager)context.getSystemService("phone");
        int versionCode = 0;

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (NameNotFoundException var7) {
            var7.printStackTrace();
        }

        builder.append(Build.BRAND).append("^").append(Build.MODEL).append("^").append("Android").append("^").
                append(VERSION.RELEASE).append("^").append(tm.getDeviceId()).append("^").append(tm.getSubscriberId()).
                append("^").append(versionCode);
        ua = builder.toString();
        LogUtil.i("TestDemo", "ua---->" + ua);
        return ua;
    }
}