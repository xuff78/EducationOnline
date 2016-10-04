package com.education.online.act.discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/29.
 */
public class ChatPage extends BaseFrameAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        _setHeaderTitle("赵老板");
        _setRightHome(R.mipmap.icon_chatsetting, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatPage.this, ChatSetting.class));
            }
        });

        initView();
    }

    private void initView() {
        //https://github.com/kyleduo/SwitchButton
    }
}
