package com.education.online.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.education.online.R;
import com.education.online.fragment.HomePage;
import com.education.online.fragment.SelectorPage;
import com.education.online.http.HttpHandler;

/**
 * Created by 可爱的蘑菇 on 2016/8/15.
 */
public class MainPage extends BaseFrameAct implements View.OnClickListener{

    private int pressPos = 0;
    private View menuBtn1, menuBtn2, menuBtn3, menuBtn4;
    private HomePage home=new HomePage();
    private SelectorPage selectorPage=new SelectorPage();
    private View lastSelectedView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        _setHeaderTitle("首页");
        _setRightHome(R.mipmap.icon_query, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        _setLeftBackText("城市", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        initView();

        changePage(home);
    }

    private void initView() {
        menuBtn1=findViewById(R.id.menuBtn1);
        menuBtn1.setOnClickListener(this);
        menuBtn2=findViewById(R.id.menuBtn2);
        menuBtn2.setOnClickListener(this);
        menuBtn3=findViewById(R.id.menuBtn3);
        menuBtn3.setOnClickListener(this);
        menuBtn4=findViewById(R.id.menuBtn4);
        menuBtn4.setOnClickListener(this);
        lastSelectedView=menuBtn1;
        menuBtn1.setSelected(true);
    }

    private void changePage(Fragment frg){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_frame, frg);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        if(view!=lastSelectedView) {
            lastSelectedView.setSelected(false);
            view.setSelected(true);
            lastSelectedView=view;
            switch (view.getId()) {
                case R.id.menuBtn1:
                    changePage(home);
                    break;
                case R.id.menuBtn2:
                    Bundle b=new Bundle();
                    selectorPage.setArguments(b);
                    changePage(selectorPage);
                    break;
                case R.id.menuBtn3:
                    break;
                case R.id.menuBtn4:
                    break;
            }
        }
    }
}
