package com.education.online.act.pushlive;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.education.online.R;
import com.education.online.bean.CourseDetailBean;
import com.education.online.util.SharedPreferencesUtil;
import com.upyun.hardware.Config;
import com.upyun.hardware.PushClient;

import java.io.IOException;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class LiveCameraPage extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private SurfaceRenderView surface;
    private PushClient mClient;
    private ImageView mBtToggle;
    private ImageView mBtSetting;
    private ImageView mBtconvert;
    private ImageView mImgFlash;
    private Config config;
    private String mNotifyMsg;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    private static final int REQUEST_CODE_PERMISSION_RECORD_AUDIO = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_camera);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        CourseDetailBean courseBean= (CourseDetailBean) getIntent().getSerializableExtra(CourseDetailBean.Name);
        surface = (SurfaceRenderView) findViewById(R.id.sv_camera);
        mBtToggle = (ImageView) findViewById(R.id.bt_toggle);
        mBtSetting = (ImageView) findViewById(R.id.bt_setting);
        mBtconvert = (ImageView) findViewById(R.id.btn_camera_switch);
        mImgFlash = (ImageView) findViewById(R.id.btn_flashlight_switch);
        mBtToggle.setOnClickListener(this);
        mBtSetting.setOnClickListener(this);
        mBtconvert.setOnClickListener(this);
        mImgFlash.setOnClickListener(this);
        mImgFlash.setEnabled(true);

        mClient = new PushClient(surface);


        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                mNotifyMsg = ex.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, mNotifyMsg);
                        mClient.stopPush();
                        mBtToggle.setAlpha(255);
                    }
                });
            }
        });

        // check permission for 6.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }

        surface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mClient != null) {
                        //mClient.focusOnTouch();
                    }
                }
                return true;
            }
        });


        config = new Config.Builder().build();
        config.url=config.url+courseBean.getCourse_id();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (config != null) {
            mClient.setConfig(config);
        }
        changeSurfaceSize(surface, mClient.getConfig());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_toggle:
                if (mClient.isStart()) {
                    mClient.stopPush();
                    Log.e(TAG, "stop");
                    mBtToggle.setAlpha(255);
                } else {
                    try {
                        mClient.startPush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, e.toString());
                        return;
                    }
                    Log.e(TAG, "start");
                    mBtToggle.setAlpha(155);
                }
                break;
            case R.id.bt_setting:
                mClient.stopPush();
                mBtToggle.setAlpha(255);
//                startActivity(new Intent(this, SettingActivity.class));
                break;

            case R.id.btn_camera_switch:
                boolean converted = mClient.covertCamera();
                if (converted) {
                    mImgFlash.setEnabled(!mImgFlash.isEnabled());
                }
                break;

            case R.id.btn_flashlight_switch:
                mClient.toggleFlashlight();
                break;
        }
    }

    private void changeSurfaceSize(SurfaceRenderView surface, Config config) {
        int width = 1280;
        int height = 720;

        switch (config.resolution) {
            case HIGH:
                width = 1280;
                height = 720;
                break;
            case NORMAL:
                width = 640;
                height = 480;
                break;
            case LOW:
                width = 320;
                height = 240;
                break;
        }

//        ViewGroup.LayoutParams lp = surface.getLayoutParams();
//
//        lp.width = height;
//        lp.height = width;
//        surface.setLayoutParams(lp);
        surface.setVideoSize(height, width);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClient != null) {
            mClient.stopPush();
        }
    }

    // check permission
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION_RECORD_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "granted camera permission");
            } else {
                Log.e(TAG, "no camera permission");
            }
        } else if (requestCode == REQUEST_CODE_PERMISSION_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "granted record audio permission");
            } else {
                Log.d(TAG, "no record audio permission");
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

