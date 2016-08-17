package com.education.online.act;

import android.os.Bundle;
import android.view.View;

import com.education.online.R;
import com.education.online.http.HttpHandler;
import com.education.online.util.StatusBarCompat;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SearchResultAct extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.fitPage(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);

        _setHeaderGone();
        initView();

    }

    private void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                break;
            case R.id.registerBtn:
                break;
        }
    }
}
