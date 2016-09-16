package com.education.online.act.Mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.RateListAdapter;
import com.education.online.adapter.WalletHistoryAdapter;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class MyEvaluation extends BaseFrameAct implements View.OnClickListener {


    private RecyclerView recyclerList;
    private TextView fromMe, toMe, toDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_evalution);
        _setHeaderTitle("我的评价");
        init();
    }

    public void init() {
        fromMe = (TextView) findViewById(R.id.fromMe);
        toMe = (TextView) findViewById(R.id.toMe);
        toDo = (TextView) findViewById(R.id.toDo);
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new RateListAdapter(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fromMe:
                break;
            case R.id.toMe:
                break;

        }
    }
}
