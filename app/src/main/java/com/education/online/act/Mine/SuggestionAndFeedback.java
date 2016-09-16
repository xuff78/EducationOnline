package com.education.online.act.Mine;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/13.
 */

public class SuggestionAndFeedback extends BaseFrameAct {

    private EditText entersuggestion;
    private TextView submitbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editingsuggestion);
        _setHeaderTitle("建议与反馈");
        init();

    }

    public void init() {
        entersuggestion = (EditText) findViewById(R.id.entersuggestion);
        submitbtn = (TextView) findViewById(R.id.submitbtn);
    }
}