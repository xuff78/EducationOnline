package com.education.online.act;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Explode;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.bean.CourseBean;
import com.education.online.bean.CourseFilter;
import com.education.online.bean.FilterAll;
import com.education.online.bean.FilterInfo;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.TeacherWithCourse;
import com.education.online.fragment.CourseVideoList;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.fragment.TeacherList;
import com.education.online.fragment.dialog.SelectorFilter;
import com.education.online.fragment.dialog.SelectorOrder;
import com.education.online.fragment.dialog.SelectorPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.CourseUpdate;
import com.education.online.inter.DialogCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.MenuPopup;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SearchResultAct extends BaseFrameAct implements View.OnClickListener, DialogCallback, SelectorPage.CourseSelector{

    private TextView typeTxt, selectTypeView;
    private EditText searchEdt;
    private View typeLayout, menuBtn1, menuBtn2, menuBtn3, transblackBg, courseTypeLayout;
    private FrameLayout filterDetailLayout;
    private MenuPopup popup;
    private String[] typeStrs={"课程", "老师"};
    private int type=0; //0课程， 1教师
    private CourseFilter courseFilter=new CourseFilter();
    private Fragment selectorPage, selectorByOrder, selectorFilter, currentUsedFrg;
    private boolean filterShown=false;
    private OnlineCoursePage onlinecoursePage = new OnlineCoursePage();
    private CourseVideoList courseVideoList = new CourseVideoList();
    private CourseVideoList coursewareList = new CourseVideoList();
    private TeacherList teacherList = new TeacherList();
    private HttpHandler handler;
    private String pageSize="20";
    private int page=1;
    private List<CourseBean> items=new ArrayList<>();
    private List<TeacherWithCourse> teacheritems=new ArrayList<>();
    private CourseUpdate currentCourseFrg;
    private String searchWord="";
//    private String query_type=Constant.TypeCourse;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getCourseList)){
                    String courseInfo=JsonUtil.getString(jsonData, "course_info");
                    if(type==0) {
                        items = JSON.parseObject(courseInfo, new TypeReference<List<CourseBean>>() {});
                        currentCourseFrg.addCourses(items, true);
                    }else if(type==1){
                        teacheritems = JSON.parseObject(courseInfo,  new TypeReference<List<TeacherWithCourse>>() {});
                        teacherList.addTeacherCourses(teacheritems, true);
                    }
                    page++;

                    String catesInfo=JsonUtil.getString(jsonData, "subject_info");
                    ArrayList<SubjectBean> cates= JSON.parseObject(catesInfo, new TypeReference<ArrayList<SubjectBean>>(){});
                    ((SelectorPage)selectorPage).setCateInfo(cates);
                    courseFilter.setPage(String.valueOf(page));
                }else if(method.equals(Method.updateSortList)){
                }else if(method.equals(Method.updateSortList)){
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21) {
            getWindow().setEnterTransition(new Explode().setDuration(600));
            getWindow().setExitTransition(new Explode().setDuration(400));
        }
        setContentView(R.layout.search_result_act);

        initHandler();
        _setHeaderGone();
        initView();
        type = getIntent().getIntExtra("Type", 0);
        initFilter();
        if(getIntent().hasExtra(Constant.SearchSubject)){
            addCourseListFragment(onlinecoursePage);
            String subject_id = getIntent().getStringExtra(Constant.SearchSubject);
            courseFilter.setSubject_ids(subject_id);
            ((SelectorPage)selectorPage).setLastSelection(subject_id);
        }else if(getIntent().hasExtra(Constant.SearchWords)) {
            String searchwords = getIntent().getStringExtra(Constant.SearchWords);
            searchEdt.setText(searchwords);
            searchEdt.setSelection(searchwords.length());
            courseFilter.setKey_word(searchwords);
            if(type==1) {
                addCourseListFragment(teacherList);
                courseTypeLayout.setVisibility(View.GONE);
            }else {
                addCourseListFragment(onlinecoursePage);
            }
        }
        handler.getCourseList(courseFilter);
    }

    private void initFilter() {
        selectorFilter=new SelectorFilter();
        FilterAll filter=new FilterAll();
        ArrayList<FilterInfo> list=new ArrayList<>();
        courseFilter=new CourseFilter();
        String[] names=null;
        if (type == 0) {
            courseTypeLayout.setVisibility(View.VISIBLE);
            addCourseListFragment(onlinecoursePage);
            setFirstFilter(list);
            names=new String[]{"智能排序","人气最高","评价最高","价格最低"};
        } else {
            courseTypeLayout.setVisibility(View.GONE);
            addCourseListFragment(teacherList);
            courseFilter.setCourse_type(null);
            courseFilter.setQuery_type("teacher");
            FilterInfo info=new FilterInfo();
            info.setTypeName("教师资质");
            String[] typenames=new String[]{"教师认证","专业证书","双重认证"};
            info.setItemInfo(typenames);
            list.add(info);
            FilterInfo info2=new FilterInfo();
            info2.setTypeName("教龄");
            String[] typenames2=new String[]{"1-5年","5-10年","10-15年","15年以上"};
            info2.setItemInfo(typenames2);
            list.add(info2);
            FilterInfo info3=new FilterInfo();
            info3.setTypeName("老师性别");
            String[] typenames3=new String[]{"男","女"};
            info3.setItemInfo(typenames3);
            list.add(info3);
            names=new String[]{"智能排序","人气最高","评价最高",""};
        }
        filter.setList(list);
        Bundle b=new Bundle();
        b.putSerializable(FilterAll.Name, filter);
        selectorFilter.setArguments(b);

        selectorPage=new SelectorPage();
        ((SelectorPage)selectorPage).setData(SearchResultAct.this);
        selectorByOrder=new SelectorOrder();
        Bundle b2=new Bundle();
        b2.putStringArray("Names", names);
        selectorByOrder.setArguments(b2);
    }

    private void addCourseListFragment(CourseUpdate page) {
        currentCourseFrg=page;
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_list, page);
        ft.commit();
    }

    private void initView() {
        popup = new MenuPopup(SearchResultAct.this, typeStrs, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                typeTxt.setText(typeStrs[i]);
                if(type!=i) {
                    type=i;
                    initFilter();
                    handler.getCourseList(courseFilter);
                    popup.dismiss();
                }else
                    popup.dismiss();

            }
        });

        courseTypeLayout=findViewById(R.id.courseTypeLayout);
        typeTxt= (TextView) findViewById(R.id.typeTxt);
        typeTxt.setText(typeStrs[0]);
        searchEdt= (EditText) findViewById(R.id.searchEdt);
        typeLayout=findViewById(R.id.typeLayout);
        typeLayout.setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId== EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_UNSPECIFIED||actionId==EditorInfo.IME_ACTION_SEARCH){
                    page=1;
                    courseFilter.setPage(String.valueOf(page));
                    String searchwords=searchEdt.getText().toString();
                    courseFilter.setKey_word(searchwords);
                    if(!searchWord.equals(searchwords))
                        courseFilter.setSubject_ids(null);
                    searchWord=searchwords;
                    handler.getCourseList(courseFilter);
                }
                return false;
            }
        });

        transblackBg=findViewById(R.id.transblackBg);
        transblackBg.setOnClickListener(this);
        filterDetailLayout= (FrameLayout) findViewById(R.id.fragment_frame);
        menuBtn1=findViewById(R.id.menuBtn1);
        menuBtn1.setOnClickListener(this);
        menuBtn2=findViewById(R.id.menuBtn2);
        menuBtn2.setOnClickListener(this);
        menuBtn3=findViewById(R.id.menuBtn3);
        menuBtn3.setOnClickListener(this);
        TextView courseTypeTxt1= (TextView) findViewById(R.id.courseTypeTxt1);
        courseTypeTxt1.setOnClickListener(typeListener);
        selectTypeView=courseTypeTxt1;
        findViewById(R.id.courseTypeTxt2).setOnClickListener(typeListener);
        findViewById(R.id.courseTypeTxt3).setOnClickListener(typeListener);
    }

    private void setFirstFilter(ArrayList<FilterInfo> list){
        FilterInfo info=new FilterInfo();
        info.setTypeName("价格");
        String[] typenames=new String[]{"免费","不免费"};
        info.setItemInfo(typenames);
        list.add(info);
        FilterInfo info2=new FilterInfo();
        info2.setTypeName("开课时间");
        String[] typenames2=new String[]{"不限","一周","1月内","2月内"};
        info2.setItemInfo(typenames2);
        list.add(info2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.typeLayout:
                popup.showPopupWindow(typeLayout);
                break;
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.clearBtn:
                break;
            case R.id.menuBtn1:
                openOrCloseFilterLayout(selectorPage, 0);
                break;
            case R.id.menuBtn2:
                openOrCloseFilterLayout(selectorByOrder, 1);
                break;
            case R.id.menuBtn3:
                openOrCloseFilterLayout(selectorFilter, 2);
                break;
            case R.id.transblackBg:
                closeDialog();
                break;
        }
    }

    View.OnClickListener typeListener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            TextView txt= (TextView) view;
            page=1;
            courseFilter.setPage(String.valueOf(page));
            if(txt!=selectTypeView) {
                selectTypeView.setTextColor(getResources().getColor(R.color.hard_gray));
                txt.setTextColor(getResources().getColor(R.color.dark_orange));
                selectTypeView = txt;
                switch (view.getId()) {
                    case R.id.courseTypeTxt1:
                        courseFilter.setCourse_type("live");
                        addCourseListFragment(onlinecoursePage);
                        break;
                    case R.id.courseTypeTxt2:
                        courseFilter.setCourse_type("video");
                        addCourseListFragment(courseVideoList);
                        break;
                    case R.id.courseTypeTxt3:
                        courseFilter.setCourse_type("courseware");
                        addCourseListFragment(courseVideoList);
                        break;
                }
            }
            handler.getCourseList(courseFilter);
        }
    };

    int dialogType=0;
    private void openOrCloseFilterLayout(Fragment frg, int typeNew){
        if(dialogType!=typeNew){
            changePage(frg);
            filterDetailLayout.setVisibility(View.VISIBLE);
            transblackBg.setVisibility(View.VISIBLE);
        }else if(filterShown){
            removePage();
            filterDetailLayout.setVisibility(View.GONE);
            transblackBg.setVisibility(View.GONE);
        }else{
            changePage(frg);
            filterDetailLayout.setVisibility(View.VISIBLE);
            transblackBg.setVisibility(View.VISIBLE);
        }
        dialogType=typeNew;
    }

    private void changePage(Fragment frg){
        filterShown=true;
        currentUsedFrg=frg;
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_frame, frg);
        ft.commit();
    }

    private void removePage(){
        filterShown=false;
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.remove(currentUsedFrg);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(filterShown) {
            filterDetailLayout.setVisibility(View.GONE);
            transblackBg.setVisibility(View.GONE);
            filterShown=false;
        }else
            super.onBackPressed();
    }

    @Override
    public void closeDialog() {
        removePage();
        filterDetailLayout.setVisibility(View.GONE);
        transblackBg.setVisibility(View.GONE);
    }

    @Override
    public void onSelected(SubjectBean subject) {
        closeDialog();
        courseFilter.setSubject_ids(subject.getSubject_id());
        page=1;
        courseFilter.setPage(String.valueOf(page));
        handler.getCourseList(courseFilter);
    }

    @Override
    public void onfinish(ArrayList<FilterInfo> filters) {
        if(type==0){
            FilterInfo freefilter=filters.get(0);
            if(freefilter.getSelection()==0)
                courseFilter.setIs_free("no");
            else if(freefilter.getSelection()==1)
                courseFilter.setIs_free("yes");
            else
                courseFilter.setIs_free(null);
            FilterInfo day=filters.get(1);
            if(day.getSelection()==0) {
                courseFilter.setStart_date(null);
                courseFilter.setStart_date(null);
            }else if(day.getSelection()==1){
                courseFilter.setStart_date(ActUtil.getDate());
                courseFilter.setStart_date(ActUtil.getChangedDate(Calendar.DAY_OF_YEAR, 7));
            }else if(day.getSelection()==2){
                courseFilter.setStart_date(ActUtil.getDate());
                courseFilter.setStart_date(ActUtil.getChangedDate(Calendar.MONTH, 1));
            }else if(day.getSelection()==3){
                courseFilter.setStart_date(ActUtil.getDate());
                courseFilter.setStart_date(ActUtil.getChangedDate(Calendar.MONTH, 2));
            }else {
                courseFilter.setStart_date(null);
                courseFilter.setStart_date(null);
            }
        }else if(type==1){
            FilterInfo authFilter=filters.get(0);
            if(authFilter.getSelection()==0) {
                courseFilter.setIs_tc_validate("yes");
                courseFilter.setIs_specialty_validate("no");
            }else if(authFilter.getSelection()==1) {
                courseFilter.setIs_tc_validate("no");
                courseFilter.setIs_specialty_validate("yes");
            }else if(authFilter.getSelection()==2) {
                courseFilter.setIs_tc_validate("yes");
                courseFilter.setIs_specialty_validate("yes");
            }else{
                courseFilter.setIs_tc_validate("no");
                courseFilter.setIs_specialty_validate("no");
            }
            FilterInfo worktime=filters.get(1);
            if(worktime.getSelection()==0) {
                courseFilter.setWork_time("1,5");
            }else if(worktime.getSelection()==1) {
                courseFilter.setWork_time("5,10");
            }else if(worktime.getSelection()==2) {
                courseFilter.setWork_time("10,15");
            }else if(worktime.getSelection()==3) {
                courseFilter.setWork_time("15,100");
            }else{
                courseFilter.setWork_time(null);
            }
            FilterInfo gender=filters.get(2);
            if(gender.getSelection()==0) {
                courseFilter.setGender("male");
            }else if(gender.getSelection()==1) {
                courseFilter.setGender("female");
            }else{
                courseFilter.setGender(null);
            }
        }
        page=1;
        courseFilter.setPage(String.valueOf(page));
        handler.getCourseList(courseFilter);
    }

    @Override
    public void onSelected(int pos) {
        if(pos==0){
            courseFilter.setSort(null);
        }else if(pos==1){
            courseFilter.setSort("hot");
        }else if(pos==2){
            courseFilter.setSort("evaluate");
        }else if(pos==3){
            courseFilter.setSort("price");
        }
        page=1;
        courseFilter.setPage(String.valueOf(page));
        handler.getCourseList(courseFilter);
    }
}
