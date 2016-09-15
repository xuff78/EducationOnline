package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/13.
 */

public class HelpandFeedback extends BaseFrameAct implements View.OnClickListener{

    private RelativeLayout illegalreport,teleconsulting, suggestionAndFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_and_feedback);
        _setHeaderTitle("帮助与反馈");
        _setLeftBackGone();
        illegalreport = (RelativeLayout) findViewById(R.id.illegalreport);
        illegalreport.setOnClickListener(this);
        teleconsulting = (RelativeLayout) findViewById(R.id.teleconsulting);
        teleconsulting.setOnClickListener(this);
        suggestionAndFeedback = (RelativeLayout) findViewById(R.id.suggestionAndFeedback);
        suggestionAndFeedback.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.illegalreport:
                startActivity(new Intent(HelpandFeedback.this,IllegalReport.class));
                break;
            case R.id.teleconsulting:

                break;
            case R.id.suggestionAndFeedback:
                startActivity(new Intent(HelpandFeedback.this,SuggestionAndFeedback.class));
                break;
        }
    }
}
