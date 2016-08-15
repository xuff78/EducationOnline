package com.education.online.act;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.education.online.R;
import com.education.online.http.HttpHandler;
import com.education.online.util.ActUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.StatusBarCompat;

/**
 * Created by 可爱的蘑菇 on 2016/8/15.
 */
public class MainPage extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;
    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        _setHeaderTitle("首页");
        initView();

    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view) {

    }
}
