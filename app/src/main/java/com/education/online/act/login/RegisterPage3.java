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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page3);
        Confirmiden= (Button) findViewById(R.id.ConfirmIden);
        Confirmiden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(RegisterPage3.this, CompleteDataPage.class));
            }
        });
    }

}
