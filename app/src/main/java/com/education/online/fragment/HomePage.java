package com.education.online.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.education.online.R;
import com.education.online.adapter.MainAdapter;
import com.education.online.bean.CategoryBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/16.
 */
public class HomePage extends BaseFragment {

    private RecyclerView recyclerList;
    private HttpHandler handler;
    private String jsonStr;

    private void initHandler() {
        handler = new HttpHandler(getActivity(), new CallBack(getActivity()) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                jsonStr=jsonData;
                recyclerList.setAdapter(new MainAdapter(getActivity(), jsonStr));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        initView(view);
        if(jsonStr==null) {
            initHandler();
            handler.getHomepage();
        }else{
            recyclerList.setAdapter(new MainAdapter(getActivity(), jsonStr));
        }
        return view;
    }

    private void initView(View v) {
        recyclerList=(RecyclerView)v.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
    }
}
