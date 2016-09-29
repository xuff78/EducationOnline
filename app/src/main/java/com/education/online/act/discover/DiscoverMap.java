package com.education.online.act.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/29.
 */
public class DiscoverMap extends BaseFrameAct implements View.OnClickListener, BaiduMap.OnMarkerClickListener{

    public MapView mMapView;
    public BaiduMap baiduMap;
    private LayoutInflater inflater;
    private BDLocation myLocation;
    private LocationClient locationClient;
    private BDLocationListener myListener = new MyLocationListener();// 注册定位监听，返回定位的结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_map);

        _setHeaderTitle("hi同学");
        inflater=LayoutInflater.from(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.locationBtn).setOnClickListener(this);
        mMapView = (MapView) findViewById(R.id.mMapView);
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(true);
        baiduMap = mMapView.getMap();
        baiduMap.setOnMarkerClickListener(this);

        locationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(false);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(myListener);
        locationClient.start();

        View me=inflater.inflate(R.layout.people_marker_view, null);
        View headerBg=me.findViewById(R.id.headFrame);
        headerBg.setBackgroundResource(R.mipmap.icon_marker_me);
        BitmapDescriptor iconLocation = BitmapDescriptorFactory.fromView(me);
        MyLocationConfiguration configuration=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, iconLocation);
        baiduMap.setMyLocationConfigeration(configuration);
        baiduMap.setMyLocationEnabled(true);

//        View v=inflater.inflate(R.layout.people_marker_view, null);
//        Bundle b=new Bundle();
//        LatLng ll=new LatLng();
//        addMarkerToMap()
    }

    @Override
    public void onClick(View view) {
        if(myLocation!=null) {
            LatLng locationLL = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(locationLL);
            baiduMap.animateMapStatus(mapStatusUpdate);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle markerExtraInfo = marker.getExtraInfo();
        return false;
    }

    public void addMarkerToMap(LatLng point, Bundle bundle, View markerView) {
        try {
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(markerView);//fromResource(R.mipmap.touxiang2x);
            int height = markerView.getHeight();//获取marker的高度

            //构建MarkerOption，用于在地图上添加Marker  OverlayOptions
            MarkerOptions option = new MarkerOptions()
                    .position(point)
                    .perspective(true)
                    .icon(bitmapDescriptor);
//                    .zIndex(index);
            //在地图上添加Marker，并显示
            Marker marker = (Marker) (baiduMap.addOverlay(option));
            bundle.putInt("height", height);
            marker.setExtraInfo(bundle);
        } catch (Exception e) {

        }
    }
    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null) {
                myLocation=bdLocation;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationClient.stop();
    }
}
