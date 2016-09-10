package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.CourseTimeAdapter;
import com.education.online.bean.ArraryCourseTimeBean;
import com.education.online.bean.CourseTimeBean;
import com.education.online.http.HttpHandler;
import com.education.online.inter.WhellCallback;
import com.education.online.util.LogUtil;
import com.education.online.view.WheelAddressSelectorDialog;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/6.
 */
//实现了wheelCallback 接口
public class CourseTimeSet extends BaseFrameAct implements View.OnClickListener, WhellCallback {

    HttpHandler handler;
    private TextView submitCourseBtn, uploadBtn, subjectTxt, priceTxt, joinNum;
    private EditText timesetDesc;
    private LinearLayout courseLayout;
    private WheelAddressSelectorDialog dialog;
    private ArrayList<CourseTimeBean> courses=new ArrayList<>();
    private LayoutInflater inflater;
    private ListView courseList;
    private CourseTimeAdapter adapter;
    private int modifyPos=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_timeset);

        _setHeaderTitle("教学计划");
        _setRightHomeGone();
        initView();
        inflater=LayoutInflater.from(this);
    }

    private void initView() {
        timesetDesc= (EditText) findViewById(R.id.timesetDesc);
        courseList=(ListView) findViewById(R.id.courseList);

        View footer = LayoutInflater.from(this).inflate(R.layout.course_edit_footer, null);
        footer.findViewById(R.id.addOneTimeLayout).setOnClickListener(this);
        footer.findViewById(R.id.addMutiTimeLayout).setOnClickListener(this);
        courseList.addFooterView(footer);
        adapter=new CourseTimeAdapter(this, courses, this);
        courseList.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addOneTimeLayout:
                //添加一次课时弹出窗口进行选择
                dialog=new WheelAddressSelectorDialog(CourseTimeSet.this, CourseTimeSet.this);
                dialog.show();
                break;
            case R.id.addMutiTimeLayout:
               Intent intent = new Intent(CourseTimeSet.this, AddSerialClass.class);
               startActivityForResult(intent,0x11);
                break;
            case R.id.delBtn:
                int posdel = (int) view.getTag();
                courses.remove(posdel);
                adapter.notifyDataSetChanged();
                break;
            case R.id.modifyBtn:
                modifyPos = (int) view.getTag();
                dialog=new WheelAddressSelectorDialog(CourseTimeSet.this, CourseTimeSet.this, courses.get(modifyPos));
                dialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x11){
           ArrayList<CourseTimeBean> temp = new ArrayList<> ();
            ArraryCourseTimeBean list = new ArraryCourseTimeBean();
            list = (ArraryCourseTimeBean) data.getSerializableExtra("TimeListArray") ;
            temp = list.getTimelist();
            courses.addAll(temp);
            adapter.notifyDataSetChanged();//通知列表更新
        }
    }

    @Override
    public void onFinish(CourseTimeBean bean) {
        LogUtil.i("Date", "日期: "+bean.getDatetime());
        courses.add(bean);
        adapter.notifyDataSetChanged();//通知列表更新
    }

    @Override
    public void onChanged(CourseTimeBean bean) {
        courses.set(modifyPos, bean);
        adapter.notifyDataSetChanged();
    }
}
