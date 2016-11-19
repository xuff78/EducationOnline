package com.education.online.act.discovery.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.QuestionInfoBean;

/**
 * Created by Administrator on 2016/9/29.
 */
public class IwantToAnswer extends BaseFrameAct {

    ImageView icon,picture,answerpicture;
    TextView classname, myquestion, subject,time;
    RelativeLayout addpicturelayout;
    EditText answer;
    private Intent intent;
    QuestionInfoBean questionInfoBean = new QuestionInfoBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iwanttoanswer);
        _setHeaderTitle("我要回答");
        init();

    }
    private void init(){
        intent = getIntent();
  //      layoutManager = new LinearLayoutManager(this);
        questionInfoBean = (QuestionInfoBean) intent.getSerializableExtra("questionInfoBean");


        icon = (ImageView) findViewById(R.id.icon);
        picture = (ImageView) findViewById(R.id.picture);
        answerpicture= (ImageView) findViewById(R.id.answerpicture);
        classname = (TextView) findViewById(R.id.classname);
        myquestion = (TextView) findViewById(R.id.myquestion);
        subject = (TextView) findViewById(R.id.subject);
        time = (TextView) findViewById(R.id.time);
        addpicturelayout = (RelativeLayout) findViewById(R.id.addpicturelayout);
        answer = (EditText) findViewById(R.id.answer);




    }

}
