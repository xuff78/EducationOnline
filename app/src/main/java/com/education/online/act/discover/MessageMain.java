package com.education.online.act.discover;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.VideoMainAdapter;

/**
 * Created by 可爱的蘑菇 on 2016/9/29.
 */
public class MessageMain extends BaseFrameAct implements View.OnClickListener{

    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_top_menu);

        _setHeaderTitle("消息");
        initView();
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new VideoMainAdapter(this, ""));
    }

    @Override
    public void onClick(View view) {

    }
}