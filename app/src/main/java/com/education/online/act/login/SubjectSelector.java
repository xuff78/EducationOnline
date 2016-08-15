package com.education.online.act.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.fragment.BaseFragment;
import com.education.online.fragment.SelectorPage;
import com.education.online.util.StatusBarCompat;

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
        ft.add(R.id.fragment_frame, selectorPage);
        ft.commit();
    }
}
