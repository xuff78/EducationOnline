package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.education.online.bean.FilterAll;
import com.education.online.bean.FilterInfo;
import com.education.online.bean.SubjectBean;
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
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.MenuPopup;

import org.json.JSONException;

import java.util.ArrayList;
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
    private int type=0;
    private String courseType="live";
    private String is_free=null;
    private String sort=null;
    private String subject_id=null;
    private String searchwords=null;
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
    private CourseUpdate currentCourseFrg;
    private String query_type=Constant.TypeCourse;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getCourseList)){
                    items= JSON.parseObject(JsonUtil.getString(jsonData, "course_info"),
                            new TypeReference<List<CourseBean>>(){});
                    currentCourseFrg.addCourses(items, true);
                }else if(method.equals(Method.updateSortList)){
                }else if(method.equals(Method.updateSortList)){
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_act);

        initHandler();
        _setHeaderGone();
        initView();
        initFrgment();
        addCourseListFragment(onlinecoursePage);
        type = getIntent().getIntExtra("Type", 0);
        if(getIntent().hasExtra(Constant.SearchWords)) {
            if(type==1) {
                query_type=Constant.TypeTeacher;
                handler.getCourseList("underway", courseType, subject_id, null, null, null, null, pageSize, String.valueOf(page), query_type);
            }else {
                searchwords = getIntent().getStringExtra(Constant.SearchWords);
                searchEdt.setText(searchwords);
                searchEdt.setSelection(searchwords.length());
                handler.getCourseList("underway", courseType, null, searchwords, null, null, null, pageSize, String.valueOf(page), query_type);
            }
        }else if(getIntent().hasExtra(Constant.SearchSubject)){
            subject_id = getIntent().getStringExtra(Constant.SearchSubject);
            handler.getCourseList("underway", courseType, subject_id, null, null, null, null, pageSize, String.valueOf(page), query_type);
        }
    }

    private void initFrgment() {
        selectorPage=new SelectorPage();
        ((SelectorPage)selectorPage).setData(this);
        selectorByOrder=new SelectorOrder();
        selectorFilter=new SelectorFilter();

        FilterAll filter=new FilterAll();
        ArrayList<FilterInfo> list=new ArrayList<>();
        setFirstFilter(list);
        filter.setList(list);
        Bundle b=new Bundle();
        b.putSerializable(FilterAll.Name, filter);
        selectorFilter.setArguments(b);
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
                    selectorFilter=new SelectorFilter();
                    FilterAll filter=new FilterAll();
                    ArrayList<FilterInfo> list=new ArrayList<>();
                    if (i == 0) {
                        setFirstFilter(list);
                    } else {
                        FilterInfo info=new FilterInfo();
                        info.setTypeName("教师资质");
                        String[] typenames=new String[]{"教师认证","专业证书"};
                        info.setItemInfo(typenames);
                        list.add(info);
                        FilterInfo info2=new FilterInfo();
                        info2.setTypeName("教龄");
                        String[] typenames2=new String[]{"不限","1-5年","5-10年","10-15年","15年以上"};
                        info2.setItemInfo(typenames2);
                        list.add(info2);
                        FilterInfo info3=new FilterInfo();
                        info3.setTypeName("老师性别");
                        String[] typenames3=new String[]{"不限","男","女"};
                        info3.setItemInfo(typenames3);
                        list.add(info3);
                    }
                    type=i;
                    filter.setList(list);
                    Bundle b=new Bundle();
                    b.putSerializable(FilterAll.Name, filter);
                    selectorFilter.setArguments(b);
                    popup.dismiss();
                }

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
                    if(!searchEdt.getText().toString().trim().equals(""))
                    {
                        if(type==0) {
                            courseTypeLayout.setVisibility(View.VISIBLE);
                            addCourseListFragment(onlinecoursePage);
                        }else {
                            courseTypeLayout.setVisibility(View.GONE);
                            addCourseListFragment(teacherList);
                        }
                    }else
                        ToastUtils.displayTextShort(SearchResultAct.this, "请填写搜索关键字");
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
            if(txt!=selectTypeView) {
                selectTypeView.setTextColor(getResources().getColor(R.color.hard_gray));
                txt.setTextColor(getResources().getColor(R.color.dark_orange));
                selectTypeView = txt;
                switch (view.getId()) {
                    case R.id.courseTypeTxt1:
                        addCourseListFragment(onlinecoursePage);
                        break;
                    case R.id.courseTypeTxt2:
                        addCourseListFragment(courseVideoList);
                        break;
                    case R.id.courseTypeTxt3:
                        addCourseListFragment(courseVideoList);
                        break;
                }
            }
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
        subject_id=subject.getSubject_id();
        page=1;
        handler.getCourseList("underway", courseType, subject_id, searchwords, is_free, null,
                sort, pageSize, String.valueOf(page), query_type);
    }

    @Override
    public void onfinish(ArrayList<FilterInfo> filters) {
        if(type==0){
            FilterInfo freefilter=filters.get(0);
            if(freefilter.getSelection()==0)
                is_free="yes";
            else if(freefilter.getSelection()==1)
                is_free="no";
            FilterInfo day=filters.get(1);
            if(day.getSelection()==0)
                is_free=null;
            else if(day.getSelection()==1){

            }else if(day.getSelection()==2){

            }else if(day.getSelection()==3){

            }
            page=1;
            handler.getCourseList("underway", courseType, subject_id, searchwords, is_free, null,
                    sort, pageSize, String.valueOf(page), query_type);
        }else{

        }
    }

    @Override
    public void onSelected(int pos) {
        if(pos==0){
            sort=null;
        }else if(pos==1){
            sort="hot";
        }else if(pos==2){
            sort="evaluate";
        }else if(pos==3){
            sort="price";
        }
        handler.getCourseList("underway", courseType, subject_id, searchwords, is_free, null,
                sort, pageSize, String.valueOf(page), query_type);
    }
}
