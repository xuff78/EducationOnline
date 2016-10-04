package com.education.online.act.discover;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.kyleduo.switchbutton.SwitchButton;

/**
 * Created by Administrator on 2016/9/29.
 */
public class ChatSetting extends BaseFrameAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_setting);

        _setHeaderTitle("设置");
        _setRightHomeText("保存", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        TextView right_text= (TextView) findViewById(R.id.right_text);
        right_text.setTextColor(getResources().getColor(R.color.dark_orange));

        initView();
    }

    private void initView() {
        //https://github.com/kyleduo/SwitchButton
        ((SwitchButton)findViewById(R.id.switchBtn)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }
}
