package com.education.online.act.teacher;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.MainAdapter;
import com.education.online.adapter.RateAdapter;

/**
 * Created by Administrator on 2016/9/1.
 */
public class MyRatePage extends BaseFrameAct {

    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        _setHeaderTitle("我的评价");

        initView();
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new RateAdapter(this));
    }
}
