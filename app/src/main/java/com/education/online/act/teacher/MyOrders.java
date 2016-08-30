package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.SearchAct;
import com.education.online.adapter.MainAdapter;
import com.education.online.adapter.TeacherOrderAdapter;

/**
 * Created by Administrator on 2016/8/29.
 */
public class MyOrders extends BaseFrameAct implements View.OnClickListener{

    private RecyclerView recyclerList;
    private View typeLayout, courseTypeLayout;
    private TextView selectTypeView, selectStatus, statusAll, statusFinish, statusToPay, statusToComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_orders);

        _setHeaderGone();

        initView();
    }

    private void initView() {
        statusAll= (TextView) findViewById(R.id.statusAll);
        statusAll.setOnClickListener(this);
        selectStatus=statusAll;
        statusFinish= (TextView) findViewById(R.id.statusFinish);
        statusFinish.setOnClickListener(this);
        statusToPay= (TextView) findViewById(R.id.statusToPay);
        statusToPay.setOnClickListener(this);
        statusToComment= (TextView) findViewById(R.id.statusToComment);
        statusToComment.setOnClickListener(this);

        TextView courseTypeTxt1= (TextView) findViewById(R.id.courseTypeTxt1);
        courseTypeTxt1.setOnClickListener(typeListener);
        selectTypeView=courseTypeTxt1;
        findViewById(R.id.courseTypeTxt2).setOnClickListener(typeListener);
        findViewById(R.id.courseTypeTxt3).setOnClickListener(typeListener);

        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new TeacherOrderAdapter(this));
    }

    @Override
    public void onClick(View view) {
        TextView txt= (TextView) view;
        if(txt!=selectStatus) {
            selectStatus.setTextColor(getResources().getColor(R.color.normal_gray));
            txt.setTextColor(getResources().getColor(R.color.normal_blue));
            selectStatus = txt;
            switch (view.getId()) {
                case R.id.statusAll:
                    break;
                case R.id.statusFinish:
                    break;
                case R.id.statusToPay:
                    break;
                case R.id.statusToComment:
                    break;
            }
        }
    }


    View.OnClickListener typeListener=new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            TextView txt= (TextView) view;
            if(txt!=selectTypeView) {
                selectTypeView.setTextColor(getResources().getColor(R.color.normal_gray));
                txt.setTextColor(getResources().getColor(R.color.normal_red));
                selectTypeView = txt;
                switch (view.getId()) {
                    case R.id.courseTypeTxt1:
//                        addListFragment(onlinecoursePage);
                        break;
                    case R.id.courseTypeTxt2:
//                        addListFragment(courseVideoList);
                        break;
                    case R.id.courseTypeTxt3:
//                        addListFragment(courseVideoList);
                        break;
                }
            }
        }
    };
}
