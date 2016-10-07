package com.education.online.act.discovery.discovery;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.MyAnswersAdapter;

/**
 * Created by Administrator on 2016/9/29.
 */
public class MyAnswers extends BaseFrameAct {
    RecyclerView recycleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrapcontentrecyclerview);
        _setHeaderTitle("我的回答");
        recycleList = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleList.setLayoutManager(layoutManager);
        recycleList.setAdapter(new MyAnswersAdapter(this,""));


    }
}
