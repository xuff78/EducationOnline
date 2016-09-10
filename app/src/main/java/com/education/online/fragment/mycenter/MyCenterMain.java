package com.education.online.fragment.mycenter;

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
import com.education.online.fragment.BaseFragment;

/**
 * Created by 可爱的蘑菇 on 2016/9/10.
 */
public class MyCenterMain extends BaseFragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mycenter_layout, container, false);

        initView(view);
        return view;
    }

    private void initView(View v) {
        v.findViewById(R.id.myCourseLayout).setOnClickListener(this);
        v.findViewById(R.id.myDownloadLayout).setOnClickListener(this);
        v.findViewById(R.id.myOrderLayout).setOnClickListener(this);

        v.findViewById(R.id.myWalletLayout).setOnClickListener(this);
        v.findViewById(R.id.mySchoolLayout).setOnClickListener(this);
        v.findViewById(R.id.interestingLayout).setOnClickListener(this);
        v.findViewById(R.id.myQuestionLayout).setOnClickListener(this);
        v.findViewById(R.id.myCollectionLayout).setOnClickListener(this);
        v.findViewById(R.id.settingLayout).setOnClickListener(this);
        v.findViewById(R.id.helpLayout).setOnClickListener(this);
        v.findViewById(R.id.myCommentLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myCourseLayout:
//                startActivity(new Intent(getActivity(), TeacherHomePage.class));
                break;
            case R.id.myDownloadLayout:
//                startActivity(new Intent(getActivity(), MyOrders.class));
                break;
            case R.id.myOrderLayout:
//                startActivity(new Intent(getActivity(), AuthMenu.class));
                break;
            case R.id.myWalletLayout:
                break;
            case R.id.mySchoolLayout:
                break;
            case R.id.myQuestionLayout:
                break;
            case R.id.myCollectionLayout:
                break;
            case R.id.settingLayout:
                break;
            case R.id.helpLayout:
                break;
            case R.id.myCommentLayout:
                break;
            case R.id.interestingLayout:
                break;
        }
    }
}
