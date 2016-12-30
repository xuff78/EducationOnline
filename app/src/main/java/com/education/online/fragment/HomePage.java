package com.education.online.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.SearchAct;
import com.education.online.adapter.ActivityTopGalleryAdapter;
import com.education.online.adapter.MainAdapter;
import com.education.online.bean.CategoryBean;
import com.education.online.bean.CourseBean;
import com.education.online.bean.HomePageInfo;
import com.education.online.bean.JsonMessage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.view.ExtendedViewPager;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2016/8/16.
 */
public class HomePage extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerList;
    private HttpHandler handler;
    private String jsonStr;
    private MainAdapter adapter;
//    private ExtendedViewPager mViewPager;
    private HomePageInfo info;
    private int firstItemHeight;
    private int listScrollY=0;
    private SwipeRefreshLayout mSwipeLayout;
    private int page=1;
    private ArrayList<CourseBean> courses=new ArrayList<>();
    private boolean onloading=false, complete=false;
    private LinearLayoutManager layoutManager;

    private void initHandler() {
        handler = new HttpHandler(getActivity(), new CallBack(getActivity()) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if (method.equals(Method.getHomePage)) {
                    jsonStr = jsonData;
                    info = JSON.parseObject(jsonData, HomePageInfo.class);
                    adapter = new MainAdapter(getActivity(), info, courses);
                    recyclerList.setAdapter(adapter);
                    mSwipeLayout.setRefreshing(false);
                    page=1;
                    listScrollY=0;
                    courses.clear();
                    handler.getRecommentCourses(page);
                }else if (method.equals(Method.getRecommentCourses)) {
                    int totalpage= JsonUtil.getJsonInt(jsonData, "page_total");
                    if(totalpage==page){
                        complete=true;
                    }else
                        page++;
                    courses.addAll(JSON.parseObject(JsonUtil.getString(jsonData, "course_info"), new TypeReference<ArrayList<CourseBean>>(){}));
                    adapter.notifyDataSetChanged();
                    onloading=false;
                }
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                if (method.equals(Method.getHomePage))
                    mSwipeLayout.setRefreshing(false);
                else if (method.equals(Method.getRecommentCourses)) {
                    onloading=false;
                    adapter.setLoadingHint("加载失败");
                }
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                if (method.equals(Method.getHomePage))
                    mSwipeLayout.setRefreshing(false);
                else if (method.equals(Method.getRecommentCourses)) {
                    onloading=false;
                    adapter.setLoadingHint("加载失败");
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment, container, false);

        initView(view);
        firstItemHeight= ImageUtil.dip2px(getActivity(), 200);
        if(jsonStr==null) {
            initHandler();
            handler.getHomepage();
            mSwipeLayout.setRefreshing(true);
        }else{
            recyclerList.setAdapter(adapter);
//            mViewPager.setAdapter(new ActivityTopGalleryAdapter(getActivity(), info.getAdverts_info()));
//            mViewPager.startAutoScroll();
        }
        return view;
    }

    private void initView(View v) {

        mSwipeLayout = (SwipeRefreshLayout)v.findViewById(R.id.id_swipe_ly);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_dark,
                android.R.color.holo_green_light);

        v.findViewById(R.id.toolbarIconRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActUtil.startAnimActivity(getActivity(), new Intent(getActivity(), SearchAct.class));
            }
        });

        recyclerList=(RecyclerView)v.findViewById(R.id.recyclerList);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        final View headerLayout=v.findViewById(R.id.headerLayout);
        recyclerList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int lastVisibleItem=0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                listScrollY+=dy;
                float alpha=Math.abs(Float.valueOf(listScrollY))/firstItemHeight;
                headerLayout.setAlpha(alpha);


                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                LogUtil.i("test", "listScrollY: "+listScrollY);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    if(!onloading){
                        if(!complete){
                            onloading = true;
                            handler.getRecommentCourses(page);
                            adapter.setLoadingHint("正在加载");
                        }else
                            adapter.setLoadingHint("");
                    }
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        handler.getHomepage();
    }
}
