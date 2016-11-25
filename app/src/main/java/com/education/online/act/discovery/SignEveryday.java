package com.education.online.act.discovery;

import android.os.Bundle;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.JsonUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/9/29.
 */
public class SignEveryday extends BaseFrameAct {

    HttpHandler mHandler;
    TextView hintInfo;
    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                if(method.equals(Method.signin)){
                    hintInfo.setText("我的积分： "+ JsonUtil.getString(jsonData, "integral")+"     此次增加积分： "+
                            JsonUtil.getString(jsonData, "signin_integral"));
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_everyday);

        _setHeaderTitle("每日签到");
        initHandler();

        initView();
        mHandler.signin();
    }

    private void initView() {
        //https://github.com/kyleduo/SwitchButton
        hintInfo= (TextView) findViewById(R.id.hintInfo);
    }
}
