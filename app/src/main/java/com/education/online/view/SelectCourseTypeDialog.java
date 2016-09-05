package com.education.online.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.education.online.R;
import com.education.online.util.ScreenUtil;

/**
 * Created by Administrator on 2016/9/5.
 */
public class SelectCourseTypeDialog extends Dialog{

    private LinearLayout dialogLayout;
    private Button takePhotoBtn,pickPhotoBtn,cancelBtn;

    /**获取到的图片路径*/
    private String picPath;

    private Intent lastIntent ;

    private static Uri photoUri;
    private Activity act;
    private View.OnClickListener listener;

    public SelectCourseTypeDialog(Activity act, View.OnClickListener listener) {
        super(act, R.style.view_dialog);
        this.act=act;
        this.listener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pic_select_);
        initView();

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.dialog_popfrombottom);  //添加动画
        window.setLayout(ScreenUtil.getWidth(act), -2);

    }
    /**
     * 初始化加载View
     */
    private void initView() {
        takePhotoBtn = (Button) findViewById(R.id.btn_take_photo);
        takePhotoBtn.setText("直播课");
        takePhotoBtn.setTag(0);
        takePhotoBtn.setOnClickListener(listener);
        pickPhotoBtn = (Button) findViewById(R.id.btn_pick_photo);
        pickPhotoBtn.setTag(1);
        pickPhotoBtn.setText("视频课");
        pickPhotoBtn.setOnClickListener(listener);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setTag(2);
        cancelBtn.setText("课件");
        cancelBtn.setOnClickListener(listener);
    }
}
