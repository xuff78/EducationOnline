package com.education.online.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.util.ActUtil;
import com.education.online.util.ScreenUtil;

/**
 * Created by Administrator on 2016/8/24.
 */
public class PayTypeDialog extends Dialog implements View.OnClickListener {

    boolean showWallet=false;
    private PayDialogCallBack cb;
    public static final int WalletPay=0;
    public static final int AliPay=1;
    public static final int WechatPay=2;
    public static final int UnionPay=3;
    private View checkedIcon=null;
    private View checkIcon, checkIcon2, checkIcon3, checkIcon4;
    private int choice=0;
    private String price="";

    public PayTypeDialog(Context context, boolean showWallet, PayDialogCallBack cb, String price) {
        super(context, R.style.view_dialog);
        this.showWallet=showWallet;
        this.cb=cb;
        this.price=price;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_pay_type);
        initView();

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.dialog_popfrombottom);  //添加动画
        window.setLayout(ScreenUtil.getWidth(getContext()), -2);

    }
    /**
     * 初始化加载View
     */
    private void initView() {
        TextView priceTxt= (TextView) findViewById(R.id.priceTxt);
        priceTxt.setText(ActUtil.twoDecimal(price)+"元");
        RelativeLayout walletPayLayout = (RelativeLayout) findViewById(R.id.walletPayLayout);
        walletPayLayout.setOnClickListener(this);
        RelativeLayout alipayPayLayout = (RelativeLayout) findViewById(R.id.alipayPayLayout);
        alipayPayLayout.setOnClickListener(this);
        RelativeLayout wechatPayLayout = (RelativeLayout) findViewById(R.id.wechatPayLayout);
        wechatPayLayout.setOnClickListener(this);
        RelativeLayout unionPayLayout = (RelativeLayout) findViewById(R.id.unionPayLayout);
        unionPayLayout.setOnClickListener(this);
        checkIcon=findViewById(R.id.checkIcon);
        checkIcon.setOnClickListener(this);
        checkIcon2=findViewById(R.id.checkIcon2);
        checkIcon2.setOnClickListener(this);
        checkIcon3=findViewById(R.id.checkIcon3);
        checkIcon3.setOnClickListener(this);
        checkIcon4=findViewById(R.id.checkIcon4);
        checkIcon4.setOnClickListener(this);
        checkedIcon=checkIcon;
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choice!=-1) {
                    dismiss();
                }
            }
        });
        findViewById(R.id.payBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                cb.onSelected(choice);
            }
        });
        if(!showWallet)
            walletPayLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if(checkedIcon!=null)
            checkedIcon.setVisibility(View.GONE);
        switch (view.getId()){
            case R.id.walletPayLayout:
                checkedIcon=checkIcon;
                choice=0;
                break;
            case R.id.alipayPayLayout:
                checkedIcon=checkIcon2;
                choice=1;
                break;
            case R.id.wechatPayLayout:
                checkedIcon=checkIcon3;
                choice=2;
                break;
            case R.id.unionPayLayout:
                checkedIcon=checkIcon4;
                choice=3;
                break;
        }
        checkedIcon.setVisibility(View.VISIBLE);
    }

    public interface PayDialogCallBack{
        void onSelected(int payType);
    }
}
