package com.education.online.weex;


import com.education.online.retrofit.RequestStrUtil;
import com.education.online.util.LogUtil;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/16.
 */

public class WeexHttpModule extends WXModule {

    @WXModuleAnno(moduleMethod = true,runOnUIThread = true)
    public void requestHttp(String jsonData, final JSCallback cb) {
        HashMap<String, String> paramMap=new HashMap<>();
        String url="";
        try {
            JSONObject obj=new JSONObject(jsonData);
            if(!obj.isNull("url"))
                url=obj.getString("url");
            if(!obj.isNull("params")){
                JSONArray array=obj.getJSONArray("params");
                for (int i=0;i<array.length();i++){
                    JSONObject subjson=array.getJSONObject(i);
                    paramMap.put(subjson.getString("name"), subjson.getString("value"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestStrUtil.getRequestBody(mWXSDKInstance.getContext().getApplicationContext(), url, paramMap);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                LogUtil.i("weex http", str);
                Map<String,String> data=new HashMap<>();
                data.put("jsonData",str);
                LogUtil.i("test", "test return: "+str);
                cb.invoke(data);

            }
        });
    }
}
