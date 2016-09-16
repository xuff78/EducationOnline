package com.education.online.act.Mine;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.view.SelectPicDialog;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class UserOrderDetail extends BaseFrameAct {


    private ImageView headIcon;
    private TextView nickName;
    private Dialog progressDialog;
    private ImageView SexFemale;
    private ImageView SexMale;
    private LinearLayout LayoutMale;
    private LinearLayout LayoutFemale;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_edit);

        _setHeaderTitle("订单详情");
        initView();
    }

    private void initView() {
        headIcon= (ImageView) findViewById(R.id.userIcon);
        nickName= (TextView) findViewById(R.id.nickName);

        headIcon.setOnClickListener(listener);
        findViewById(R.id.birthdayLayout).setOnClickListener(listener);

        SexFemale = (ImageView) findViewById(R.id.SexFemale);
        SexMale = (ImageView) findViewById(R.id.SexMale);
        SexFemale.setImageResource(R.mipmap.icon_round_right);
        SexMale.setImageResource(R.mipmap.icon_round);
        LayoutMale = (LinearLayout) findViewById(R.id.LayoutMale);
        LayoutFemale = (LinearLayout) findViewById(R.id.LayoutFemale);
        LayoutFemale.setOnClickListener(listener);
        LayoutMale.setOnClickListener(listener);
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.headIcon:
                    new SelectPicDialog(UserOrderDetail.this).show();

                    break;
                case R.id.birthdayLayout:
                    break;
                case R.id.LayoutFemale:
                    SexFemale.setImageResource(R.mipmap.icon_round_right);
                    SexMale.setImageResource(R.mipmap.icon_round);
                    break;
                case R.id.LayoutMale:
                    SexFemale.setImageResource(R.mipmap.icon_round);
                    SexMale.setImageResource(R.mipmap.icon_round_right);
                    break;

            }
        }
    };
}
