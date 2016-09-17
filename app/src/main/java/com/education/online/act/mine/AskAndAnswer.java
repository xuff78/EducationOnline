package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/17.
 */

public class AskAndAnswer extends BaseFrameAct {

    private TextView subject, submitbtn;
    private SeekBar seekbar;
    private EditText enterquestion;
    private ImageView addpicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_and_answer);
        _setHeaderTitle("问答");
        _setRightHomeText("我的提问", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AskAndAnswer.this,MyQuestion.class));
            }
        });
        init();
    }

    void init(){

        subject = (TextView) findViewById(R.id.subject);
        submitbtn= (TextView) findViewById(R.id.submitbtn);
        seekbar= (SeekBar) findViewById(R.id.seekbar);
        enterquestion= (EditText) findViewById(R.id.enterquestion);
        addpicture= (ImageView) findViewById(R.id.addpicture);


    }
}

