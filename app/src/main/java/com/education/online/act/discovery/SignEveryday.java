package com.education.online.act.discovery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.JsonMessage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/9/29.
 */
public class SignEveryday extends BaseFrameAct {

    HttpHandler mHandler;
    Button signBtn;
    View hintInfoLayout;
    TextView hintInfo;
    ImageView teacherImg;
    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                if(method.equals(Method.signin)){
                    hintInfo.setText("我的积分： "+ JsonUtil.getString(jsonData, "integral")+"     此次增加积分： "+
                            JsonUtil.getString(jsonData, "signin_integral"));
                    signBtn.setVisibility(View.GONE);
                    hintInfoLayout.setVisibility(View.VISIBLE);
                    loader.displayImage(ImageUtil.getImageUrl(SharedPreferencesUtil.getString(SignEveryday.this, Constant.Avatar)), teacherImg);
                }else if(method.equals(Method.signToday)){
                    hintInfo.setText("我的积分： "+ JsonUtil.getString(jsonData, "integral")+"     此次增加积分： "+
                            JsonUtil.getString(jsonData, "signin_integral"));
                    String is_signin=JsonUtil.getString(jsonData, "is_signin");
                    if(is_signin.equals("1")){
                        hintInfoLayout.setVisibility(View.VISIBLE);
                        loader.displayImage(ImageUtil.getImageUrl(SharedPreferencesUtil.getString(SignEveryday.this, Constant.Avatar)), teacherImg);
                    }else{
                        signBtn.setVisibility(View.VISIBLE);
                    }
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
        mHandler.signToday();
    }

    private void initView() {
        //https://github.com/kyleduo/SwitchButton
        teacherImg=(ImageView)findViewById(R.id.teacherImg);
        hintInfo= (TextView) findViewById(R.id.hintInfo);
        hintInfoLayout= findViewById(R.id.hintInfoLayout);
        signBtn= (Button) findViewById(R.id.signBtn);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.signin();
            }
        });
    }
}
