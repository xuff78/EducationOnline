package com.education.online.weex;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.education.online.R;
import com.education.online.act.SearchAct;
import com.education.online.fragment.BaseFragment;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.circularbtn.MorphingAnimation;
import com.education.online.view.circularbtn.MorphingButton;
import com.education.online.view.refreshlayout.MaterialRefreshLayout;
import com.education.online.view.refreshlayout.MaterialRefreshListener;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HomePageWeex extends BaseFragment implements IWXRenderListener {


    //    private static String TEST_URL = "http://dotwe.org/raw/dist/6fe11640e8d25f2f98176e9643c08687.bundle.js";
//    private static String TEST_URL = "http://106.75.91.154:8899/advpage.js";
    private static String TEST_URL = "http://192.168.199.233:8080/dist/HomePage.js";
//    private static String TEST_URL = "http://172.16.10.66:8080/dist/HomePage.js";
    private WXSDKInstance mWXSDKInstance;
    private FrameLayout mContainer;
    private MorphingButton.Params paramStart, paramEnd;
    private MorphingButton toolbarIconRight;
    private MaterialRefreshLayout mSwipeLayout;
    public float ScrollY=0;
    private View headerLayout;
    private boolean btnRound=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_weex_page, container, false);

        initView(view);

        mContainer = (FrameLayout) view.findViewById(R.id.container);
        mWXSDKInstance = new WXSDKInstance(getActivity());
        mWXSDKInstance.registerRenderListener(this);
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, TEST_URL);
        mWXSDKInstance.renderByUrl("EducationOnline",TEST_URL,options,null, WXRenderStrategy.APPEND_ONCE);
        return view;
    }

    private void initView(View v) {

        mSwipeLayout = (MaterialRefreshLayout)v.findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setSunStyle(false);
        mSwipeLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                mSwipeLayout.finishRefresh();
            }

            @Override
            public boolean canScroll() {
                return ScrollY<-1;
            }

            @Override
            public void onfinish() {

            }


        });
        toolbarIconRight= (MorphingButton) v.findViewById(R.id.toolbarIconRight);
        toolbarIconRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActUtil.startAnimActivity(getActivity(), new Intent(getActivity(), SearchAct.class));
            }
        });
        paramStart= MorphingButton.Params.create()
                .strokeColor(getResources().getColor(R.color.light_gray))
                .strokeWidth(2)
                .color(Color.WHITE)
                .colorPressed(Color.WHITE)
                .duration(300)
                .height(ImageUtil.dip2px(getActivity(),32))
                .width(ImageUtil.dip2px(getActivity(),32))
                .cornerRadius(ImageUtil.dip2px(getActivity(), 16))
                .icon(R.mipmap.icon_query);
        int width= ScreenUtil.getWidth(getActivity())-ImageUtil.dip2px(getActivity(),20);
        paramEnd= MorphingButton.Params.create()
                .strokeColor(getResources().getColor(R.color.light_gray))
                .strokeWidth(2)
                .color(Color.WHITE)
                .colorPressed(Color.WHITE)
                .duration(300)
                .height(ImageUtil.dip2px(getActivity(),32))
                .width(width)
                .cornerRadius(ImageUtil.dip2px(getActivity(), 16))
                .animationListener(new MorphingAnimation.Listener() {
                    @Override
                    public void onAnimationEnd() {
                        toolbarIconRight.setPadding(0,10,0,10);
                        toolbarIconRight.setText("点击搜索课程");
                    }
                });
        toolbarIconRight.morph(paramStart);
        headerLayout=v.findViewById(R.id.headerLayout);
        headerLayout.setAlpha(0);
    }

    public void setScrollY(float eventY){
        ScrollY=eventY;
        float alpha=Math.abs(ScrollY)/355;
        headerLayout.setAlpha(alpha);
        if(alpha>1){
            if(btnRound) {
                toolbarIconRight.morph(paramEnd);
                btnRound=false;
            }
        }else{
            if(!btnRound) {
                toolbarIconRight.morph(paramStart);
                toolbarIconRight.setText("");
                btnRound=true;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        mContainer.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }
}
