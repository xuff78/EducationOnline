package com.education.online.act.Mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.InterestingAdapter;
import com.education.online.adapter.RateAdapter;
import com.education.online.bean.SubjectBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MyInteresting extends BaseFrameAct {

    private RecyclerView recyclerList;
    private InterestingAdapter adapter;
    private HttpHandler handler;
    private ArrayList<SubjectBean> cates=new ArrayList<>();
    private ArrayList<SubjectBean> oldinteresting =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interest_layout);

        _setHeaderTitle("我的兴趣");
        findViewById(R.id.submitCourseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String requestStr="";
                LinkedHashMap<String, SubjectBean> interest = adapter.getInterest();
                for(SubjectBean bean:oldinteresting){
                    if(interest.containsKey(bean.getSubject_id())){
                        interest.remove(bean.getSubject_id());
                    }else{
                        requestStr=requestStr+bean.getSubject_id()+"_0,";
                    }
                }
                for(String key:interest.keySet()){
                    SubjectBean bean=interest.get(key);
                    requestStr=requestStr+bean.getSubject_id()+"_1,";
                }
                if(requestStr.length()>0) {
                    handler.editInterest(requestStr.substring(0, requestStr.length() - 1));
                }else
                    ToastUtils.displayTextShort(MyInteresting.this, "尚未添加任何兴趣");
            }
        });
        initView();
        initHandler();
        handler.getInterestList();
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
    }

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData){
                if(method.equals(Method.getInterestList)){
                    cates= JSON.parseObject(getIntent().getStringExtra("jsonData"), new TypeReference<ArrayList<SubjectBean>>(){});
                    if(jsonData.length()>0)
                        oldinteresting=JSON.parseObject(jsonData, new TypeReference<ArrayList<SubjectBean>>(){});
                    adapter=new InterestingAdapter(MyInteresting.this, cates, oldinteresting);
                    recyclerList.setAdapter(adapter);
                }else if(method.equals(Method.editInterest)){
                    ToastUtils.displayTextShort(MyInteresting.this, "修改完成");
                    finish();
                }
            }
        });
    }
}
