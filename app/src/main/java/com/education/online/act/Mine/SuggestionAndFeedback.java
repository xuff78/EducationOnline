package com.education.online.act.Mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ToastUtils;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/9/13.
 */

public class SuggestionAndFeedback extends BaseFrameAct {

    private EditText entersuggestion;
    private TextView submitbtn;
    private HttpHandler httpHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editingsuggestion);
        _setHeaderTitle("建议与反馈");
        init();
        initHandler();
    }

    public void init() {
        entersuggestion = (EditText) findViewById(R.id.entersuggestion);
        submitbtn = (TextView) findViewById(R.id.submitbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = "";
                feedback = entersuggestion.getText().toString().trim();
                if(feedback.length()>0)
                httpHandler.submitFeedback(feedback);
                else ToastUtils.displayTextShort(SuggestionAndFeedback.this,"请输入有效反馈信息");
            }
        });
    }
    private void initHandler(){
        httpHandler = new HttpHandler(this, new CallBack(this){
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.submitFeedback)){
                    ToastUtils.displayTextShort(SuggestionAndFeedback.this,"反馈成功！");
                    finish();
                }

            }
        });
    }
}