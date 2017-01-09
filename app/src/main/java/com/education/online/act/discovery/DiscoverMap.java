package com.education.online.act.discovery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.UserInfo;
import com.education.online.bean.VideoImgItem;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;

import java.util.ArrayList;

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
    private Marker loactionMarker;
    private Overlay roundRate=null;
    private HttpHandler mHandler;
    private ArrayList<UserInfo> users=new ArrayList<>();
    private String myUsercode="";
    private ImageLoader imageLoader;

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                if(method.equals(Method.nearUser)){
                    String json= JsonUtil.getString(jsonData, "user_info");
                    users= JSON.parseObject(json, new TypeReference<ArrayList<UserInfo>>(){});
                    for(final UserInfo user:users){
                        if(!myUsercode.equals(user.getUsercode())) {
                            final View v = inflater.inflate(R.layout.people_marker_view, null);
                            final ImageView headIcon = (ImageView) v.findViewById(R.id.headIcon);
                            imageLoader.loadImage(ImageUtil.getImageUrl(user.getAvatar()),new SimpleImageLoadingListener(){
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    headIcon.setImageBitmap(loadedImage);
                                    Bundle b = new Bundle();
                                    b.putSerializable("UserInfo", user);
                                    LatLng ll = new LatLng(user.getLatitude(), user.getLongitude());
                                    addMarkerToMap(ll, b, v);
                                }
                            });
                        }
                    }
                }else if(method.equals(Method.getUserInfo)){
                    Intent intent=new Intent(DiscoverMap.this, StudentNew.class);
                    intent.putExtra("jsonData", jsonData);
//                    startActivity(intent);
                    ActUtil.startAnimActivity(DiscoverMap.this, intent);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_map);

        imageLoader=ImageLoader.getInstance();
        initHandler();
        _setHeaderTitle("hi同学");
        inflater=LayoutInflater.from(this);
        myUsercode=SharedPreferencesUtil.getUsercode(this);
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
        option.setScanSpan(5000);
        option.setIsNeedAddress(false);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(myListener);
        locationClient.start();

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
        UserInfo user= (UserInfo) markerExtraInfo.getSerializable("UserInfo");
        if(user==null) {
            user=new UserInfo();
            user.setUsercode(myUsercode);
        }
        mHandler.getUserInfo(user.getUsercode());
        return false;
    }

    public void showWorkingSpace() {
        if(roundRate!=null)
            roundRate.remove();
        LatLng llCircle = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        /**
         *CircleOptions:创建圆的选项
         *   fillColor(int color):设置圆填充颜色
         *   center(LatLng center):设置圆心坐标
         *   stroke(Stroke stroke):设置圆边框信息
         *
         *Stroke:边框类，可以给圆、多边形设置一个边框
         *   Stroke(int strokeWidth, int color):
         *      color:边框的颜色
         *      strokeWidth:边框的宽度， 默认为 5， 单位：像素
         * */

        OverlayOptions ooCircle = new CircleOptions()
                .fillColor(0x331b93e5)
                .center(llCircle).stroke(new Stroke(2, 0xff1b93e5))
                .radius(3000);
        roundRate=baiduMap.addOverlay(ooCircle);

//        OverlayOptions ooDot = new DotOptions().center(llCircle).radius(6)
//                .color(0xFF0000FF);
//        baiduMap.addOverlay(ooDot);
    }

    public Marker addMarkerToMap(LatLng point, Bundle bundle, View markerView) {
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
            return marker;
        } catch (Exception e) {

        }
        return null;
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null) {
                myLocation=bdLocation;
                LatLng ll=new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

                final View me = inflater.inflate(R.layout.people_marker_view, null);
                View headerBg = me.findViewById(R.id.headFrame);
                final ImageView headIcon= (ImageView) me.findViewById(R.id.headIcon);
                headerBg.setBackgroundResource(R.mipmap.icon_marker_me);

                //清除上次的点
                if(loactionMarker!=null)
                    loactionMarker.remove();
                else{
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 15);
                    baiduMap.animateMapStatus(u);
                }

                imageLoader.loadImage(ImageUtil.getImageUrl(SharedPreferencesUtil.getString(DiscoverMap.this, Constant.Avatar)),
                        new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        headIcon.setImageBitmap(loadedImage);
                        Bundle b = new Bundle();
                        LatLng ll = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        loactionMarker=addMarkerToMap(ll, b, me);
                    }
                });
                showWorkingSpace();

                SharedPreferencesUtil.setString(DiscoverMap.this, Constant.Lat, "" + bdLocation.getLatitude());
                SharedPreferencesUtil.setString(DiscoverMap.this, Constant.Lon, "" + bdLocation.getLongitude());
                mHandler.nearUser(myLocation.getLatitude()+"", myLocation.getLongitude()+"");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationClient.stop();
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
        try {
            mMapView.onDestroy();
        }catch (Exception e){

        }
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
    }
}
