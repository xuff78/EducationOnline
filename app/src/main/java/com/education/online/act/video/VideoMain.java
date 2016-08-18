package com.education.online.act.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.SearchAct;
import com.education.online.adapter.VideoMainAdapter;

/**
 * Created by 可爱的蘑菇 on 2016/8/18.
 */
public class VideoMain extends BaseFrameAct{

    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        _setHeaderTitle("视频");
        _setRightHome(R.mipmap.icon_query, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoMain.this, SearchAct.class));
            }
        });

        initView();
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new VideoMainAdapter(this, ""));
    }
}
