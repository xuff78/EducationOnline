package com.education.online.act;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.ChangeTransform;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.education.online.R;
import com.education.online.act.login.LoginActivity;
import com.education.online.act.teacher.AuthMenu;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.VersionBean;
import com.education.online.fragment.DiscoveryPage;
import com.education.online.fragment.HomePage;
import com.education.online.fragment.OnlineCoursePage;
import com.education.online.fragment.mycenter.MyCenterMain;
import com.education.online.fragment.teacher.TeacherPage;
import com.education.online.fragment.dialog.SelectorPage;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.StatusBarCompat;
import com.education.online.weex.HomePageWeex;
import com.googlecode.mp4parser.Version;

import org.json.JSONException;

import de.greenrobot.event.EventBus;

public class MainPage extends BaseFrameAct implements View.OnClickListener{

    private int pressPos = 0;
    private View menuBtn1, menuBtn2, menuBtn3, menuBtn4, menuBtn5;
    private HomePageWeex home=new HomePageWeex();
    private SelectorPage selectorPage=new SelectorPage();
    private TeacherPage teacherPage = new TeacherPage();
    private MyCenterMain centerMain=new MyCenterMain();
    private DiscoveryPage discoveryPage=new DiscoveryPage();
    private View lastSelectedView=null;
    private HttpHandler handler;
    public float ScrollY=0;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                if(method.equals(Method.checkVersion)){
                    final VersionBean mBean = JSON.parseObject(jsonData, VersionBean.class);
                    //0-不更新,1-建议更新,2-强制更新,3-系统维护
                    if ("0".equals(mBean.getUpdate_type())){

                    } else if ("1".equals(mBean.getUpdate_type())||"2".equals(mBean.getUpdate_type())) {
                        DialogUtil.showConfirmDialog(MainPage.this, "新版本升级", mBean.getVersion_desc(),
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse(mBean.getUrl())));
                                    }
                                });
                    } else if ("3".equals(mBean.getUpdate_type())) {
                        DialogUtil.showInfoDialog(MainPage.this, "提示","系统正在维护中，请稍后重试",
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        StatusBarCompat.fitPage(this);

        if(getIntent().hasExtra("Login")) {
            SharedPreferencesUtil.setSessionid(this, SharedPreferencesUtil.FAILURE_STRING);
            SharedPreferencesUtil.setUsercode(this, SharedPreferencesUtil.FAILURE_STRING);
            startActivity(new Intent(MainPage.this, FirstPage.class));
            finish();
        }else if(getIntent().hasExtra("Exit"))
            finish();
        else {
            setContentView(R.layout.main_page);

            initHandler();
            _setHeaderGone();
            _setHeaderTitle("首页");
            _setLeftBackGone();
            _setRightHome(R.mipmap.icon_query, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActUtil.startAnimActivity(MainPage.this, new Intent(MainPage.this, SearchAct.class));
                }
            });
            initView();

            selectorPage.setData(new SelectorPage.CourseSelector() {
                @Override
                public void onSelected(SubjectBean subject) {
                    Intent i = new Intent(MainPage.this, SearchResultAct.class);
                    i.putExtra(Constant.SearchWords, subject.getSubject_name());
                    i.putExtra(Constant.SearchSubject, subject.getSubject_id());
                    startActivity(i);
                }
            });
            changePage(home);
            initLocation();
            if (!SharedPreferencesUtil.getString(this, Constant.UserIdentity).equals("2")) {
                menuBtn5.setVisibility(View.GONE);
            }
            handler.checkVersion(ActUtil.getVersionCode(this));
        }
    }

    private void initView() {
        EventBus.getDefault().register(this);
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

    public void toSubjectList() {
        menuBtn2.callOnClick();
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
                    _setHeaderGone();
                    changePage(home);
                    break;
                case R.id.menuBtn2:
                    _setHeaderShown();
                    _setHeaderTitle("科目");
                    changePage(selectorPage);
                    break;
                case R.id.menuBtn3:
                    _setHeaderShown();
                    _setHeaderTitle("发现");
                    changePage(discoveryPage);
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
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null)
                    return;
                SharedPreferencesUtil.setString(MainPage.this, Constant.Lat, "" + location.getLatitude());
                SharedPreferencesUtil.setString(MainPage.this, Constant.Lon, "" + location.getLongitude());
                if (mLocationClient != null && mLocationClient.isStarted()) {
                    mLocationClient.stop();
                    mLocationClient = null;
                }
                handler.updateLocation("" + location.getLatitude(), "" + location.getLongitude());
//                String address=location.getAddrStr();
//                SharedPreferencesUtil.setString(MainPage.this, Constant.Addr, address);
//                LogUtil.d("totp", "addr:" + address);
//                _setLeftBackText(location.getCity(), null);
            }

        });
        mLocationClient.start();
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private long firstBack=-1;

    private void exit() {
//        if (firstBack==-1||System.currentTimeMillis()-firstBack>2000) {
//            firstBack=System.currentTimeMillis();
//            Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                    Toast.LENGTH_SHORT).show();
//        } else {
            if(mLocationClient!=null){
                mLocationClient.stop();
            }
//            ActUtil.exitChat();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
//        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(float eventY) {
        ScrollY=eventY;
    }
}
