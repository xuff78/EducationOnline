package com.education.online.act;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.http.HttpHandler;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.StatusBarCompat;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SearchAct extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;
    private TextView typeTxt;
    private EditText searchEdt;
    private LinearLayout mostKeywordsLayout, recentKeywordsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);

        _setHeaderGone();
        initView();

    }

    private void initView() {
        typeTxt= (TextView) findViewById(R.id.typeTxt);
        searchEdt= (EditText) findViewById(R.id.searchEdt);
        findViewById(R.id.typeLayout).setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);

        mostKeywordsLayout= (LinearLayout) findViewById(R.id.mostKeywordsLayout);
        recentKeywordsLayout= (LinearLayout) findViewById(R.id.recentKeywordsLayout);
        findViewById(R.id.clearBtn).setOnClickListener(this);

        int itemWidth = (ScreenUtil.getWidth(this)- ImageUtil.dip2px(this,90))/4;
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(itemWidth, -2);
        llp.rightMargin=ImageUtil.dip2px(this, 15);
        LinearLayout linelayout=new LinearLayout(this);
        int size=6;
        for(int i=0;i<size;i++){
            TextView txt=new TextView(this);
            txt.setTextSize(13);
            txt.setGravity(Gravity.CENTER);
            txt.setBackgroundResource(R.drawable.shape_corner_blackline);
            txt.setTextColor(Color.GRAY);
            txt.setText("测试");
            linelayout.addView(txt, llp);
            if(i%4==3||i==size-1){
                mostKeywordsLayout.addView(linelayout);
                linelayout=new LinearLayout(this);
                linelayout.setPadding(0, llp.rightMargin,0,0);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.typeLayout:
                break;
            case R.id.cancelBtn:
                break;
            case R.id.clearBtn:
                break;
        }
    }
}
