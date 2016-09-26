package com.education.online.act.Mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.view.PayTypeDialog;

/**
 * Created by Administrator on 2016/9/26.
 */
public class MyWalletCharge extends BaseFrameAct implements View.OnClickListener{

    private EditText rechargePrice;
    private PayTypeDialog dialog;
    private View checkedIcon=null;
    private View checkIcon2, checkIcon3, checkIcon4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_charge_layout);

        _setHeaderTitle("余额充值");

        initView();
    }

    private void initView() {
        checkIcon2=findViewById(R.id.checkIcon2);
        checkIcon2.setOnClickListener(this);
        checkIcon3=findViewById(R.id.checkIcon3);
        checkIcon3.setOnClickListener(this);
        checkIcon4=findViewById(R.id.checkIcon4);
        checkIcon4.setOnClickListener(this);
        rechargePrice= (EditText) findViewById(R.id.rechargePrice);
        TextView walletAmount= (TextView) findViewById(R.id.walletAmount);
        findViewById(R.id.payBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(checkedIcon!=null)
            checkedIcon.setVisibility(View.GONE);
        switch (view.getId()){
            case R.id.alipayPayLayout:
                checkedIcon=checkIcon2;
                break;
            case R.id.wechatPayLayout:
                checkedIcon=checkIcon3;
                break;
            case R.id.unionPayLayout:
                checkedIcon=checkIcon4;
                break;
        }
        checkedIcon.setVisibility(View.VISIBLE);
    }
}
