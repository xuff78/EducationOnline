package com.education.online.act.teacher;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.view.View;
import android.view.animation.AlphaAnimation;
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
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */

public class TeacherInformationPage extends BaseFrameAct implements TeacherMainAdapter.EvaluateCallback, View.OnClickListener,
        AppBarLayout.OnOffsetChangedListener {

    private LinearLayout consultingLayout, addToFavoriteLayout;
    private RecyclerView recyclerViewList;
    private HttpHandler handler;
    private TeacherBean teacher=new TeacherBean();
    private TeacherMainAdapter adapter;
    private List<EvaluateBean> evaluations=new ArrayList<>();
    private String usercode="";
    private ImageView starIcon, backBtn;
    private int page=1;
    private LinearLayoutManager layoutManager;

    private ImageView teacherpotrait, headIcon2;
    private TextView teacherSexual, teacherName, teacherTitles, teachingExperience, identityConfirmed, fansNum, studentNum, praisePercent;
    private LinearLayout brief, subjects, photoalbum, teachercomments;
    private TextView textbrief, textsubjects, textphotoalbum, textteachercomments;
    private View viewbrief, viewsubjects, viewphotoalbum, viewteachercomments;
    private View lastSelectedview;
    private int lastSelectedPosition;
    private AppBarLayout mAppBarLayout;
    private View mTitleContainer, toolBarLayout;
    private TextView mTitle;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getUserInfo)){
                    TeacherBean teacherInfo= JSON.parseObject(jsonData, TeacherBean.class);
                    ActUtil.updateInfo(TeacherBean.class, teacher, teacherInfo);
                    
                    inittData();
                    teacher.setPhoto_album(teacherInfo.getPhoto_album());
                    teacher.setCourse_info(teacherInfo.getCourse_info());
                    adapter.notifyDataSetChanged();

                    if(teacher.getIs_attention().equals("0"))
                        starIcon.setImageResource(R.mipmap.icon_star);
                    else if(teacher.getIs_attention().equals("1"))
                        starIcon.setImageResource(R.mipmap.icon_star_red);
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
                    if(teacher.getIs_attention().equals("0")) {
                        starIcon.setImageResource(R.mipmap.icon_star_red);
                        teacher.setIs_attention("1");
                        ToastUtils.displayTextShort(TeacherInformationPage.this, "成功关注");
                    }else if(teacher.getIs_attention().equals("1")) {
                        starIcon.setImageResource(R.mipmap.icon_star);
                        teacher.setIs_attention("0");
                        ToastUtils.displayTextShort(TeacherInformationPage.this, "取消关注");
                    }
                }
            }
        });
    }

    private void inittData() {
        String imgUrl=ImageUtil.getImageUrl(teacher.getAvatar());
        ImageLoader.getInstance().displayImage(imgUrl, teacherpotrait);
        ImageLoader.getInstance().displayImage(imgUrl, headIcon2);
        if(teacher.getGender().equals("1")){
            teacherSexual.setText("男");
        }else if(teacher.getGender().equals("0")){
            teacherSexual.setText("女");
        }
        teacherName.setText(teacher.getName());
        mTitle.setText(teacher.getName());
        teacherTitles.setText(teacher.getSpecialty());
        teachingExperience.setText(teacher.getWork_time()+"年教龄");
        if(teacher.getIs_validate().equals("1"))
            identityConfirmed.setText("已认证");
        else
            identityConfirmed.setText("未认证");
        fansNum.setText(teacher.getAttention_count());
        studentNum.setText(teacher.getStudent_count());
        praisePercent.setText(teacher.getGood_evaluate_ratio());
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
            if(getIntent().hasExtra("Avatar"))
                teacher.setAvatar(getIntent().getStringExtra("Avatar"));
            if(getIntent().hasExtra("Name"))
                teacher.setName(getIntent().getStringExtra("Name"));
        }else {
            usercode = SharedPreferencesUtil.getUsercode(this);
            findViewById(R.id.bottomlayout).setVisibility(View.GONE);
        }
        adapter=new TeacherMainAdapter(TeacherInformationPage.this, teacher, evaluations, TeacherInformationPage.this);
        recyclerViewList.setAdapter(adapter);
        handler.getUserInfo(usercode);
        startAlphaAnimation(toolBarLayout, 0, View.INVISIBLE);
    }

    private void InitView() {
        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        toolBarLayout = findViewById(R.id.toolBarLayout);
        mTitleContainer = findViewById(R.id.imagelayout);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.appbarLayout);
        mAppBarLayout.addOnOffsetChangedListener(this);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        consultingLayout = (LinearLayout) findViewById(R.id.consultingLayout);
        consultingLayout.setOnClickListener(this);
        addToFavoriteLayout = (LinearLayout) findViewById(R.id.addToFavoriteLayout);
        addToFavoriteLayout.setOnClickListener(this);
        starIcon= (ImageView) findViewById(R.id.starIcon);
        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewList);
        layoutManager = new LinearLayoutManager(TeacherInformationPage.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewList.setLayoutManager(layoutManager);

        headIcon2 = (ImageView) findViewById(R.id.headIcon2);
        teacherpotrait = (ImageView) findViewById(R.id.teacherpotrait);
        teacherSexual = (TextView) findViewById(R.id.teacherSexual);
        teacherName = (TextView) findViewById(R.id.teacherName);
        teacherTitles = (TextView) findViewById(R.id.teacherTitles);
        teachingExperience = (TextView) findViewById(R.id.teachingExperience);
        identityConfirmed = (TextView) findViewById(R.id.identityConfirmed);

        brief = (LinearLayout) findViewById(R.id.brief);
        brief.setOnClickListener(menuListener);
        subjects = (LinearLayout) findViewById(R.id.subjects);
        subjects.setOnClickListener(menuListener);
        photoalbum = (LinearLayout) findViewById(R.id.photoalbum);
        photoalbum.setOnClickListener(menuListener);
        teachercomments = (LinearLayout) findViewById(R.id.teachercomments);
        teachercomments.setOnClickListener(menuListener);

        fansNum = (TextView) findViewById(R.id.fansNum);
        studentNum = (TextView) findViewById(R.id.studentNum);
        praisePercent = (TextView) findViewById(R.id.praisePercent);

        textbrief = (TextView) findViewById(R.id.textbrief);
        textsubjects = (TextView) findViewById(R.id.textsubjects);
        textphotoalbum = (TextView) findViewById(R.id.textphotoalbum);
        textteachercomments = (TextView) findViewById(R.id.textteachercomments);

        viewbrief = findViewById(R.id.viewbrief);
        viewsubjects = findViewById(R.id.viewsubjects);
        viewphotoalbum = findViewById(R.id.viewphotoalbum);
        viewteachercomments = findViewById(R.id.viewteachercomments);
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
        if(R.id.consultingLayout==view.getId()){

        }else if(R.id.addToFavoriteLayout==view.getId()){
            handler.addAttention(usercode);
        }
    }

    View.OnClickListener menuListener=new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (view != lastSelectedview) {
                setStatusFalse(lastSelectedPosition);
                switch (view.getId()) {
                    case R.id.brief:
                        lastSelectedview = brief;
                        lastSelectedPosition = 0;
                        textbrief.setTextColor(textbrief.getResources().getColor(R.color.dark_orange));
                        viewbrief.setVisibility(View.VISIBLE);
                        adapter.setItemType(1);
                        break;
                    case R.id.subjects:
                        lastSelectedview = subjects;
                        lastSelectedPosition = 1;
                        textsubjects.setTextColor(textbrief.getResources().getColor(R.color.dark_orange));
                        viewsubjects.setVisibility(View.VISIBLE);
                        adapter.setItemType(2);
                        break;
                    case R.id.photoalbum:

                        lastSelectedview = photoalbum;
                        lastSelectedPosition = 2;
                        textphotoalbum.setTextColor(textbrief.getResources().getColor(R.color.dark_orange));
                        viewphotoalbum.setVisibility(View.VISIBLE);
                        adapter.setItemType(3);
                        break;
                    case R.id.teachercomments:
                        lastSelectedview = teachercomments;
                        lastSelectedPosition = 3;
                        textteachercomments.setTextColor(textteachercomments.getResources().getColor(R.color.dark_orange));
                        viewteachercomments.setVisibility(View.VISIBLE);
                        adapter.setItemType(4);
                        break;
                }
            }
        }
    };

    public void setStatusFalse(int pos) {
        switch (pos) {
            case 0:
                textbrief.setTextColor(textbrief.getResources().getColor(R.color.light_gray));
                viewbrief.setVisibility(View.INVISIBLE);
                break;
            case 1:
                textsubjects.setTextColor(textsubjects.getResources().getColor(R.color.light_gray));
                viewsubjects.setVisibility(View.INVISIBLE);
                break;
            case 2:
                textphotoalbum.setTextColor(textphotoalbum.getResources().getColor(R.color.light_gray));
                viewphotoalbum.setVisibility(View.INVISIBLE);
                break;
            case 3:
                textteachercomments.setTextColor(textteachercomments.getResources().getColor(R.color.light_gray));
                viewteachercomments.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        LogUtil.i("appbar", "appbar offset:  "+offset+ "   percentage:  "+percentage);
//        handleAlphaOnTitle(percentage);
        percentage=percentage-0.5f+percentage/2;
        if(percentage<0)
            percentage=0;
        toolBarLayout.setAlpha(percentage);
        backBtn.setAlpha(1-percentage);
//        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(toolBarLayout, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
                LogUtil.i("appbar", "setVisiable");
            }
        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(toolBarLayout, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
                LogUtil.i("appbar", "setInvisiable");
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
