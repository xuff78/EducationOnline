package com.education.online.act;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.education.online.R;
import com.education.online.util.Constant;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/12/2.
 */
public class CM_TargetLocationAct extends BaseFrameAct  implements BaiduMap.OnMapLongClickListener, OnGetGeoCoderResultListener
            , BaiduMap.OnMapStatusChangeListener{

    public static final String LocationSelection="LocationSelection";
    public MapView mMapView;
    public BaiduMap baiduMap;
//    public Marker mMarker = null;
    private GeoCoder geoCoder = null;
    private String lastLocation=null;
    private ImageView edit_address, targetCenter;
    private LinearLayout bottom_edit_layout;
    private EditText edit_address_content;
    private RelativeLayout bottom_layout;
    private LatLng mPoint=null;

    public static final int GetLocation=0x22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.targetlocation_act);

        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(this);
        _setHeaderTitle("位置信息");
        initView();

        //显示地理位置的情况
        if(getIntent().hasExtra(CM_TargetLocationAct.LocationSelection)) {
            if (getIntent().hasExtra(Constant.Lon) && getIntent().hasExtra(Constant.Lat)) {
                Double lon = getIntent().getDoubleExtra(Constant.Lon, 0);
                Double lat = getIntent().getDoubleExtra(Constant.Lat, 0);
                mPoint = new LatLng(lat, lon);
                showCurrentPosition(false);
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(mPoint)
                        .zoom(19)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                baiduMap.setMapStatus(mMapStatusUpdate);
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(mPoint));
            }else
                showCurrentPosition(true);
            bottom_layout.setVisibility(View.VISIBLE);
            targetCenter.setVisibility(View.VISIBLE);
            //选择发送地点的场景
            final String currentlon = SharedPreferencesUtil.getString(this, Constant.Lon);
            final String currentlat = SharedPreferencesUtil.getString(this, Constant.Lat);
            lastLocation = SharedPreferencesUtil.getString(CM_TargetLocationAct.this, Constant.Addr);
//            baiduMap.setOnMapLongClickListener(this);
            baiduMap.setOnMapStatusChangeListener(this);
            _setRightHomeText("完成", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    if (mPoint != null) {//手动选择了地图点
//                        mPoint=mMarker.getPosition();
                        i.putExtra(Constant.Lon, mPoint.longitude);
                        i.putExtra(Constant.Lat, mPoint.latitude);
                    } else {//没有手动选择，默认为自己的位置
                        i.putExtra(Constant.Lon, Double.valueOf(currentlon));
                        i.putExtra(Constant.Lat, Double.valueOf(currentlat));
                    }
                    //判断是否描述为空
                    if (!edit_address_content.getText().toString().trim().equals("")) {
                        //lastLocation += " ("+edit_address_content.getText().toString().trim()+")";
                        lastLocation = edit_address_content.getText().toString().trim();
                    }
                    if (lastLocation != null && lastLocation.length() > 0) {
                        i.putExtra(Constant.Addr, lastLocation);
                        setResult(RESULT_OK, i);
                        finish();
                    } else
                        ToastUtils.displayTextShort(CM_TargetLocationAct.this, "未获取到地址，请重新选择");
                }
            });
        }else{
            targetCenter.setVisibility(View.GONE);
            Double lon = getIntent().getDoubleExtra(Constant.Lon, 0);
            Double lat = getIntent().getDoubleExtra(Constant.Lat, 0);
            showCurrentPosition(false);
            showTargetPosition(lon, lat, R.mipmap.icon_location_target, true, false);
        }
    }

    private void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(true);
        baiduMap = mMapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);

        targetCenter=(ImageView)findViewById(R.id.targetCenter);
        bottom_layout=(RelativeLayout)findViewById(R.id.bottom_layout);
        bottom_edit_layout=(LinearLayout)findViewById(R.id.bottom_edit_layout);
//        final Animation openAnimation= AnimationUtils.loadAnimation(CM_TargetLocationAct.this,R.anim.edit_et_open);
//        final Animation closeAnimation= AnimationUtils.loadAnimation(CM_TargetLocationAct.this,R.anim.edit_et_close);
        edit_address_content=(EditText)findViewById(R.id.edit_address_content);
        edit_address=(ImageView)findViewById(R.id.edit_address);
       /* edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottom_edit_layout.getVisibility() == View.GONE) {
                    bottom_edit_layout.setVisibility(View.VISIBLE);
                    bottom_edit_layout.startAnimation(openAnimation);
                } else {
                    bottom_edit_layout.setVisibility(View.GONE);
                    bottom_edit_layout.startAnimation(closeAnimation);
                }
            }
        });*/
    }

    public void showTargetPosition(Double lon, Double lat, int res, boolean moveTo, boolean self) {
        //添加自己的定位
        LatLng point = new LatLng(lat, lon);
        BitmapDescriptor myBitmapDescriptor = BitmapDescriptorFactory.fromResource(res);
        //构建MarkerOption，用于在地图上添加Marker  OverlayOptions
        MarkerOptions option = new MarkerOptions()
                .position(point)
                .icon(myBitmapDescriptor).zIndex(0);
//        option.animateType(MarkerOptions.MarkerAnimateType.grow);
        //在地图上添加Marker，并显示
        Marker mMarker = (Marker) (baiduMap.addOverlay(option));
        //定义地图状态
        if(moveTo) {
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(point)
                    .zoom(19)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            baiduMap.setMapStatus(mMapStatusUpdate);
        }

        if(self)
            mMarker=null;
        else
            lastLocation=null;
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));

    }

    public void showCurrentPosition(boolean moveTo) {

        String lon = SharedPreferencesUtil.getString(this, Constant.Lon);
        String lat = SharedPreferencesUtil.getString(this, Constant.Lat);
        //添加自己的定位
        LatLng point2 = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

        MyLocationData locData = new MyLocationData.Builder()
                .latitude(point2.latitude)
                .longitude(point2.longitude).build();
        // 设置定位数据
        baiduMap.setMyLocationData(locData);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.dian);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
        baiduMap.setMyLocationConfigeration(config);

        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point2)
                .zoom(19)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        if(moveTo) {
            baiduMap.setMapStatus(mMapStatusUpdate);
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point2));
        }
    }

    ValueAnimator animator=null;
    int markerY=-1;
    private void startAnimation(){
        if(animator!=null&&animator.isRunning())
            animator.cancel();
        if(markerY==-1)
            markerY=(int)targetCenter.getY();
        animator = ValueAnimator.ofInt(0, -60, 0).setDuration(800);
        animator.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.anim.accelerate_decelerate_interpolator));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animaY= (Integer) valueAnimator.getAnimatedValue();
                targetCenter.setY(markerY+animaY);
            }
        });
        animator.start();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        //判断是否获得定位的poi数据
        List<PoiInfo> poiList=result.getPoiList();
        if(poiList != null && poiList.size() != 0){
            lastLocation=poiList.get(0).name;
        }else{
            lastLocation=result.getAddress();
        }
        edit_address_content.setText(lastLocation);
        //ToastUtils.displayTextShort(this, lastLocation);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        showTargetPosition(latLng.longitude, latLng.latitude, R.mipmap.icon_location_target, false, false);
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {
    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        LatLng cenpt = mapStatus.target;
        mPoint=cenpt;
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
        startAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
    }
}
