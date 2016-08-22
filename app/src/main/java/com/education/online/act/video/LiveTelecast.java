package com.education.online.act.video;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.fragment.dialog.SelectorPage;

/**
 * Created by Administrator on 2016/8/22.
 */
public class LiveTelecast extends BaseFrameAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_telecats);

        _setHeaderTitle("全部科目");
        initView();
    }

    private void initView() {
        openFragment(R.id.fragment_list, new OnlineCoursePage());
    }
}
