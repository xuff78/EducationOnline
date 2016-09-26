package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.order.GetVeriCode;
import com.education.online.act.order.SetPayPwd;

/**
 * Created by Administrator on 2016/9/13.
 */
public class MyWallet extends BaseFrameAct implements View.OnClickListener{

    private TextView walletBalance, chargeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_main_layout);
        _setHeaderTitle("钱包管理");
        _setRightHomeListener(this);
        initView();
    }

    private void initView() {
        walletBalance= (TextView) findViewById(R.id.walletBalance);
        chargeBtn= (TextView) findViewById(R.id.chargeBtn);
        chargeBtn.setOnClickListener(this);
        findViewById(R.id.pswLayout).setOnClickListener(this);
        findViewById(R.id.tixianLayout).setOnClickListener(this);
        findViewById(R.id.historyLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chargeBtn:
                startActivity(new Intent(MyWallet.this, MyWalletCharge.class));
                break;
            case R.id.pswLayout:
//                startActivity(new Intent(getActivity(), MyOrders.class));
                startActivity(new Intent(MyWallet.this, GetVeriCode.class));
                break;
            case R.id.tixianLayout:
//                startActivity(new Intent(getActivity(), AuthMenu.class));
                break;
            case R.id.historyLayout:
                startActivity(new Intent(MyWallet.this, MyWalletHistory.class));
                break;
        }
    }
}
