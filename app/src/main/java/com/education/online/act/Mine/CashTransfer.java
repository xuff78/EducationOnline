package com.education.online.act.Mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.order.OrderPay;
import com.education.online.act.order.WalletPay;
import com.education.online.bean.AccountInfo;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

/**
 * Created by 可爱的蘑菇 on 2017/4/18.
 */

public class CashTransfer extends BaseFrameAct implements View.OnClickListener{

    private RelativeLayout modifypwd;
    private AccountInfo info;
    private EditText amountEdt;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_layout);
        _setHeaderTitle("提现");
        _setRightHomeText("更换账号  ", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CashTransfer.this, WalletPay.class);
                intent.putExtra("cost", amountEdt.getText().toString());
                startActivityForResult(intent, 0x20);
            }
        });
        initHandler();
        info= (AccountInfo) getIntent().getSerializableExtra("AccountInfo");
        init();
        handler.getTransferList(page);
    }
    public void init(){
        amountEdt=(EditText)findViewById(R.id.amountEdt);
        ImageView typeIcon=(ImageView)findViewById(R.id.typeIcon);
        TextView typeTxt=(TextView)findViewById(R.id.typeTxt);
        TextView accountTxt=(TextView)findViewById(R.id.accountTxt);
        findViewById(R.id.confirmBtn).setOnClickListener(this);
        if(info.getAccount_type().equals("ALI")) {
            typeIcon.setImageResource(R.mipmap.icon_alipay);
            typeTxt.setText(info.getAccount_name());
        }
        accountTxt.setText(info.getAccount_no());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmBtn:
                if(ActUtil.isCash(amountEdt.getText().toString(), this)) {
                    Intent intent = new Intent();
                    intent.setClass(CashTransfer.this, WalletPay.class);
                    intent.putExtra("cost", amountEdt.getText().toString());
                    startActivityForResult(intent, 0x10);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x10&&resultCode==0x11){
            String psw=data.getStringExtra("psw");
            handler.transfer(ActUtil.twoDecimal(amountEdt.getText().toString()), info.getAccount_type(), psw);
        }else if(requestCode==0x20&&resultCode==0x11){
            String psw=data.getStringExtra("psw");
            handler.unboundAccount(psw);
        }
    }


    private HttpHandler handler;
    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, final String jsonData) throws JSONException {
                if (method.equals(Method.transfer)) {
                    DialogUtil.showInfoDialog(CashTransfer.this, "提示", "提现成功", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setResult(Constant.refreshData);
                            finish();
                        }
                    });
                }else if (method.equals(Method.unboundAccount)) {
                    DialogUtil.showInfoDialog(CashTransfer.this, "提示", "已解除账号绑定", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                }else if (method.equals(Method.getTransferList)) {

                }
            }
        });
    }
}
