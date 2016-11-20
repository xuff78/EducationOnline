package com.education.online.act.discovery.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.AnswersAdapter;
import com.education.online.adapter.MyAnswersAdapter;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.QuestionInfoBean;
import com.education.online.bean.QuestionListHolder;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public class MyAnswers extends BaseFrameAct {
    private LinearLayoutManager layoutManager;
    RecyclerView recycleList;
    private HttpHandler httphandler;
    private String query_type = "answer";
    private String status = "";
    private int page = 1;
    private String pageSize = "10";
    private String subject_id = "";
    private AnswersAdapter adapter;
    private boolean onloading = false;
    private List<QuestionInfoBean> questionList = new ArrayList();
    private QuestionListHolder questionListHolder = new QuestionListHolder();


    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchparrentrecyclerview);
        _setHeaderTitle("我的回答");
        initHandler();
        initView();
        httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page));
    }

    private void initView() {
        intent = new Intent();
        recycleList = (RecyclerView) findViewById(R.id.recyclerList);
         layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleList.setLayoutManager(layoutManager);
        adapter = new AnswersAdapter(this, questionListHolder, questionList);
        recycleList.setAdapter(adapter);
        adapter.setOnItemClickListener(new AnswersAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int i = (int) view.getTag();
                QuestionInfoBean questionInfoBean = questionList.get(i);
                intent.putExtra("questionInfoBean", questionInfoBean);
                intent.setClass(MyAnswers.this, QuestionDetails.class);
                startActivity(intent);
            }
        });

        recycleList.addOnScrollListener(srcollListener);

    }

    private void initHandler() {
        httphandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {

                super.doSuccess(method, jsonData);
                if (method.equals(Method.getQuestionList)) {
                    Log.d("GetQuestionList", "获取列表成功");
                    onloading = false;
                    // ToastUtils.displayTextShort(AskAndSpeedAnswer.this, "获取列表成功");
                    questionListHolder = JsonUtil.getQuestionListHolder(jsonData);
                    questionList.addAll(questionListHolder.getQuestion_infos());
                    adapter.notifyDataSetChanged();
                    page++;
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                if (method.equals(Method.getQuestionList)) {
                    onloading = false;
                    adapter.setLoadingHint("加载失败");

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
    }

    RecyclerView.OnScrollListener srcollListener = new RecyclerView.OnScrollListener() {

        int lastVisibleItem = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()) {
                if (!onloading) {
                    if (Integer.parseInt(questionListHolder.getCurrent_page()) < Integer.parseInt(questionListHolder.getPage_total())) {
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
            int totalItemCount = layoutManager.getItemCount();
            if(totalItemCount>0)
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    };
}
