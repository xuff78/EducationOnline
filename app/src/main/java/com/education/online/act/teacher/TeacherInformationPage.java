package com.education.online.act.teacher;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.CommentsAdapter;
import com.education.online.adapter.TeacherMainAdapter;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluatePage;
import com.education.online.bean.TeacherBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */

public class TeacherInformationPage extends BaseFrameAct implements TeacherMainAdapter.EvaluateCallback, View.OnClickListener {

    private LinearLayout consultingLayout, addToFavoriteLayout;
    private RecyclerView recyclerViewList;
    private HttpHandler handler;
    private TeacherBean teacher;
    private TeacherMainAdapter adapter;
    private List<EvaluateBean> evaluations=new ArrayList<>();
    private String usercode="";
    private int page=1;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getUserInfo)){
                    teacher= JSON.parseObject(jsonData, TeacherBean.class);
                    adapter=new TeacherMainAdapter(TeacherInformationPage.this, teacher, evaluations, TeacherInformationPage.this);
                    recyclerViewList.setAdapter(adapter);
                    handler.getEvaluate(usercode, null, page);
                }else if(method.equals(Method.getEvaluate)){
                    page++;
                    String average = JsonUtil.getString(jsonData, "average");
                    if(average.length()==0)
                        average="0";
                    adapter.setAverage(average);
                    String info= JsonUtil.getString(jsonData, "evaluate_details");
                    EvaluatePage page=JSON.parseObject(info, EvaluatePage.class);
                    evaluations.addAll(page.getEvaluate());
                    adapter.notifyDataSetChanged();
                }else if(method.equals(Method.addAttention)){
                    ToastUtils.displayTextShort(TeacherInformationPage.this, "成功关注");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_main_page);

        _setHeaderGone();
        initHandler();
        InitView();
        if(getIntent().hasExtra(Constant.UserCode)){
            usercode = getIntent().getStringExtra(Constant.UserCode);
        }else {
            usercode = SharedPreferencesUtil.getUsercode(this);
            findViewById(R.id.bottomlayout).setVisibility(View.GONE);
        }
        handler.getUserInfo(usercode);
    }

    private void InitView() {
        consultingLayout = (LinearLayout) findViewById(R.id.consultingLayout);
        consultingLayout.setOnClickListener(this);
        addToFavoriteLayout = (LinearLayout) findViewById(R.id.addToFavoriteLayout);
        consultingLayout.setOnClickListener(this);
        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(TeacherInformationPage.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewList.setLayoutManager(layoutManager);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void loadNext() {
        handler.getEvaluate(usercode, null, page);
    }

    @Override
    public void onClick(View view) {
        if(consultingLayout==view){

        }else if(addToFavoriteLayout==view){
            handler.addAttention(usercode);
        }
    }
}
