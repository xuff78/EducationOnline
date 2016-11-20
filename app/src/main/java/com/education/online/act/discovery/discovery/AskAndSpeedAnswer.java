package com.education.online.act.discovery.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.FirstPage;
import com.education.online.act.Mine.AskAndAnswer;
import com.education.online.act.login.CompleteDataPage;
import com.education.online.act.login.SubjectSelector;
import com.education.online.adapter.AnswersAdapter;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.QuestionInfoBean;
import com.education.online.bean.QuestionListHolder;
import com.education.online.bean.SubjectBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */
public class AskAndSpeedAnswer extends BaseFrameAct implements View.OnClickListener {
    private LinearLayoutManager layoutManager;
    private TextView recentquestion, compeletequestion;
    private RecyclerView recyclerList;
    private LinearLayout sort, subject;
    private HttpHandler httphandler;
    private String query_type = "";
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
        setContentView(R.layout.myquestionsananswer);
        _setHeaderTitle("秒问秒答");
        _setRightHomeText("我的回答", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AskAndSpeedAnswer.this, MyAnswers.class),0x10);
            }
        });

        initHandler();
        init();
        httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page));

    }

    void init() {
        adapter = new AnswersAdapter(this, questionListHolder, questionList);
        layoutManager = new LinearLayoutManager(this);
        recentquestion = (TextView) findViewById(R.id.recentquestion);
        recentquestion.setOnClickListener(this);
        compeletequestion = (TextView) findViewById(R.id.compeletequestion);
        compeletequestion.setOnClickListener(this);
        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        sort = (LinearLayout) findViewById(R.id.sort);
        sort.setOnClickListener(this);
        subject = (LinearLayout) findViewById(R.id.subject);
        subject.setOnClickListener(this);
        intent = new Intent();

        LinearLayoutManager layoutManager = new LinearLayoutManager(AskAndSpeedAnswer.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(adapter);
        adapter.setOnItemClickListener(new AnswersAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int i = (int) view.getTag();
                QuestionInfoBean questionInfoBean = questionList.get(i);
                intent.putExtra("questionInfoBean",questionInfoBean);
                intent.setClass(AskAndSpeedAnswer.this, QuestionDetails.class);
                startActivity(intent);
            }
        });
        recyclerList.addOnScrollListener(srcollListener);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x10) {
            if (resultCode == 0x10) {
                reset();
                httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page));
            }
        }
        if (resultCode == 0x11) {
            SubjectBean subjectBean = (SubjectBean) data.getSerializableExtra(SubjectBean.Name);
            // addClassBean.setName(subjectBean.getSubject_name());
            reset();
            subject_id = subjectBean.getSubject_id();
            httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recentquestion:
                recentquestion.setTextColor(getResources().getColor(R.color.dark_orange));
                compeletequestion.setTextColor(getResources().getColor(R.color.normal));
                status = "";
                reset();
                httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page));
                break;
            case R.id.compeletequestion:
                compeletequestion.setTextColor(getResources().getColor(R.color.dark_orange));
                recentquestion.setTextColor(getResources().getColor(R.color.normal));
                status = "completed";
                reset();
                httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page));
                break;
            case R.id.subject:
                startActivityForResult(new Intent(AskAndSpeedAnswer.this, SubjectSelector.class), 0x10);
                break;
            case R.id.sort:
                break;
        }
    }

    private void reset() {
        page = 1;
        questionList.clear();
        subject_id = "";

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
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    };
}
