package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.fragment.SelectorOrder;
import com.education.online.fragment.SelectorPage;
import com.education.online.http.HttpHandler;
import com.education.online.util.ToastUtils;
import com.education.online.view.MenuPopup;

/**
 * Created by Administrator on 2016/8/17.
 */
public class SearchResultAct extends BaseFrameAct implements View.OnClickListener{

    HttpHandler handler;
    private TextView typeTxt;
    private EditText searchEdt;
    private View typeLayout, menuBtn1, menuBtn2, menuBtn3, transblackBg;
    private FrameLayout filterDetailLayout;
    private MenuPopup popup;
    private String[] typeStrs={"课程", "老师", "视频"};
    private int type=0;
    private Fragment selectorPage, selectorByOrder;
    private boolean filterShown=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_act);

        _setHeaderGone();
        initView();
        initFrgment();
    }

    private void initFrgment() {
        selectorPage=new SelectorPage();
        selectorByOrder=new SelectorOrder();
    }

    private void initView() {
        popup = new MenuPopup(SearchResultAct.this, typeStrs, new AdapterView.OnItemClickListener() {
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
                if(actionId== EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_UNSPECIFIED||actionId==EditorInfo.IME_ACTION_SEARCH){
                    if(!searchEdt.getText().toString().trim().equals(""))
                    {
                        startActivity(new Intent(SearchResultAct.this, SearchResultAct.class));
                    }else
                        ToastUtils.displayTextShort(SearchResultAct.this, "请填写搜索关键字");
                }
                return false;
            }
        });

        transblackBg=findViewById(R.id.transblackBg);
        filterDetailLayout= (FrameLayout) findViewById(R.id.fragment_frame);
        menuBtn1=findViewById(R.id.menuBtn1);
        menuBtn1.setOnClickListener(this);
        menuBtn2=findViewById(R.id.menuBtn2);
        menuBtn2.setOnClickListener(this);
        menuBtn3=findViewById(R.id.menuBtn3);
        menuBtn3.setOnClickListener(this);

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
                break;
            case R.id.menuBtn1:
                openOrCloseFilterLayout(selectorPage, 0);
                break;
            case R.id.menuBtn2:
                openOrCloseFilterLayout(selectorByOrder, 1);
                break;
            case R.id.menuBtn3:
//                openOrCloseFilterLayout(selectorByOrder, 2);
                break;
        }
    }

    private void openOrCloseFilterLayout(Fragment frg, int typeNew){
        if(type!=typeNew){
            changePage(frg);
            filterDetailLayout.setVisibility(View.VISIBLE);
            transblackBg.setVisibility(View.VISIBLE);
        }else if(filterShown){
            removePage(frg);
            filterDetailLayout.setVisibility(View.GONE);
            transblackBg.setVisibility(View.GONE);
        }else{
            changePage(frg);
            filterDetailLayout.setVisibility(View.VISIBLE);
            transblackBg.setVisibility(View.VISIBLE);
        }
        type=typeNew;
    }

    private void changePage(Fragment frg){
        filterShown=true;
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_frame, frg);
        ft.commit();
    }

    private void removePage(Fragment frg){
        filterShown=false;
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.remove(frg);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(filterShown) {
            filterDetailLayout.setVisibility(View.GONE);
            transblackBg.setVisibility(View.GONE);
            filterShown=false;
        }else
            super.onBackPressed();
    }
}
