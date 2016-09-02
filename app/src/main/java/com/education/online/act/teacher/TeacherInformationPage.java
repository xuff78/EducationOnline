package com.education.online.act.teacher;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.CommentsAdapter;
import com.education.online.adapter.TeacherMainAdapter;

/**
 * Created by Administrator on 2016/9/1.
 */

public class TeacherInformationPage extends BaseFrameAct {

    private LinearLayout consultingLayout, addToFavoriteLayout;
    private RecyclerView recyclerViewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_main_page);

        _setHeaderGone();
        InitView();
    }

    private void InitView() {


        consultingLayout = (LinearLayout) findViewById(R.id.consultingLayout);
        addToFavoriteLayout = (LinearLayout) findViewById(R.id.addToFavoriteLayout);


        recyclerViewList = (RecyclerView) findViewById(R.id.recyclerViewList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(TeacherInformationPage.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewList.setLayoutManager(layoutManager);

        recyclerViewList.setAdapter(new TeacherMainAdapter(this, ""));

    }
}
