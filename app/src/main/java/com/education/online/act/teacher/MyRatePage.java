package com.education.online.act.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.discovery.StudentNew;
import com.education.online.act.discovery.Studentintroduction;
import com.education.online.adapter.RateAdapter;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluatePage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class MyRatePage extends BaseFrameAct implements AdapterCallback{

    private RecyclerView recyclerList;
    private int page=1;
    private HttpHandler handler;
    private List<EvaluateBean> evaluations=new ArrayList<>();
    private RateAdapter adapter;
    private String  usercode="";
    private String star=null;
    private View bottomLayout;
    private EditText feedbackEdt;
    private Button feedbackSubmit;
    private EvaluateBean feebackEvaluate;
    private String replyTxt;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getEvaluate)){

                    String average = JsonUtil.getString(jsonData, "average");
                    if(average.length()==0)
                        average="0";
                    String info= JsonUtil.getString(jsonData, "evaluate_details");
                    EvaluatePage pageEvluate= JSON.parseObject(info, EvaluatePage.class);
                    if(page==1){
                        evaluations.clear();
                        evaluations.addAll(pageEvluate.getEvaluate());
                        if(star==null)
                            adapter.setOtherInfo(average, pageEvluate.getTotal(),JsonUtil.getJsonInt(jsonData, "one_ratio"), JsonUtil.getJsonInt(jsonData, "two_ratio"),
                                    JsonUtil.getJsonInt(jsonData, "three_ratio"),JsonUtil.getJsonInt(jsonData, "four_ratio"),JsonUtil.getJsonInt(jsonData, "five_ratio"));
                        adapter.notifyDataSetChanged();
                    }else {
                        evaluations.addAll(pageEvluate.getEvaluate());
                        adapter.notifyDataSetChanged();
                    }
                    page++;
                }else if(method.equals(Method.submitEvaluateReply)){
                    bottomLayout.setVisibility(View.GONE);
                    ToastUtils.displayTextShort(MyRatePage.this, "回复已提交");
                    feebackEvaluate.setReply_info(replyTxt);
                    feedbackEdt.setText("");
                    adapter.notifyDataSetChanged();
                }else if(method.equals(Method.getUserInfo)){
                    Intent intent=new Intent(MyRatePage.this, Studentintroduction.class);
                    intent.putExtra("jsonData", jsonData);
                    ActUtil.startAnimActivity(MyRatePage.this, intent, selectedView, "headIcon");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recylist_with_edit);

        _setHeaderTitle("我的评价");

        initView();
        initHandler();
        usercode= SharedPreferencesUtil.getUsercode(this);
        handler.getEvaluate(usercode, star, page);
    }

    private void initView() {
        feedbackSubmit=(Button)findViewById(R.id.feedbackSubmit);
        feedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replyTxt=feedbackEdt.getText().toString().trim();
                if(replyTxt.length()>0){
                    handler.submitEvaluateReply(feebackEvaluate.getEvaluate_id(), replyTxt);
                }
            }
        });
        feedbackEdt=(EditText)findViewById(R.id.feedbackEdt);
        bottomLayout=findViewById(R.id.bottomLayout);
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter=new RateAdapter(this, evaluations, this, listener);
        recyclerList.setAdapter(adapter);
    }

    private View selectedView=null;

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectedView=view;
            EvaluateBean evaluateBean = (EvaluateBean) view.getTag();
            handler.getUserInfo(evaluateBean.getUsercode());
        }
    };

    @Override
    public void onClick(View v, int i) {
        if(i==0)
            star=null;
        else
            star=String.valueOf(6-i);
        page=1;
        handler.getEvaluate(usercode, star, page);
    }

    @Override
    public void additem() {

    }

    @Override
    public void delitem(View v, int i) {
        int pressPos=i-2;
        bottomLayout.setVisibility(View.VISIBLE);
        feedbackEdt.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(feedbackEdt, InputMethodManager.SHOW_FORCED);
        feebackEvaluate=evaluations.get(pressPos);
    }
}
