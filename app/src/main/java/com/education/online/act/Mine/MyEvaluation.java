package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.video.Comment;
import com.education.online.adapter.RateListAdapter;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluatePage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class MyEvaluation extends BaseFrameAct implements View.OnClickListener {


    private RecyclerView recyclerList;
    private TextView fromMe, toMe, toDo;
    private String  usercode="";
    private int page=1;
    private HttpHandler handler;
    private List<EvaluateBean> evaluations=new ArrayList<>();
    private RateListAdapter adapter;
    private View hintLayout;
    private EvaluateBean bean; //选择修改的评论

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
                    EvaluatePage pageEvluate=JSON.parseObject(info, EvaluatePage.class);
                    evaluations.addAll(pageEvluate.getEvaluate());
                    if(page==1){
                        recyclerList.setAdapter(adapter);
                    }else {
                        adapter.notifyDataSetChanged();
                    }
                    page++;
                }else if(method.equals(Method.getEvaluateOthers)){
                    String wait_evaluate = JsonUtil.getString(jsonData, "wait_evaluate");
                    if(wait_evaluate.length()==0)
                        wait_evaluate="0";
                    if(!wait_evaluate.equals("0")){
                        toDo.setText("您有"+wait_evaluate+"个待评价课程，快去给老师评价吧");
                        hintLayout.setVisibility(View.VISIBLE);
                    }
                    String info= JsonUtil.getString(jsonData, "evaluate_details");
                    EvaluatePage pageEvluate=JSON.parseObject(info, EvaluatePage.class);
                    evaluations.addAll(pageEvluate.getEvaluate());
                    if(page==1){
                        recyclerList.setAdapter(adapter);
                    }else {
                        adapter.notifyDataSetChanged();
                    }
                    page++;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_evalution);
        _setHeaderTitle("我的评价");
        init();
        initHandler();
        usercode= SharedPreferencesUtil.getUsercode(this);
        handler.getEvaluateOthers(page);
    }

    public void init() {
        hintLayout=findViewById(R.id.hintLayout);
        hintLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyEvaluation.this, MyOrderUser.class));
            }
        });
        fromMe = (TextView) findViewById(R.id.fromMe);
        toMe = (TextView) findViewById(R.id.toMe);
        toDo = (TextView) findViewById(R.id.toDo);
        toMe.setOnClickListener(this);
        fromMe.setOnClickListener(this);
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter=new RateListAdapter(this, evaluations, listener);
        recyclerList.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        page=1;
        evaluations.clear();
        switch (view.getId()) {
            case R.id.fromMe:
                toMe.setTextColor(getResources().getColor(R.color.hard_gray));
                fromMe.setTextColor(getResources().getColor(R.color.normal_red));
                recyclerList.setAdapter(new RateListAdapter(this, evaluations, listener));
                handler.getEvaluateOthers(page);
                break;
            case R.id.toMe:
                toMe.setTextColor(getResources().getColor(R.color.normal_red));
                fromMe.setTextColor(getResources().getColor(R.color.hard_gray));
                recyclerList.setAdapter(new RateListAdapter(this, evaluations, listener));
                handler.getEvaluate(usercode, null, page);
                break;
        }
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            bean = evaluations.get((Integer) view.getTag());
            Intent intent=new Intent(MyEvaluation.this, Comment.class);
            intent.putExtra("courseImg", bean.getAvatar());
            intent.putExtra("courseName", bean.getCourse_name());
            intent.putExtra("courseIntroduction", "");
            intent.putExtra("course_id", "");
            intent.putExtra("evaluate_id", bean.getEvaluate_id());
            intent.putExtra("message", bean.getInfo());
            intent.putExtra("star", bean.getStar());
            startActivityForResult(intent, 0x11);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x22){
            bean.setStar(data.getStringExtra("star"));
            bean.setInfo(data.getStringExtra("message"));
            adapter.notifyDataSetChanged();
        }
    }
}
