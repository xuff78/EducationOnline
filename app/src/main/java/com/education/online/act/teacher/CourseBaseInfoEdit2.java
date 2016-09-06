package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.http.HttpHandler;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CourseBaseInfoEdit2 extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;
    private TextView submitCourseBtn, payBackTxt, insertTxt, paybackSelection1Text, paybackSelection2Text, insert1Text, insert2Text;
    private View arrowRight, arrowRight2, paybackSelection, insertSelection;
    private ImageView paybackSelection1Icon, paybackSelection2Icon, insert1Icon, insert2Icon;
    private int payback=0, insert=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_baseinfo_edit2);

        _setHeaderTitle("课程设置");
        _setRightHomeGone();
        initView();
    }

    private void initView() {
        paybackSelection=findViewById(R.id.paybackSelection);
        insertSelection=findViewById(R.id.insertSelection);
        arrowRight=findViewById(R.id.arrowRight);
        arrowRight2=findViewById(R.id.arrowRight2);
        paybackSelection1Icon= (ImageView) findViewById(R.id.paybackSelection1Icon);
        paybackSelection2Icon= (ImageView) findViewById(R.id.paybackSelection2Icon);
        insert1Icon= (ImageView) findViewById(R.id.insert1Icon);
        insert2Icon= (ImageView) findViewById(R.id.insert2Icon);
        insert1Text= (TextView) findViewById(R.id.insert1Text);
        insert2Text= (TextView) findViewById(R.id.insert2Text);
        insert1Text.setOnClickListener(this);
        insert2Text.setOnClickListener(this);
        payBackTxt= (TextView) findViewById(R.id.payBackTxt);
        insertTxt= (TextView) findViewById(R.id.insertTxt);
        paybackSelection1Text= (TextView) findViewById(R.id.paybackSelection1Text);
        paybackSelection2Text= (TextView) findViewById(R.id.paybackSelection2Text);
        paybackSelection1Text.setOnClickListener(this);
        paybackSelection2Text.setOnClickListener(this);
        findViewById(R.id.setTimeLayout).setOnClickListener(this);
        findViewById(R.id.paybackLayout).setOnClickListener(this);
        findViewById(R.id.insertLayout).setOnClickListener(this);
        findViewById(R.id.submitCourseBtn).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setTimeLayout:
                startActivityForResult(new Intent(CourseBaseInfoEdit2.this, CourseTimeSet.class), 0x10);
                break;
            case R.id.paybackLayout:
                arrowRight.setRotationX(1);
                if(!paybackSelection.isShown())
                    paybackSelection.setVisibility(View.VISIBLE);
                else
                    paybackSelection.setVisibility(View.GONE);
                break;
            case R.id.insertLayout:
                arrowRight2.setRotationX(1);
                if(!insertSelection.isShown())
                    insertSelection.setVisibility(View.VISIBLE);
                else
                    insertSelection.setVisibility(View.GONE);
                break;
            case R.id.submitCourseBtn:

                break;
            case R.id.insert1Text:
                insert1Text.setTextColor(getResources().getColor(R.color.normal_red));
                insert1Icon.setVisibility(View.VISIBLE);
                insert2Text.setTextColor(getResources().getColor(R.color.hard_gray));
                insert2Icon.setVisibility(View.GONE);
                insertTxt.setText(insert1Text.getText().toString());
                break;
            case R.id.insert2Text:
                insert2Text.setTextColor(getResources().getColor(R.color.normal_red));
                insert2Icon.setVisibility(View.VISIBLE);
                insert1Text.setTextColor(getResources().getColor(R.color.hard_gray));
                insert1Icon.setVisibility(View.GONE);
                insertTxt.setText(insert2Text.getText().toString());
                break;
            case R.id.paybackSelection1Text:
                paybackSelection1Text.setTextColor(getResources().getColor(R.color.normal_red));
                paybackSelection1Icon.setVisibility(View.VISIBLE);
                paybackSelection2Text.setTextColor(getResources().getColor(R.color.hard_gray));
                paybackSelection2Icon.setVisibility(View.GONE);
                payBackTxt.setText(paybackSelection1Text.getText().toString());
                break;
            case R.id.paybackSelection2Text:
                paybackSelection2Text.setTextColor(getResources().getColor(R.color.normal_red));
                paybackSelection2Icon.setVisibility(View.VISIBLE);
                paybackSelection1Text.setTextColor(getResources().getColor(R.color.hard_gray));
                paybackSelection1Icon.setVisibility(View.GONE);
                payBackTxt.setText(paybackSelection2Text.getText().toString());
                break;
        }
    }
}
