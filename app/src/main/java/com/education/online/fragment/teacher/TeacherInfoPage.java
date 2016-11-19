package com.education.online.fragment.teacher;

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
import com.education.online.adapter.CommentsAdapter;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Great Gao on 2016/11/8.
 */
public  class TeacherInfoPage extends BaseFragment implements View.OnClickListener {
    private EvaluateListBean evaluateListBean = new EvaluateListBean();
    private List<EvaluateBean> evaluateList = new ArrayList<>();
    ImageView teacherpotrait;
    TextView teacherSexual, teacherName, teacherTitles, teachingExperience,identityConfirmed,fansNum,studentNum,praisePercent;
    RecyclerView recyclerList ;
    private CourseDetailBean courseDetailBean = new CourseDetailBean();
    LinearLayout addToFavoriteLayout, consultingLayout,brief, subjects, photoalbum,teachercomments ;
    View viewbrief, viewsubjects, viewphotoalbum, viewteachercomments;
    TextView textbrief, textsubjects, textphotoalbum, textteachercomments;
    private View lastSelectedview;
    private int lastSelectedPosition;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_main_page, container, false);

        initView(view);
        recyclerList.setAdapter(new CommentsAdapter(getActivity(),courseDetailBean ,evaluateList));
        return view;
    }


    private void initView(View v) {
        recyclerList = (RecyclerView) v.findViewById(R.id.courseRecycleView);

        teacherpotrait= (ImageView) v.findViewById(R.id.teacherpotrait);
        teacherSexual = (TextView) v.findViewById(R.id.teacherSexual);
        teacherName= (TextView) v.findViewById(R.id.teacherName);
        teacherTitles = (TextView) v.findViewById(R.id.teacherTitles);
        teachingExperience = (TextView) v.findViewById(R.id.teachingExperience);
        identityConfirmed = (TextView) v.findViewById(R.id.identityConfirmed);
        fansNum= (TextView) v.findViewById(R.id.fansNum);
        studentNum = (TextView) v.findViewById(R.id.studentNum);
        praisePercent = (TextView) v.findViewById(R.id.praisePercent);

        addToFavoriteLayout = (LinearLayout) v.findViewById(R.id.addfavoritelayout);
        consultingLayout = (LinearLayout) v.findViewById(R.id.consultingLayout);

        brief = (LinearLayout) v.findViewById(R.id.brief);
        brief.setOnClickListener(this);
        subjects = (LinearLayout) v.findViewById(R.id.subjects);
        subjects.setOnClickListener(this);
        photoalbum = (LinearLayout) v.findViewById(R.id.photoalbum);
        photoalbum.setOnClickListener(this);
        teachercomments = (LinearLayout) v.findViewById(R.id.teachercomments);
        teachercomments.setOnClickListener(this);

        viewbrief = v.findViewById(R.id.viewbrief);
        viewsubjects = v.findViewById(R.id.viewsubjects);
        viewteachercomments = v.findViewById(R.id.viewteachercomments);
        viewphotoalbum= v.findViewById(R.id.viewphotoalbum);

        textbrief = (TextView) v.findViewById(R.id.textbrief);
        textsubjects = (TextView) v.findViewById(R.id.textsubjects);
        textphotoalbum = (TextView) v.findViewById(R.id.textphotoalbum);
        textteachercomments = (TextView) v.findViewById(R.id.textteachercomments);

        //
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        lastSelectedview = brief;
        lastSelectedPosition=0;
        evaluateList.addAll(evaluateListBean.getEvaluateList());

    }
    public void setStatusFalse(int pos){
        switch (pos)
        {
            case 0:
                viewbrief.setVisibility(View.INVISIBLE);
                textbrief.setTextColor(getResources().getColor(R.color.light_gray));

                break;
            case 1:
                viewsubjects.setVisibility(View.INVISIBLE);
                textsubjects.setTextColor(getResources().getColor(R.color.light_gray));
                break;
            case 2:
                viewphotoalbum.setVisibility(View.INVISIBLE);
                textphotoalbum.setTextColor(getResources().getColor(R.color.light_gray));
                break;
            case 3:
                viewteachercomments.setVisibility(View.INVISIBLE);
                textteachercomments.setTextColor(getResources().getColor(R.color.light_gray));
                break;
        }
    }



    @Override
    public void onClick(View view) {
        if (view != lastSelectedview) {
            setStatusFalse(lastSelectedPosition);
            switch (view.getId()) {
                case R.id.brief:
                    recyclerList.setAdapter(new CommentsAdapter(getActivity(), courseDetailBean,evaluateList));
                    lastSelectedview= brief;
                    lastSelectedPosition=0;
                    textbrief.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewbrief.setVisibility(View.VISIBLE);
                    break;
                case R.id.subjects:
                    recyclerList.setAdapter(new CommentsAdapter(getActivity(), courseDetailBean,evaluateList));
                    lastSelectedview= subjects;
                    lastSelectedPosition=1;
                    textsubjects.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewsubjects.setVisibility(View.VISIBLE);

                    break;
                case R.id.photoalbum:
                    recyclerList.setAdapter(new CommentsAdapter(getActivity(), courseDetailBean,evaluateList));
                    lastSelectedview= photoalbum;
                    lastSelectedPosition=2;
                    textphotoalbum.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewphotoalbum.setVisibility(View.VISIBLE);
                    break;
                case R.id.teachercomments:
                    recyclerList.setAdapter(new CommentsAdapter(getActivity(), courseDetailBean,evaluateList));
                    lastSelectedview= teachercomments;
                    lastSelectedPosition=2;
                    textteachercomments.setTextColor(getResources().getColor(R.color.dark_orange));
                    viewteachercomments.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
