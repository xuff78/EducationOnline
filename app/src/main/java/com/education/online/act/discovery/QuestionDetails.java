package com.education.online.act.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/29.
 */
public class QuestionDetails extends BaseFrameAct{

    RecyclerView recycleList;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myquestiondetails);
        _setHeaderTitle("问题详情");
        init();
    }
    public void init(){
        recycleList = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(QuestionDetails.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleList.setLayoutManager(layoutManager);
        recycleList.setAdapter(new QuestionDetailsitemAdapter(this,""));

        linearLayout = (LinearLayout) findViewById(R.id.layout1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionDetails.this,IwantToAnswer.class));
            }
        });
        }
}
