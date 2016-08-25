package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.education.online.R;
import com.education.online.fragment.HomePage;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.fragment.dialog.SelectorPage;

public class MainPage extends BaseFrameAct implements View.OnClickListener{

    private int pressPos = 0;
    private View menuBtn1, menuBtn2, menuBtn3, menuBtn4;
    private HomePage home=new HomePage();
    private SelectorPage selectorPage=new SelectorPage();
    private OnlineCoursePage onlinecoursePage = new OnlineCoursePage();

    private View lastSelectedView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        _setHeaderTitle("首页");
        _setRightHome(R.mipmap.icon_query, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, SearchAct.class));
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
                    changePage(selectorPage);
                    break;
                case R.id.menuBtn3:
                   // changePage(onlinecoursePage);
                    break;
                case R.id.menuBtn4:
                    startActivity(new Intent(MainPage.this, CourseMainPage.class));
                    break;
            }
        }
    }
}
