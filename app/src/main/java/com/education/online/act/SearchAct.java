package com.education.online.act;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.http.HttpHandler;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.StatusBarCompat;
import com.education.online.view.AutoFitLinearLayout;
import com.education.online.view.MenuPopup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SearchAct extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;
    private TextView typeTxt;
    private EditText searchEdt;
    private LinearLayout mostKeywordsLayout;
    private AutoFitLinearLayout recentKeywordsLayout;
    private View typeLayout;
    private MenuPopup popup;
    private String[] typeStrs={"教程", "课件", "视频"};
    private int type=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);

        _setHeaderGone();
        initView();

    }

    private void initView() {
        popup = new MenuPopup(SearchAct.this, typeStrs, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                typeTxt.setText(typeStrs[i]);
                type=i;
            }
        });

        typeTxt= (TextView) findViewById(R.id.typeTxt);
        typeTxt.setText(typeStrs[0]);
        searchEdt= (EditText) findViewById(R.id.searchEdt);
        typeLayout=findViewById(R.id.typeLayout);
        typeLayout.setOnClickListener(this);
        findViewById(R.id.cancelBtn).setOnClickListener(this);

        mostKeywordsLayout= (LinearLayout) findViewById(R.id.mostKeywordsLayout);
        recentKeywordsLayout= (AutoFitLinearLayout) findViewById(R.id.recentKeywordsLayout);
        findViewById(R.id.clearBtn).setOnClickListener(this);

        int itemWidth = (ScreenUtil.getWidth(this)- ImageUtil.dip2px(this,90))/4;
        int itemHeight = ImageUtil.dip2px(this,30);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(itemWidth, itemHeight);
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

        String[] words={"什么", "这么神奇", "有", "娃娃哈哈哈", "啥的了飞机萨克的龙卷风", "就是说", "版本"};
        for(int i=0;i<words.length;i++){
            TextView txt=new TextView(this);
            txt.setTextSize(14);
            txt.setGravity(Gravity.CENTER);
            txt.setTextColor(Color.GRAY);
            txt.setText(words[i]);
            txt.setPadding(itemHeight/3, itemHeight/6, itemHeight/3, itemHeight/6);
            recentKeywordsLayout.addView(txt);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.typeLayout:
                popup.showPopupWindow(typeLayout);
                break;
            case R.id.cancelBtn:
                break;
            case R.id.clearBtn:
                break;
        }
    }
}
