package com.education.online.act.discover;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.util.ImageUtil;
import com.google.zxing.WriterException;

/**
 * Created by Administrator on 2016/9/29.
 */
public class InvitePage extends BaseFrameAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friend);

        _setHeaderTitle("推荐给朋友");

        initView();
    }

    private void initView() {
        ImageView iv=(ImageView)findViewById(R.id.qrcodeImg);
        try {
            Bitmap qrcode = ImageUtil.Create2DCode(InvitePage.this,"jffdslkfjs83293jksand");
            iv.setImageBitmap(qrcode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        //https://github.com/kyleduo/SwitchButton
    }
}
