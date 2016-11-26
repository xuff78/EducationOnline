package com.education.online.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.education.online.R;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.google.zxing.WriterException;

/**
 * Created by 可爱的蘑菇 on 2016/11/26.
 */
public class QrcodeDialog  extends Dialog {

    private ImageView qrcodeImg;

    /**获取到的图片路径*/

    private Activity act;
    private String qrcode;

    public QrcodeDialog(Activity act, String qrcode) {
        super(act, R.style.view_dialog);
        this.act=act;
        this.qrcode=qrcode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qrcode);
        initView();

//        Window window = getWindow();
//        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
//        window.setWindowAnimations(R.style.dialog_popfrombottom);  //添加动画
//        window.setLayout(ScreenUtil.getWidth(act), -2);

    }
    /**
     * 初始化加载View
     */
    private void initView() {
        qrcodeImg = (ImageView) findViewById(R.id.qrcodeImg);
        try {
            Bitmap bmp = ImageUtil.Create2DCode(act, qrcode);
            qrcodeImg.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
