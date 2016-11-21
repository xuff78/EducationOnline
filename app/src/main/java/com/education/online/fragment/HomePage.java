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
import com.education.online.R;
import com.education.online.act.SearchAct;
import com.education.online.adapter.ActivityTopGalleryAdapter;
import com.education.online.adapter.MainAdapter;
import com.education.online.bean.CategoryBean;
import com.education.online.bean.HomePageInfo;
import com.education.online.bean.JsonMessage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.view.ExtendedViewPager;

import org.json.JSONException;

import java.util.ArrayList;

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

    private void initHandler() {
        handler = new HttpHandler(getActivity(), new CallBack(getActivity()) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                jsonStr=jsonData;
                info= JSON.parseObject(jsonData, HomePageInfo.class);
                adapter=new MainAdapter(getActivity(), info);
                recyclerList.setAdapter(adapter);
//                mViewPager.setAdapter(new ActivityTopGalleryAdapter(getActivity(), info.getAdverts_info()));
//                mViewPager.startAutoScroll();
                mSwipeLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                mSwipeLayout.setRefreshing(false);
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                mSwipeLayout.setRefreshing(false);
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
//        TextView toolbarTxtRight= (TextView) v.findViewById(R.id.toolbarTxtRight);
//        String city=SharedPreferencesUtil.getString(getActivity(), "my_address");
//        if(!city.equals(SharedPreferencesUtil.FAILURE_STRING)){
//            toolbarTxtRight.setText(city);
//        }
        v.findViewById(R.id.toolbarIconRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActUtil.startAnimActivity(getActivity(), new Intent(getActivity(), SearchAct.class));
            }
        });

//        mViewPager = (ExtendedViewPager) v.findViewById(R.id.galleryImgs);
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
//        toolbar.setTitle("");
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        recyclerList=(RecyclerView)v.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        final View headerLayout=v.findViewById(R.id.headerLayout);
        recyclerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                listScrollY+=dy;
                float alpha=Math.abs(Float.valueOf(listScrollY))/firstItemHeight;
                headerLayout.setAlpha(alpha);

                LogUtil.i("test", "listScrollY: "+listScrollY);
            }
        });

//        AppBarLayout appBarLayout= (AppBarLayout) v.findViewById(R.id.appbarLayout);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
//                float alpha=Math.abs(Float.valueOf(i))/appBarLayout.getTotalScrollRange();
//                headerLayout.setAlpha(alpha);
//                LogUtil.i("test", "toolbar alpha: "+alpha);
//            }
//        });
    }

    @Override
    public void onRefresh() {
        handler.getHomepage();
    }
}
