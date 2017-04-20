package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.order.GetVeriCode;
import com.education.online.act.order.SetPayPwd;
import com.education.online.bean.AccountInfo;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/13.
 */
public class MyWallet extends BaseFrameAct implements View.OnClickListener{

    private TextView walletBalance, chargeBtn;
    private HttpHandler httpHandler;
    private String page_size="10";
    private int page =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_main_layout);
        _setHeaderTitle("钱包管理");
        _setRightHomeListener(this);
        initView();
        initHandler();
        httpHandler.getWalletInfo(page_size,String.valueOf(page));
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
                startActivityForResult(new Intent(MyWallet.this, MyWalletCharge.class), 0x10);
                break;
            case R.id.pswLayout:
//                startActivity(new Intent(getActivity(), MyOrders.class));
                startActivity(new Intent(MyWallet.this, GetVeriCode.class));
                break;
            case R.id.tixianLayout:
                httpHandler.getUserAccount();
                break;
            case R.id.historyLayout:
                startActivity(new Intent(MyWallet.this, MyWalletHistory.class));
                break;
        }
    }

    private void initHandler(){
        httpHandler = new HttpHandler(MyWallet.this, new CallBack(MyWallet.this){
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getWalletInfo)){
                    JSONObject jsonObject = new JSONObject(jsonData);
                    if (!jsonObject.isNull("balance")){
                        String balance = jsonObject.getString("balance");
                        walletBalance.setText(balance);
                    }
                }else if(method.equals(Method.getUserAccount)){
                    ArrayList<AccountInfo> accounts=JSON.parseObject(jsonData, new TypeReference<ArrayList<AccountInfo>>(){});
                    if(accounts.size()>0){
                        Intent i=new Intent(MyWallet.this, CashTransfer.class);
                        i.putExtra("AccountInfo", accounts.get(0));
                        startActivityForResult(i, 0x10);
                    }else{
                        startActivity(new Intent(MyWallet.this, TransferType.class));
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Constant.refreshData){
            httpHandler.getWalletInfo(page_size,String.valueOf(page));
        }
    }
}
