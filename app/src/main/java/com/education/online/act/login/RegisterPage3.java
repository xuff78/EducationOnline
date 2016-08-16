package com.education.online.act.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class RegisterPage3 extends BaseFrameAct {

    private Button Confirmiden;
    private ImageView RoleTeacher;
    private ImageView RoleStudent;
    private LinearLayout LayoutTeacher;
    private LinearLayout LayoutStudent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page3);
       init();

    }
    private void init(){
        Confirmiden= (Button) findViewById(R.id.ConfirmIden);
        RoleTeacher= (ImageView) findViewById(R.id.RoleTeacher);
        RoleStudent= (ImageView) findViewById(R.id.RoleStudent);
        RoleStudent.setImageResource(R.mipmap.icon_round_right);
        RoleTeacher.setImageResource(R.mipmap.icon_round);
        LayoutTeacher= (LinearLayout) findViewById(R.id.LayoutTeacher);
        LayoutStudent = (LinearLayout) findViewById(R.id.LayoutStudent);
        MyRadioBtnClickListener radioclicklistener = new MyRadioBtnClickListener();
        LayoutTeacher.setOnClickListener(radioclicklistener);
        LayoutStudent.setOnClickListener(radioclicklistener);

        Confirmiden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage3.this, CompleteDataPage.class));
            }
        });
    }
    private class MyRadioBtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.LayoutStudent:
                    RoleStudent.setImageResource(R.mipmap.icon_round_right);
                    RoleTeacher.setImageResource(R.mipmap.icon_round);
                    break;
                case R.id.LayoutTeacher:
                    RoleTeacher.setImageResource(R.mipmap.icon_round_right);
                    RoleStudent.setImageResource(R.mipmap.icon_round);
                    break;
                default:

            }
        }
    }
}


