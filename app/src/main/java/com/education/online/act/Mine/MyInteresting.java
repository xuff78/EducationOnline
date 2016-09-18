package com.education.online.act.Mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.InterestingAdapter;
import com.education.online.adapter.RateAdapter;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MyInteresting extends BaseFrameAct {

    private RecyclerView recyclerList;
    private InterestingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interest_layout);

        _setHeaderTitle("我的兴趣");
        findViewById(R.id.submitCourseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addInterest();
            }
        });
        initView();
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        adapter=new InterestingAdapter(this);
        recyclerList.setAdapter(adapter);
    }
}
