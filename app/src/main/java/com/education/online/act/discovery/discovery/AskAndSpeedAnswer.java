package com.education.online.act.discovery.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.AnswersAdapter;

/**
 * Created by Administrator on 2016/9/28.
 */
public class AskAndSpeedAnswer extends BaseFrameAct implements View.OnClickListener{

    TextView recentquestion,compeletequestion;
    RecyclerView recyclerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myquestionsananswer);
        _setHeaderTitle("秒问秒答");
        _setRightHomeText("我的回答", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AskAndSpeedAnswer.this, MyAnswers.class));
            }
        });


        init();

    }
void init(){
    recentquestion = (TextView) findViewById(R.id.recentquestion);
    recentquestion.setOnClickListener(this);
    compeletequestion = (TextView) findViewById(R.id.compeletequestion);
    compeletequestion.setOnClickListener(this);
    recyclerList = (RecyclerView) findViewById(R.id.recyclerList);

    LinearLayoutManager layoutManager = new LinearLayoutManager(AskAndSpeedAnswer.this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerList.setLayoutManager(layoutManager);
    AnswersAdapter adapter = new AnswersAdapter(this,"");
    recyclerList.setAdapter(adapter);
    adapter.setOnItemClickListener(new AnswersAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view) {
            startActivity(new Intent(AskAndSpeedAnswer.this,QuestionDetails.class));
        }
    });

}

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recentquestion:
                recentquestion.setTextColor(getResources().getColor(R.color.dark_orange));
                compeletequestion.setTextColor(getResources().getColor(R.color.normal));
            break;
            case R.id.compeletequestion:
                compeletequestion.setTextColor(getResources().getColor(R.color.dark_orange));
                recentquestion.setTextColor(getResources().getColor(R.color.normal));
            break;
        }
    }


}
