package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.AddClassBean;
import com.education.online.bean.ArraryCourseTimeBean;
import com.education.online.bean.JsonMessage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.util.DialogUtil;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CourseBaseInfoEdit2 extends BaseFrameAct implements View.OnClickListener{

    private AddClassBean addClassBean;
    HttpHandler handler;
    private TextView submitCourseBtn, payBackTxt, insertTxt, paybackSelection1Text, paybackSelection2Text, insert1Text, insert2Text;
    private View arrowRight, arrowRight2, paybackSelection, insertSelection;
    private ImageView paybackSelection1Icon, paybackSelection2Icon, insert1Icon, insert2Icon;
    private int payback=0, insert=0;
    private Intent intent;
    private HttpHandler httpHandler;
    private ArraryCourseTimeBean arraryCourseTimeBean ;
    private String time_len;
    private int type;


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
        intent = getIntent();
        addClassBean = (AddClassBean) intent.getSerializableExtra("addClassBean");
        type = intent.getIntExtra("type",0);
        arraryCourseTimeBean = new ArraryCourseTimeBean();

        initiHandler();

    }
    public void initiHandler(){
        httpHandler = new HttpHandler(this, new CallBack(this)
        {
            @Override
            public void onSuccess(String method, String jsonMessage) throws JSONException {
                super.onSuccess(method, jsonMessage);
                Intent intent = new Intent();
                intent.putExtra("success",true);
                setResult(0x14,intent);
                finish();


            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                //
            }
        });
  }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setTimeLayout:
                intent.setClass(CourseBaseInfoEdit2.this, CourseTimeSet.class);
                intent.putExtra("arrayCourseTimeBean",arraryCourseTimeBean);
                startActivityForResult(intent, 0x10);
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
                if(type==2&&addClassBean.getCourseware_time().length()!=0)//直播课且有时间
                {
                    httpHandler.addClass(addClassBean);
                }else
                {
                    Toast.makeText(CourseBaseInfoEdit2.this,"请填写完整信息",Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.insert1Text:
                insert1Text.setTextColor(getResources().getColor(R.color.normal_red));
                insert1Icon.setVisibility(View.VISIBLE);
                insert2Text.setTextColor(getResources().getColor(R.color.hard_gray));
                insert2Icon.setVisibility(View.GONE);
                insertTxt.setText(insert1Text.getText().toString());
                addClassBean.setTransfer("non");
                break;
            case R.id.insert2Text:
                insert2Text.setTextColor(getResources().getColor(R.color.normal_red));
                insert2Icon.setVisibility(View.VISIBLE);
                insert1Text.setTextColor(getResources().getColor(R.color.hard_gray));
                insert1Icon.setVisibility(View.GONE);
                insertTxt.setText(insert2Text.getText().toString());
                addClassBean.setTransfer("always");
                break;
            case R.id.paybackSelection1Text:
                paybackSelection1Text.setTextColor(getResources().getColor(R.color.normal_red));
                paybackSelection1Icon.setVisibility(View.VISIBLE);
                paybackSelection2Text.setTextColor(getResources().getColor(R.color.hard_gray));
                paybackSelection2Icon.setVisibility(View.GONE);
                payBackTxt.setText(paybackSelection1Text.getText().toString());
                addClassBean.setRefund("always");
                break;
            case R.id.paybackSelection2Text:
                paybackSelection2Text.setTextColor(getResources().getColor(R.color.normal_red));
                paybackSelection2Icon.setVisibility(View.VISIBLE);
                paybackSelection1Text.setTextColor(getResources().getColor(R.color.hard_gray));
                paybackSelection1Icon.setVisibility(View.GONE);
                payBackTxt.setText(paybackSelection2Text.getText().toString());
                addClassBean.setRefund("non");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x11)
        {
            arraryCourseTimeBean = (ArraryCourseTimeBean) data.getSerializableExtra("arrayCourseTimeBean");
            String temp = data.getStringExtra("courseware_time");
            String temp1 = data.getStringExtra("time_len");
            float temp2 = Float.parseFloat(temp1);
            int temp3 = (int)(temp2*60);
            temp1=String.valueOf(temp3);
            addClassBean.setTime_len(temp1);
            addClassBean.setCourseware_time(temp);
        }

    }
}
