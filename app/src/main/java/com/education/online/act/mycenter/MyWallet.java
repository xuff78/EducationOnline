package com.education.online.act.mycenter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.fragment.CoursePage;

/**
 * Created by Administrator on 2016/9/13.
 */
public class MyWallet extends BaseFrameAct implements View.OnClickListener{

    int currentPos = 0;
    private LinearLayout addfavorite_layout, share_layout, download_layout;
    private TextView textaddfavorite, textshare, textdownload, textaddorbuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_main_layout);
        _setHeaderTitle("钱包管理");
        _setRightHomeListener(this);
        initView();
    }

    private void initView() {

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
        }
    }
}
