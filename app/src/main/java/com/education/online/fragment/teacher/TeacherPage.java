package com.education.online.fragment.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.teacher.AuthMenu;
import com.education.online.act.teacher.MyOrders;
import com.education.online.act.teacher.MyRatePage;
import com.education.online.act.teacher.TeacherCourseStart;
import com.education.online.act.teacher.TeacherHomePage;
import com.education.online.act.teacher.TeacherInfoEdit;
import com.education.online.bean.CategoryBean;
import com.education.online.bean.UserInfo;
import com.education.online.fragment.BaseFragment;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/25.
 */
public class TeacherPage extends BaseFragment implements View.OnClickListener{

    private ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_page, container, false);
        imageLoader=ImageLoader.getInstance();
        initView(view);
        return view;
    }

    private void initView(View v) {
        v.findViewById(R.id.homepageEditLayout).setOnClickListener(this);
        v.findViewById(R.id.courseManagerLayout).setOnClickListener(this);
        v.findViewById(R.id.commentLayout).setOnClickListener(this);
        v.findViewById(R.id.orderLayout).setOnClickListener(this);
        v.findViewById(R.id.authLayout).setOnClickListener(this);
        v.findViewById(R.id.settingLayout).setOnClickListener(this);

        UserInfo user= JSON.parseObject(SharedPreferencesUtil.getString(getActivity(), Constant.UserInfo), UserInfo.class);
        ImageView teacherImg= (ImageView) v.findViewById(R.id.teacherImg);
        imageLoader.displayImage(ImageUtil.getImageUrl(user.getAvatar()), teacherImg);
        TextView nameTxt= (TextView) v.findViewById(R.id.nameTxt);
        nameTxt.setText(user.getName());
        TextView descTxt= (TextView) v.findViewById(R.id.descTxt);
        descTxt.setText("未生效");
        TextView sexTxt= (TextView) v.findViewById(R.id.sexTxt);
        if(user.getGender().equals("1")){
            sexTxt.setText("男");
        }else if(user.getGender().equals("0")){
            sexTxt.setText("女");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homepageEditLayout:
                startActivity(new Intent(getActivity(), TeacherHomePage.class));
                break;
            case R.id.orderLayout:
                startActivity(new Intent(getActivity(), MyOrders.class));
                break;
            case R.id.authLayout:
                startActivity(new Intent(getActivity(), AuthMenu.class));
                break;
            case R.id.commentLayout:
                startActivity(new Intent(getActivity(), MyRatePage.class));
                break;
            case R.id.settingLayout:
//                startActivity(new Intent(getActivity(), MyRatePage.class));
                break;
            case R.id.courseManagerLayout:
                startActivity(new Intent(getActivity(), TeacherCourseStart.class));
                break;
        }
    }
}
