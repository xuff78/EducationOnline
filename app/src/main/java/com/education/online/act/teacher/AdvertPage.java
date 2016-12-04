package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.AdvertAdapter;
import com.education.online.bean.AdvertsBean;
import com.education.online.bean.JsonMessage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;

import org.json.JSONException;

/**
 * Created by Great Gao on 2016/12/4.
 */
public class AdvertPage extends BaseFrameAct {

    private HttpHandler handler;
    private RecyclerView recyclerView;
    private HttpHandler httpHandler;
    private Intent intent;
    private AdvertAdapter advertAdapter ;
    private String jsonStr;
    private AdvertsBean infos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advert);
        intent = getIntent();
        initHandler();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (intent.hasExtra("advert_id")) {
            String advert_id = intent.getStringExtra("advert_id");
            httpHandler.getAdvert(advert_id);
        }

    }

    private void initHandler() {
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getAdvert)){
                    infos = JsonUtil.getAdvert(jsonData);
                    advertAdapter = new AdvertAdapter(AdvertPage.this,infos);
                    recyclerView.setAdapter(advertAdapter);
                    _setHeaderTitle(infos.getTitle());
                }

            }
        });
    }
}
