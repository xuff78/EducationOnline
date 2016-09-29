package com.education.online.act.discover;

import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;

/**
 * Created by Administrator on 2016/9/29.
 */
public class DiscoverMap extends BaseFrameAct implements View.OnClickListener, BaiduMap.OnMarkerClickListener{

    public MapView mMapView;
    public BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_map);

        _setHeaderTitle("hi同学");
        initView();
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.mMapView);
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(true);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        findViewById(R.id.locationBtn).setOnClickListener(this);
        baiduMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle markerExtraInfo = marker.getExtraInfo();
        return false;
    }
}
