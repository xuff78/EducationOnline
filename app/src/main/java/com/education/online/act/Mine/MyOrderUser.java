package com.education.online.act.Mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.TeacherOrderAdapter;
import com.education.online.adapter.UserOrderAdapter;

/**
 * Created by 可爱的蘑菇 on 2016/9/17.
 */
public class MyOrderUser extends BaseFrameAct implements View.OnClickListener{

    private RecyclerView recyclerList;
    private TextView selectTypeView, selectStatus, statusAll, statusFinish, statusToPay, statusToComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_orders);

        _setHeaderTitle("我的订单");

        initView();
    }

    private void initView() {
        findViewById(R.id.courseTypeLayout).setVisibility(View.GONE);
        statusAll= (TextView) findViewById(R.id.statusAll);
        statusAll.setOnClickListener(this);
        selectStatus=statusAll;
        statusFinish= (TextView) findViewById(R.id.statusFinish);
        statusFinish.setOnClickListener(this);
        statusToPay= (TextView) findViewById(R.id.statusToPay);
        statusToPay.setOnClickListener(this);
        statusToComment= (TextView) findViewById(R.id.statusToComment);
        statusToComment.setOnClickListener(this);

        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new UserOrderAdapter(this));
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
}
