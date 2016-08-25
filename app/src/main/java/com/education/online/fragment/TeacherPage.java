package com.education.online.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.education.online.R;
import com.education.online.bean.CategoryBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/25.
 */
public class TeacherPage extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_page, container, false);

        initView(view);
        return view;
    }

    private void initView(View v) {

    }
}
