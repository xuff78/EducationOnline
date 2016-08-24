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

import com.education.online.R;
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

    public PayTypeDialog(Context context, boolean showWallet, PayDialogCallBack cb) {
        super(context, R.style.view_dialog);
        this.showWallet=showWallet;
        this.cb=cb;
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
        RelativeLayout walletPayLayout = (RelativeLayout) findViewById(R.id.walletPayLayout);
        walletPayLayout.setOnClickListener(this);
        RelativeLayout alipayPayLayout = (RelativeLayout) findViewById(R.id.alipayPayLayout);
        alipayPayLayout.setOnClickListener(this);
        RelativeLayout wechatPayLayout = (RelativeLayout) findViewById(R.id.wechatPayLayout);
        wechatPayLayout.setOnClickListener(this);
        RelativeLayout unionPayLayout = (RelativeLayout) findViewById(R.id.unionPayLayout);
        unionPayLayout.setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);
        findViewById(R.id.payBtn).setOnClickListener(this);
        if(!showWallet)
            walletPayLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.walletPayLayout:
                cb.onSelected(0);
                break;
            case R.id.alipayPayLayout:
                cb.onSelected(1);
                break;
            case R.id.wechatPayLayout:
                cb.onSelected(2);
                break;
            case R.id.unionPayLayout:
                cb.onSelected(3);
                break;
            case R.id.cancelBtn:
                break;
            case R.id.payBtn:
                break;
        }
        dismiss();
    }

    public interface PayDialogCallBack{
        void onSelected(int payType);
    }
}
