package com.education.online.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.adapter.CourseAdapter;
import com.education.online.adapter.MainAdapter;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.util.Constant;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/25.
 */

public class CoursePage extends BaseFragment {

    private RecyclerView recyclerList;
    private CourseDetailBean courseDetailBean;
    private EvaluateListBean evaluateListBean;
    private Activity activity;

    public void setCourseDetailBean(CourseDetailBean courseDetailBean) {
        this.courseDetailBean = courseDetailBean;
    }
    public void setEvaluateListBean(EvaluateListBean evaluateListBean) {
        this.evaluateListBean = evaluateListBean;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);
            initView(view);
        return view;
    }


    private void initView(View v) {
        recyclerList=(RecyclerView)v.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
//        recyclerList.setAdapter(new CourseAdapter(getActivity(), courseDetailBean,evaluateListBean));
    }
}
