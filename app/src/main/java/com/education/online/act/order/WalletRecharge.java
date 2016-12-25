package com.education.online.act.order;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.view.PayTypeDialog;

/**
 * Created by 可爱的蘑菇 on 2016/8/23.
 */
public class WalletRecharge extends BaseFrameAct implements View.OnClickListener, PayTypeDialog.PayDialogCallBack {

    private EditText rechargePrice;
    private PayTypeDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_recharge);

        _setHeaderTitle("余额充值");

        initView();
    }

    private void initView() {
        rechargePrice= (EditText) findViewById(R.id.rechargePrice);
        TextView walletAmount= (TextView) findViewById(R.id.walletAmount);
        findViewById(R.id.payBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.payBtn:
                dialog=new PayTypeDialog(WalletRecharge.this, false, WalletRecharge.this, "0");
                dialog.show();
                break;
        }
    }

    @Override
    public void onSelected(int payType) {
        switch (payType){
            case PayTypeDialog.AliPay:
                break;
            case PayTypeDialog.WechatPay:
                break;
            case PayTypeDialog.UnionPay:
                break;
        }
    }
}
