package com.education.online.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.education.online.R;
import com.education.online.util.ScreenUtil;
import com.upyun.hardware.Config;

/**
 * Created by Administrator on 2017/2/14.
 */

public class ResolutionSelectDialog extends Dialog implements View.OnClickListener {

    private Activity act;
    private OnClickCallBack cb;
    private Config.Resolution resolution;

    public ResolutionSelectDialog(Activity act, Config.Resolution resolution, OnClickCallBack cb) {
        super(act, R.style.view_dialog);
        this.act=act;
        this.cb=cb;
        this.resolution=resolution;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_resolution_layout);
        initView();
    }
    /**
     * 初始化加载View
     */
    private void initView() {
        AppCompatRadioButton rescb1= (AppCompatRadioButton) findViewById(R.id.rescb1);
        AppCompatRadioButton rescb2= (AppCompatRadioButton) findViewById(R.id.rescb2);
        AppCompatRadioButton rescb3= (AppCompatRadioButton) findViewById(R.id.rescb3);
        rescb1.setOnClickListener(this);
        rescb2.setOnClickListener(this);
        rescb3.setOnClickListener(this);
        if(resolution==Config.Resolution.HIGH)
            rescb1.setChecked(true);
        if(resolution==Config.Resolution.NORMAL)
            rescb2.setChecked(true);
        if(resolution==Config.Resolution.LOW)
            rescb3.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rescb1) {
            cb.onItemClick(Config.Resolution.HIGH);

        } else if (i == R.id.rescb2) {
            cb.onItemClick(Config.Resolution.NORMAL);

        } else if (i == R.id.rescb3) {
            cb.onItemClick(Config.Resolution.LOW);
        }
        dismiss();
    }

    public interface OnClickCallBack{
        void onItemClick(Config.Resolution resolution);
    }
}
