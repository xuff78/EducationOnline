package com.education.online.act.discovery;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/29.
 */
public class IwantToAnswer extends BaseFrameAct {

    ImageView icon,picture,answerpicture;
    TextView classname, myquestion, subject,time;
    RelativeLayout addpicturelayout;
    EditText answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iwanttoanswer);
        _setHeaderTitle("我要回答");
        init();

    }
    private void init(){
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
