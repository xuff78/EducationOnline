package com.education.online.act.teacher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.CommentsAdapter;
import com.education.online.adapter.TeacherAlbumAdapter;
import com.education.online.adapter.TeacherBriefAdapter;
import com.education.online.adapter.TeacherCoursesAdapter;

/**
 * Created by Administrator on 2016/8/30.
 */

public class TeacherDetaiPage extends BaseFrameAct implements View.OnClickListener{ private ImageView teacherpotrait;
    private TextView teacherSexual, teacherName, teacherTitles, teachingExperience, identityConfirmed, fansNum, studentNum, praisePercent;
    private LinearLayout brief, subjects, photoalbum, teachercomments, consultingLayout, addToFavoriteLayout;
    private TextView textbrief, textsubjects, textphotoalbum, textteachercomments;
    private  View viewbrief, viewsubjects, viewphotoalbum, viewteachercomments;
    private View lastSelectedview;
    private int lastSelectedPosition;
    private RecyclerView recyclerList;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_main_page);
        _setHeaderGone();
        InitView();
        recyclerList.setAdapter(new TeacherBriefAdapter(this, ""));
    }

    private void InitView() {

        teacherpotrait = (ImageView) findViewById(R.id.teacherpotrait);
        teacherSexual = (TextView) findViewById(R.id.teacherSexual);
        teacherName = (TextView) findViewById(R.id.teacherName);
        teacherTitles = (TextView) findViewById(R.id.teacherTitles);
        teachingExperience = (TextView) findViewById(R.id.teachingExperience);
        identityConfirmed = (TextView) findViewById(R.id.identityConfirmed);

        fansNum = (TextView) findViewById(R.id.fansNum);
        studentNum = (TextView) findViewById(R.id.studentNum);
        praisePercent = (TextView) findViewById(R.id.praisePercent);


        brief = (LinearLayout) findViewById(R.id.brief);
        brief.setOnClickListener(this);
        subjects = (LinearLayout) findViewById(R.id.subjects);
        subjects.setOnClickListener(this);
        photoalbum = (LinearLayout) findViewById(R.id.photoalbum);
        photoalbum.setOnClickListener(this);
        teachercomments = (LinearLayout) findViewById(R.id.teachercomments);
        teachercomments.setOnClickListener(this);

        consultingLayout = (LinearLayout) findViewById(R.id.consultingLayout);
        addToFavoriteLayout = (LinearLayout) findViewById(R.id.addToFavoriteLayout);

        textbrief = (TextView) findViewById(R.id.textbrief);
        textsubjects = (TextView) findViewById(R.id.textsubjects);
        textphotoalbum = (TextView) findViewById(R.id.textphotoalbum);
        textteachercomments = (TextView) findViewById(R.id.textteachercomments);

        viewbrief = findViewById(R.id.viewbrief);
        viewsubjects = findViewById(R.id.viewsubjects );
        viewphotoalbum = findViewById(R.id.viewphotoalbum);
        viewteachercomments = findViewById(R.id.viewteachercomments);

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        lastSelectedview = brief;
        lastSelectedPosition=0;
       // recyclerList.setAdapter(new CommentsAdapter(this, ""));

    }

    public void setStatusFalse(int pos){
        switch (pos)
        {
            case 0:
                textbrief.setTextColor(getResources().getColor(R.color.light_gray));
                viewbrief.setVisibility(View.INVISIBLE);
                break;
            case 1:
                textsubjects.setTextColor(getResources().getColor(R.color.light_gray));
                viewsubjects.setVisibility(View.INVISIBLE);
                break;
            case 2:
                textphotoalbum .setTextColor(getResources().getColor(R.color.light_gray));
                viewphotoalbum .setVisibility(View.INVISIBLE);
                break;
            case 3:
                textteachercomments .setTextColor(getResources().getColor(R.color.light_gray));
                viewteachercomments .setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view != lastSelectedview) {
            setStatusFalse(lastSelectedPosition);
            switch (view.getId()) {
                case R.id.brief:
                    recyclerList.setAdapter(new TeacherBriefAdapter(this, ""));
                    lastSelectedview= brief;
                    lastSelectedPosition=0;
                    textbrief.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewbrief.setVisibility(View.VISIBLE);
                    break;
                case R.id.subjects:
                    recyclerList.setAdapter(new TeacherCoursesAdapter(this, ""));
                    lastSelectedview= subjects;
                    lastSelectedPosition=1;
                    textsubjects.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewsubjects.setVisibility(View.VISIBLE);
                    break;
                case R.id.photoalbum:
                    recyclerList.setAdapter(new TeacherAlbumAdapter(this, ""));
                    lastSelectedview= photoalbum;
                    lastSelectedPosition=2;
                    textphotoalbum.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewphotoalbum.setVisibility(View.VISIBLE);
                    break;
                case R.id.teachercomments:
                    recyclerList.setAdapter(new CommentsAdapter(this, ""));
                    lastSelectedview= teachercomments;
                    lastSelectedPosition=3;
                    textteachercomments.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewteachercomments.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
