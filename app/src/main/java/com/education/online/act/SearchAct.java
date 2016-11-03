package com.education.online.act;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.http.HttpHandler;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.StatusBarCompat;
import com.education.online.util.ToastUtils;
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
    private LinearLayout recentKeywordsLayout;
    private View typeLayout;
    private MenuPopup popup;
    private String[] typeStrs={"课程", "老师"};
    private int type=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);

        _setHeaderGone();
        initView();
        initRecentLayout();
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
        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId==EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_UNSPECIFIED||actionId==EditorInfo.IME_ACTION_SEARCH){
                    String word=searchEdt.getText().toString().trim();
                        //记录搜索词
                        String wordsStr=SharedPreferencesUtil.getString(SearchAct.this, Constant.SearchWords);
                        if(wordsStr.equals(SharedPreferencesUtil.FAILURE_STRING)){
                            wordsStr=word;
                            SharedPreferencesUtil.setString(SearchAct.this, Constant.SearchWords, wordsStr);
                        }else{
                            wordsStr=word+":"+wordsStr;
                            SharedPreferencesUtil.setString(SearchAct.this, Constant.SearchWords, wordsStr);
                        }
                        Intent i=new Intent(SearchAct.this, SearchResultAct.class);
                        i.putExtra(Constant.SearchWords, word);
                        i.putExtra("Type", type);
                        startActivity(i);
                }
                return false;
            }
        });

        mostKeywordsLayout= (LinearLayout) findViewById(R.id.mostKeywordsLayout);
        recentKeywordsLayout= (LinearLayout) findViewById(R.id.recentKeywordsLayout);
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
            txt.setTag("测试");
            txt.setOnClickListener(this);
            linelayout.addView(txt, llp);
            if(i%4==3||i==size-1){
                mostKeywordsLayout.addView(linelayout);
                linelayout=new LinearLayout(this);
                linelayout.setPadding(0, llp.rightMargin,0,0);
            }
        }
    }

    private void initRecentLayout(){
        recentKeywordsLayout.removeAllViews();
        String wordsStr=SharedPreferencesUtil.getString(this, Constant.SearchWords);
        String[] words=null;
        if(wordsStr.equals(SharedPreferencesUtil.FAILURE_STRING)) {
            words = new String[1];
            words[0]="";
        }else{
            words=wordsStr.split(":");
        }
        LinearLayout.LayoutParams llprecent=new LinearLayout.LayoutParams(-1, ImageUtil.dip2px(this, 40));
        LinearLayout.LayoutParams llpline=new LinearLayout.LayoutParams(-1, 1);
        for(int i=0;i<words.length;i++){
            TextView txt=new TextView(this);
            txt.setTextSize(14);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setTextColor(Color.GRAY);
            txt.setText(words[i]);
            txt.setTag(words[i]);
            txt.setOnClickListener(this);
            recentKeywordsLayout.addView(txt, llprecent);
            View line=new View(this);
            line.setBackgroundResource(R.color.light_gray);
            recentKeywordsLayout.addView(line, llpline);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.typeLayout:
                popup.showPopupWindow(typeLayout);
                break;
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.clearBtn:
                SharedPreferencesUtil.setString(SearchAct.this, Constant.SearchWords, SharedPreferencesUtil.FAILURE_STRING);
                initRecentLayout();
                break;
            default:
                String searchword=(String)view.getTag();
                if(searchword.length()>0) {
                    Intent i=new Intent(SearchAct.this, SearchResultAct.class);
                    i.putExtra(Constant.SearchWords, searchword);
                    startActivity(i);
                }
                break;
        }
    }
}
