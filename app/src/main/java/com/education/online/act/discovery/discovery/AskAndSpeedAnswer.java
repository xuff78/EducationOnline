package com.education.online.act.discovery.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.AnswersAdapter;
import com.education.online.bean.FilterInfo;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.QuestionInfoBean;
import com.education.online.bean.QuestionListHolder;
import com.education.online.bean.SubjectBean;
import com.education.online.fragment.dialog.SelectorOrder;
import com.education.online.fragment.dialog.SelectorPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.DialogCallback;
import com.education.online.util.JsonUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */
public class AskAndSpeedAnswer extends BaseFrameAct implements View.OnClickListener, DialogCallback, SelectorPage.CourseSelector{
    private LinearLayoutManager layoutManager;
    private TextView recentquestion, compeletequestion;
    private RecyclerView recyclerList;
    private LinearLayout sort, subject;
    private HttpHandler httphandler;
    private String query_type = "";
    private String status = "";
    private String sort_type = "";
    private int page = 1;
    private String pageSize = "10";
    private String subject_id = "";
    private AnswersAdapter adapter;
    private boolean onloading = false;
    private List<QuestionInfoBean> questionList = new ArrayList();
    private QuestionListHolder questionListHolder = new QuestionListHolder();
    private String[] names=new String[]{"全部", "最新提问","回答人数由高到低","奖励积分由高到低"};
    private View transblackBg, filterDetailLayout;
    private Fragment selectorPage, selectorByOrder, currentUsedFrg;
    private boolean filterShown=false;
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
        httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page), sort_type);

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

        transblackBg=findViewById(R.id.transblackBg);
        transblackBg.setOnClickListener(this);
        filterDetailLayout= (FrameLayout) findViewById(R.id.fragment_frame);
        selectorByOrder=new SelectorOrder();

        selectorPage=new SelectorPage();
        ((SelectorPage)selectorPage).setData(this);

        Bundle b2=new Bundle();
        b2.putInt("type", 0);
        b2.putStringArray("Names", names);
        selectorByOrder.setArguments(b2);
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

    int dialogType=0;
    private void openOrCloseFilterLayout(Fragment frg, int typeNew){
        if(dialogType!=typeNew){
            openFragment(R.id.fragment_frame, frg);
            filterDetailLayout.setVisibility(View.VISIBLE);
            transblackBg.setVisibility(View.VISIBLE);
        }else if(filterShown){
            removePage();
            filterDetailLayout.setVisibility(View.GONE);
            transblackBg.setVisibility(View.GONE);
        }else{
            openFragment(R.id.fragment_frame, frg);
            filterDetailLayout.setVisibility(View.VISIBLE);
            transblackBg.setVisibility(View.VISIBLE);
        }
        dialogType=typeNew;
    }

    @Override
    public void openFragment(int res, Fragment frg) {
        filterShown=true;
        currentUsedFrg=frg;
        super.openFragment(res, frg);
    }

    private void removePage(){
        filterShown=false;
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.remove(currentUsedFrg);
        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x10) {
            if (resultCode == 0x10) {
                reset();
                httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page), sort_type);
            }
        }
        if (resultCode == 0x11) {
            SubjectBean subjectBean = (SubjectBean) data.getSerializableExtra(SubjectBean.Name);
            // addClassBean.setName(subjectBean.getSubject_name());
            reset();
            subject_id = subjectBean.getSubject_id();
            httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page), sort_type);
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
                httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page), sort_type);
                break;
            case R.id.compeletequestion:
                compeletequestion.setTextColor(getResources().getColor(R.color.dark_orange));
                recentquestion.setTextColor(getResources().getColor(R.color.normal));
                status = "completed";
                reset();
                httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page), sort_type);
                break;
//            case R.id.subject:
//                startActivityForResult(new Intent(AskAndSpeedAnswer.this, SubjectSelector.class), 0x10);
//                break;
//            case R.id.sort:
//                break;
            case R.id.subject:
                openOrCloseFilterLayout(selectorPage, 0);
                break;
            case R.id.sort:
                openOrCloseFilterLayout(selectorByOrder, 1);
                break;
        }
    }

    private void reset() {
        page = 1;
        questionList.clear();
//        subject_id = "";
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

    @Override
    public void onSelected(SubjectBean subject) {
        closeDialog();
        reset();
        subject_id = subject.getSubject_id();
        httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page), sort_type);
    }

    @Override
    public void closeDialog() {
        removePage();
        filterDetailLayout.setVisibility(View.GONE);
        transblackBg.setVisibility(View.GONE);
    }

    @Override
    public void onfinish(ArrayList<FilterInfo> filters) {

    }

    @Override
    public void onSelected(int pos) {
        if(pos==0){
            sort_type="";
        }else if(pos==1){
            sort_type="new";
        }else if(pos==2){
            sort_type="hot";
        }else if(pos==3){
            sort_type="integral";
        }
        reset();
        httphandler.getQuestionList(query_type, status, subject_id, pageSize, String.valueOf(page), sort_type);
    }
}
