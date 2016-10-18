package com.education.online.act.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.SubjectBean;
import com.education.online.fragment.dialog.SelectorPage;

/**
 * Created by Administrator on 2016/8/15.
 */
public class SubjectSelector extends BaseFrameAct{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);

        _setHeaderTitle("全部科目");
        initView();
    }

    private void initView() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        SelectorPage selectorPage=new SelectorPage();
        selectorPage.setData(new SelectorPage.CourseSelector() {
            @Override
            public void onSelected(SubjectBean subject) {
                Intent i=new Intent();
                i.putExtra(SubjectBean.Name, subject);
                setResult(0x11, i);
                finish();
            }
        });
        ft.add(R.id.fragment_frame, selectorPage);
        ft.commit();
    }
}
