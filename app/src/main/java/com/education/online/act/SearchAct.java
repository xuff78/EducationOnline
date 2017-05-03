package com.education.online.act;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.Mine.MyOrderUser;
import com.education.online.act.Mine.UserOrderDetail;
import com.education.online.bean.HotWord;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.OrderDetailBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.StatusBarCompat;
import com.education.online.util.ToastUtils;
import com.education.online.view.AutoFitLinearLayout;
import com.education.online.view.MenuPopup;

import java.util.ArrayList;
import java.util.List;

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
        if(Build.VERSION.SDK_INT>=21) {
            getWindow().setEnterTransition(new Fade().setDuration(500));
            getWindow().setExitTransition(new Fade().setDuration(500));
        }
        setContentView(R.layout.search_act);
        initHandler();
        handler.getHotWord();
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
                popup.dismiss();
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
                        if(word.length()>0)
                        if(wordsStr.equals(SharedPreferencesUtil.FAILURE_STRING)){
                            wordsStr=word;
                            SharedPreferencesUtil.setString(SearchAct.this, Constant.SearchWords, wordsStr);
                        }else{
                            String[] words=wordsStr.split(":");
                            if(words.length==5){
                                wordsStr=wordsStr.substring(0, wordsStr.lastIndexOf(":")-1);
                            }
                            wordsStr=word+":"+wordsStr;
                            SharedPreferencesUtil.setString(SearchAct.this, Constant.SearchWords, wordsStr);
                        }
                        startAnimAcitivity(word);
                }
                return false;
            }
        });

        mostKeywordsLayout= (LinearLayout) findViewById(R.id.mostKeywordsLayout);
        recentKeywordsLayout= (LinearLayout) findViewById(R.id.recentKeywordsLayout);
        findViewById(R.id.clearBtn).setOnClickListener(this);
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
        int itemnum=words.length>5?5:words.length;
        for(int i=0;i<itemnum;i++){
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
                KeyBoardCancle();
                finish();
                break;
            case R.id.clearBtn:
                SharedPreferencesUtil.setString(SearchAct.this, Constant.SearchWords, SharedPreferencesUtil.FAILURE_STRING);
                initRecentLayout();
                break;
            default:
                String searchword=(String)view.getTag();
                if(searchword.length()>0) {
                    startAnimAcitivity(searchword);
                }
                break;
        }
    }

    private void startAnimAcitivity(String searchword){
        Intent i=new Intent(SearchAct.this, SearchResultAct.class);
        i.putExtra(Constant.SearchWords, searchword);
        i.putExtra("Type", type);
        if(Build.VERSION.SDK_INT>=21) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(findViewById(R.id.editLayout), "searchEditLayout")).toBundle();
            startActivity(i, bundle);
        }else{
            startActivity(i);
        }
    }

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData){
                if(method.equals(Method.getHotWord)){
                    List<HotWord> words= JSON.parseObject(jsonData, new TypeReference<List<HotWord>>(){});
                    int itemWidth = (ScreenUtil.getWidth(SearchAct.this)- ImageUtil.dip2px(SearchAct.this,90))/4;
                    int itemHeight = ImageUtil.dip2px(SearchAct.this,30);
                    LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(itemWidth, itemHeight);
                    llp.rightMargin=ImageUtil.dip2px(SearchAct.this, 15);
                    LinearLayout linelayout=new LinearLayout(SearchAct.this);
                    for(int i=0;i<words.size();i++){
                        String word=words.get(i).getSubject_name();
                        TextView txt=new TextView(SearchAct.this);
                        txt.setTextSize(13);
                        txt.setGravity(Gravity.CENTER);
                        txt.setBackgroundResource(R.drawable.shape_corner_blackline);
                        txt.setTextColor(Color.GRAY);
                        txt.setText(word);
                        txt.setTag(word);
                        txt.setOnClickListener(SearchAct.this);
                        linelayout.addView(txt, llp);
                        if(i%4==3||i==words.size()-1){
                            mostKeywordsLayout.addView(linelayout);
                            linelayout=new LinearLayout(SearchAct.this);
                            linelayout.setPadding(0, llp.rightMargin,0,0);
                        }
                    }
                }
            }
        });
    }
}
