package com.education.online.act.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.CommentsAdapter;
import com.education.online.fragment.BaseFragment;

/**
 * Created by Administrator on 2016/8/29.
 */
public class PersonalInfo extends BaseFrameAct {

    private TextView realName, teachingTime, subject, educationback, school, major, teacherIntro, briefIntro, experience, tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personnal_profile);

        _setHeaderTitle("个人资料");
        _setHeaderShown();

        initView();
    }

    private void initView() {
        realName = (TextView) findViewById(R.id.realName);
        teachingTime = (TextView) findViewById(R.id.teachingTime);
        subject = (TextView) findViewById(R.id.subject);
        educationback = (TextView) findViewById(R.id.educationback);
        school = (TextView) findViewById(R.id.school);
        major = (TextView) findViewById(R.id.major);
        teacherIntro = (TextView) findViewById(R.id.teacherIntro);
        briefIntro = (TextView) findViewById(R.id.briefIntro);
        experience = (TextView) findViewById(R.id.experience);
        tags = (TextView) findViewById(R.id.tags);

    }

}
