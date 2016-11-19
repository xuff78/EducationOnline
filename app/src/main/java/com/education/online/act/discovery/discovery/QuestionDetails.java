package com.education.online.act.discovery.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.QuestionDetailsitemAdapter;
import com.education.online.bean.AnswerInfoBean;
import com.education.online.bean.AnswerListHolder;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.QuestionInfoBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
public class QuestionDetails extends BaseFrameAct {

    RecyclerView recycleList;
    LinearLayout linearLayout;
    QuestionInfoBean questionInfoBean = new QuestionInfoBean();
    HttpHandler httpHandler;
    QuestionDetailsitemAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Intent intent;
    private boolean onloading = false;
    private int page = 1;
    private String pageSize = "10";
    private AnswerListHolder answerListHolder = new AnswerListHolder();
    private List<AnswerInfoBean> answerInfoBeen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myquestiondetails);
        _setHeaderTitle("问题详情");
        init();
        initHandler();
        httpHandler.getAnswerList(questionInfoBean.getQuestion_id(), pageSize, String.valueOf(page));

    }

    public void init() {
        intent = getIntent();
        layoutManager = new LinearLayoutManager(this);
        questionInfoBean = (QuestionInfoBean) intent.getSerializableExtra("questionInfoBean");
        recycleList = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(QuestionDetails.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleList.setLayoutManager(layoutManager);
        adapter = new QuestionDetailsitemAdapter(this, questionInfoBean,answerListHolder,answerInfoBeen);
        recycleList.setAdapter(adapter);
        recycleList.addOnScrollListener(srcollListener);
        linearLayout = (LinearLayout) findViewById(R.id.layout1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(QuestionDetails.this, IwantToAnswer.class);
                startActivity(intent);
            }
        });
    }

    private void initHandler() {
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {

                super.doSuccess(method, jsonData);
                if (method.equals(Method.getAnswerList)) {
                    Log.d("GetQuestionList", "获取列表成功");
                    onloading = false;
                    // ToastUtils.displayTextShort(AskAndSpeedAnswer.this, "获取列表成功");
                    answerListHolder = JsonUtil.getAnswerListHolder(jsonData);
                    answerInfoBeen.addAll(answerListHolder.getAnswer_infos());
                    adapter.notifyDataSetChanged();
                    page++;
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                if (method.equals(Method.getAnswerList)) {
                    onloading = false;
                    adapter.setLoadingHint("加载失败");

                }
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                if (method.equals(Method.getAnswerList)) {
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
                    if (Integer.parseInt(answerListHolder.getCurrent_page()) < Integer.parseInt(answerListHolder.getPage_total())) {
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

