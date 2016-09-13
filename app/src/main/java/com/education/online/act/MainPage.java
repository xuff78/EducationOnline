package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.education.online.R;
import com.education.online.fragment.HomePage;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.fragment.mycenter.MyCenterMain;
import com.education.online.fragment.teacher.TeacherPage;
import com.education.online.fragment.dialog.SelectorPage;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;

public class MainPage extends BaseFrameAct implements View.OnClickListener{

    private int pressPos = 0;
    private View menuBtn1, menuBtn2, menuBtn3, menuBtn4, menuBtn5;
    private HomePage home=new HomePage();
    private SelectorPage selectorPage=new SelectorPage();
    private OnlineCoursePage onlinecoursePage = new OnlineCoursePage();
    private TeacherPage teacherPage = new TeacherPage();
    private MyCenterMain centerMain=new MyCenterMain();
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
        initView();

        selectorPage.setData(new SelectorPage.CourseSelector() {
            @Override
            public void onSelected() {
                startActivity(new Intent(MainPage.this, SearchResultAct.class));
            }
        });
        changePage(home);
        initLocation();
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
        menuBtn5=findViewById(R.id.menuBtn5);
        menuBtn5.setOnClickListener(this);
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
        _setHeaderShown();
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
                    _setHeaderGone();
                    changePage(centerMain);
                    break;
                case R.id.menuBtn5:
                    _setHeaderGone();
                    changePage(teacherPage);
                    break;
            }
        }
    }

    private LocationClient mLocationClient = null;

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(500);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null)
                    return;
                SharedPreferencesUtil.setString(MainPage.this,"La", "" + location.getLatitude());
                SharedPreferencesUtil.setString(MainPage.this, "Lo", "" + location.getLongitude());
                if (mLocationClient != null && mLocationClient.isStarted()) {
                    mLocationClient.stop();
                    mLocationClient = null;
                }
                String address=location.getAddrStr();
                SharedPreferencesUtil.setString(MainPage.this, "my_address", address);
                LogUtil.d("totp", "addr:" + address);
                _setLeftBackText(location.getCity(), null);
            }

        });
        mLocationClient.start();
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        }
    }
}
