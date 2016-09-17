package com.education.online.act.Mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.education.online.R;

import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/17.
 */

public class MyQuestion extends BaseFrameAct {


    private TextView askbtn;
    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myquestion);
        _setHeaderTitle("我的提问");
        init();

    }

    private void init() {

        askbtn = (TextView) findViewById(R.id.askbtn);
        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyQuestion.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new MyquestionAdapter(MyQuestion.this,"") );

    }

}

