package com.education.online.act.order;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/20.
 */
public class SetPayPwd extends BaseFrameAct {

    private String initPassword;
    private String confirmPassword;
    private boolean isConfirmEnter = false;
    private EditText[] textViews = new EditText[6];
    private TextView textView1;
    private LinearLayout passwordlayout;
    private View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_pay_password);
        _setHeaderTitle("设置支付密码");
        init();


    }

    public void init() {
        textViews[0] = (EditText) findViewById(R.id.pwd1);
        textViews[1] = (EditText) findViewById(R.id.pwd2);
        textViews[2] = (EditText) findViewById(R.id.pwd3);
        textViews[3] = (EditText) findViewById(R.id.pwd4);
        textViews[4] = (EditText) findViewById(R.id.pwd5);
        textViews[5] = (EditText) findViewById(R.id.pwd6);
        textView1 = (TextView) findViewById(R.id.textview1);
        view1=findViewById(R.id.view1);
        passwordlayout = (LinearLayout) findViewById(R.id.passwordlayout);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                for(int i=0;i<textViews.length;i++) {
                    if(textViews[i].isFocused())
                    imm.showSoftInput(textViews[i], InputMethodManager.SHOW_FORCED);
                }
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    if (textViews[0].isFocusable()) {
                        textViews[1].setFocusable(true);
                        textViews[1].setFocusableInTouchMode(true);
                    } else if (textViews[1].isFocusable()) {
                        textViews[2].setFocusable(true);
                        textViews[2].setFocusableInTouchMode(true);
                    } else if (textViews[2].isFocusable()) {
                        textViews[3].setFocusable(true);
                        textViews[3].setFocusableInTouchMode(true);
                    } else if (textViews[3].isFocusable()) {
                        textViews[4].setFocusable(true);
                        textViews[4].setFocusableInTouchMode(true);
                    } else if (textViews[4].isFocusable()) {
                        textViews[5].setFocusable(true);
                        textViews[5].setFocusableInTouchMode(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 1) {
                    if (textViews[0].isFocused()) {
                        textViews[1].requestFocus();
                        textViews[0].setFocusable(false);
                    } else if (textViews[1].isFocused()) {
                        textViews[2].requestFocus();
                        textViews[1].setFocusable(false);
                    } else if (textViews[2].isFocused()) {
                        textViews[3].requestFocus();
                        textViews[2].setFocusable(false);
                    } else if (textViews[3].isFocused()) {
                        textViews[4].requestFocus();
                        textViews[3].setFocusable(false);
                    } else if (textViews[4].isFocused()) {
                        textViews[5].requestFocus();
                        textViews[4].setFocusable(false);
                    } else if (textViews[5].isFocused()) {
                        textViews[5].clearFocus();
                        textViews[5].setFocusable(false);
                        if (isConfirmEnter) {//如果是第二次输入就关闭软键盘
                            confirmPassword = GetString();
                            if (confirmPassword.equals(initPassword)) {//相等关闭键盘
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(textViews[5].getWindowToken(), 0);
                                Toast toast = new Toast(SetPayPwd.this).makeText(SetPayPwd.this, "最终密码是" + initPassword, Toast.LENGTH_SHORT);
                                toast.show();
                                finish();
                            } else {
                                isConfirmEnter = false;
                                Toast toast = new Toast(SetPayPwd.this).makeText(SetPayPwd.this, "前后两次密码不一致，请重新输入！", Toast.LENGTH_SHORT);
                                toast.show();
                                initState();//标志位置反，初始化控件
                            }
                        } else {
                            initPassword = GetString();
                            Toast toast = new Toast(SetPayPwd.this).makeText(SetPayPwd.this, "初始密码是" + initPassword, Toast.LENGTH_SHORT);
                            toast.show();
                            isConfirmEnter = true;
                            initState();
                        }
                    }
                }

            }
        };
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setCursorVisible(false);
            textViews[i].addTextChangedListener(textWatcher);
        }
        initState();
    }


    public void initState() {
        if (isConfirmEnter) {//第二次输入
            textView1.setText("确认平台的支付密码");
            textViews[5].clearFocus();
        } else {
            initPassword = "";
            confirmPassword = "";
            textView1.setText("请设置平台的支付密码");
        }
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].getText().clear();
            if (i == 0) {
                textViews[i].setFocusable(true);
                textViews[i].setFocusableInTouchMode(true);
                textViews[i].requestFocus();//默认焦点在第一个位置
            } else {
                textViews[i].setFocusable(false);//其他位置不能得到焦点
            }
        }
    }

    public String GetString() {
        String temp = new String("");
        for (int i = 0; i < textViews.length; i++) {
            temp += textViews[i].getText().toString();
        }
        return temp;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            if (textViews[5].isFocused()) {
                textViews[5].clearFocus();
                textViews[5].setFocusable(false);
                textViews[4].setFocusableInTouchMode(true);
                textViews[4].getText().clear();
                textViews[4].requestFocus();
            } else if (textViews[4].isFocused()) {
                textViews[4].clearFocus();
                textViews[4].setFocusable(false);
                textViews[3].setFocusableInTouchMode(true);
                textViews[3].getText().clear();
                textViews[3].requestFocus();
            } else if (textViews[3].isFocused()) {
                textViews[3].clearFocus();
                textViews[3].setFocusable(false);
                textViews[2].setFocusableInTouchMode(true);
                textViews[2].getText().clear();
                textViews[2].requestFocus();
            } else if (textViews[2].isFocused()) {
                textViews[2].clearFocus();
                textViews[2].setFocusable(false);
                textViews[1].setFocusableInTouchMode(true);
                textViews[1].getText().clear();
                textViews[1].requestFocus();
            } else if (textViews[1].isFocused()) {
                textViews[1].clearFocus();
                textViews[1].setFocusable(false);
                textViews[0].setFocusableInTouchMode(true);
                textViews[0].getText().clear();
                textViews[0].requestFocus();
            }
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                this.finish();
            }
        }
        return false;
    }
}
