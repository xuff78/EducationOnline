package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;

import com.education.online.act.BaseFrameAct;
import com.education.online.act.discovery.discovery.QuestionDetails;
import com.education.online.adapter.MyquestionAdapter;
import com.education.online.bean.AnswerInfoBean;
import com.education.online.bean.AnswerListHolder;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.QuestionInfoBean;
import com.education.online.bean.QuestionListHolder;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;
import com.education.online.util.VideoUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17.
 */

public class MyQuestion extends BaseFrameAct {

    //marker
    private LinearLayoutManager layoutManager;
    private HttpHandler httpHandler;
    private TextView askbtn;
    private RecyclerView recyclerList;
    private int page = 1;
    private String pageSize = "10";
    private String qurey_type = "question";
    private String status = "";
    private String subject_id = "";
    private boolean flag = true;

    private QuestionListHolder questionListHolder = new QuestionListHolder();
    private List<QuestionInfoBean> questionInfoBeens = new ArrayList<>();

    private MyquestionAdapter adapter;

    private boolean onloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myquestion);
        _setHeaderTitle("我的提问");
        initHandler();
        init();
        httpHandler.getQuestionList(qurey_type, status, subject_id, pageSize, String.valueOf(page));
    }

    private void init() {

        askbtn = (TextView) findViewById(R.id.askbtn);
        askbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyQuestion.this, AskAndAnswer.class);
                startActivityForResult(intent, 0x10);
            }
        });
        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyQuestion.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter = new MyquestionAdapter(MyQuestion.this, questionListHolder, questionInfoBeens);
        adapter.setOnItemClickListener(new MyquestionAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int i = (int) view.getTag();
                Intent intent= new Intent();
                intent.putExtra("questionInfoBean",questionInfoBeens.get(i));
                intent.putExtra("flag",flag);
                intent.setClass(MyQuestion.this, QuestionDetails.class);
                startActivityForResult(intent,0x10);////////////do sth;
            }
        });
        recyclerList.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x10) {
            if (resultCode == 0x10) {
                questionInfoBeens.clear();
                page =1;
                httpHandler.getQuestionList(qurey_type, status, subject_id, pageSize, String.valueOf(page));
            }
        }

    }

    public void initHandler() {
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if (method.equals(Method.getQuestionList)) {
                    Log.d("getAnswerList", "获取问题列表成功");
                    questionListHolder = JsonUtil.getQuestionListHolder(jsonData);
                    questionInfoBeens.addAll(questionListHolder.getQuestion_infos());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                {
                    if (method.equals(Method.getQuestionList)) {
                        onloading = false;
                        adapter.setLoadingHint("加载失败");
                    }
                }
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                if (method.equals(Method.getQuestionList)) {
                    onloading = false;
                    adapter.setLoadingHint("加载失败");
                }
            }
        });
        //
    }

    RecyclerView.OnScrollListener srcollListener = new RecyclerView.OnScrollListener() {

        int lastVisibleItem = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()) {
                if (!onloading) {
                    if (true) {
                        onloading = true;
                        adapter.setLoadingHint("正在加载");
                    } else
                        adapter.setLoadingHint("加载完成");
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    };
}

