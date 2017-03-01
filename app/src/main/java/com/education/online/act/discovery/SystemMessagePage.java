package com.education.online.act.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.Mine.MyCourseMuti;
import com.education.online.adapter.SystemMessageAdapter;
import com.education.online.util.JsonUtil;

/**
 * Created by 可爱的蘑菇 on 2016/10/4.
 */
public class SystemMessagePage extends BaseFrameAct implements View.OnClickListener{

    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        AVAnalytics.trackAppOpened(getIntent());
        Intent noticeIntent=getIntent();
        String typeData=noticeIntent.getStringExtra("com.avos.avoscloud.Data");
        int type=JsonUtil.getJsonInt(typeData, "custom-key");
        switch (type){
            case 1:
                Intent i=noticeIntent;
                i.setClass(this, MyCourseMuti.class);
                i.putExtra("Type", 0);
                startActivity(i);
                finish();
                break;
            case 2:
            case 3:
                setContentView(R.layout.home_page);
                _setHeaderTitle("系统通知");
                initView();
                break;
        }
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new SystemMessageAdapter(this));
    }

    @Override
    public void onClick(View view) {

    }
}
