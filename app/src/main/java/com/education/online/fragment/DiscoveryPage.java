package com.education.online.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.online.R;
import com.education.online.act.teacher.AuthMenu;
import com.education.online.act.teacher.MyOrders;
import com.education.online.act.teacher.MyRatePage;
import com.education.online.act.teacher.TeacherCourseStart;
import com.education.online.act.teacher.TeacherHomePage;

/**
 * Created by 可爱的蘑菇 on 2016/9/25.
 */
public class DiscoveryPage extends BaseFragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_layout, container, false);

        initView(view);
        return view;
    }

    private void initView(View v) {
        v.findViewById(R.id.questionAndAnswerLayout).setOnClickListener(this);
        v.findViewById(R.id.growSpaceLayout).setOnClickListener(this);
        v.findViewById(R.id.signLayout).setOnClickListener(this);
        v.findViewById(R.id.sayHiLayout).setOnClickListener(this);
        v.findViewById(R.id.inviteLayout).setOnClickListener(this);
        v.findViewById(R.id.testLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homepageEditLayout:
//                startActivity(new Intent(getActivity(), TeacherHomePage.class));
                break;
            case R.id.orderLayout:
//                startActivity(new Intent(getActivity(), MyOrders.class));
                break;
            case R.id.authLayout:
//                startActivity(new Intent(getActivity(), AuthMenu.class));
                break;
            case R.id.commentLayout:
//                startActivity(new Intent(getActivity(), MyRatePage.class));
                break;
            case R.id.settingLayout:
//                startActivity(new Intent(getActivity(), MyRatePage.class));
                break;
            case R.id.courseManagerLayout:
//                startActivity(new Intent(getActivity(), TeacherCourseStart.class));
                break;
        }
    }
}
