package com.education.online.act.Mine;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.teacher.CourseBaseInfoEdit;
import com.education.online.act.upyun.UploadTask;
import com.education.online.bean.OrderDetailBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/29.
 */

public class RefundSubmit extends BaseFrameAct implements View.OnClickListener{

    private TextView courseName;
    private EditText reasonEdt;
    private OrderDetailBean orderDetailBean;
    private Dialog progressDialog;
    private String phoneTxtName = "";
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private Set<String> imgs = new HashSet<>();
    private LinearLayout imgsLayout;
    private View addBtn;
    private SelectPicDialog selectPicDialog;
    private HttpHandler mHandler;

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if (method.equals(Method.getRefund)) {
                    ToastUtils.displayTextShort(RefundSubmit.this, "申请已提交");
                    setResult(0x12);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refund_submit);

        orderDetailBean= (OrderDetailBean) getIntent().getSerializableExtra("Order");
        initHandler();
        _setHeaderTitle("退款");
        initView();
    }

    private void initView() {
        selectPicDialog=new SelectPicDialog(this);
        courseName= (TextView) findViewById(R.id.courseName);
        courseName.setText(orderDetailBean.getCourse_name());
        reasonEdt= (EditText) findViewById(R.id.reasonEdt);
        imgsLayout= (LinearLayout) findViewById(R.id.imgsLayout);
        addBtn=findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        findViewById(R.id.submitBtn).setOnClickListener(this);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            File file = FileUtil.getFile(phoneTxtName + ".png", RefundSubmit.this);
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        String imgUrl = result.substring(1);
                        LogUtil.d("Img", imgUrl);
                        // imageloader.displayImage(ImageUtil.getImageUrl(course), courseImg);
                        Toast.makeText(RefundSubmit.this,"图片上传成功！",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(RefundSubmit.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "refund/"+phoneTxtName + ".png");
            }
            Bitmap photo = BitmapFactory.decodeFile(file.toString());
            if (photo != null) {
                String url="refund/"+phoneTxtName + ".png";
                addImage(url);
                imageViews.get(imageViews.size()-1).setImageBitmap(photo);
                imgs.add(url);
                if(imageViews.size()==3)
                    addBtn.setVisibility(View.GONE);
            } else
                ToastUtils.displayTextShort(RefundSubmit.this, "找不到文件");
        }
    };

    private void addImage(String url) {
        int length=ImageUtil.dip2px(this, 60);
        final ImageView img=new ImageView(this);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(length, length);
        llp.rightMargin=length/6;
        img.setLayoutParams(llp);
        img.setTag(url);
        imgsLayout.addView(img);
        imageViews.add(img);
        img.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                String url= (String) v.getTag();
                imgs.remove(url);
                imgsLayout.removeView(img);
                return true;
            }
        });
    }

    View.OnLongClickListener dellistener=new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v) {
            String url= (String) v.getTag();
            imgs.remove(url);
            imgsLayout.removeView(v);
            return true;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && (requestCode == SelectPicDialog.SELECT_PIC_BY_TACK_PHOTO ||
                requestCode == SelectPicDialog.SELECT_PIC_BY_PICK_PHOTO)) {

            progressDialog = ProgressDialog.show(this, "", "处理中。。");
            final String picPath = SelectPicDialog.doPhoto(this, requestCode, data);
            Log.i("Upload", "最终选择的图片=" + picPath);
            if (picPath == null) {
                ToastUtils.displayTextShort(this, "获取图片失败");
                progressDialog.dismiss();
                return;
            }
            final Bitmap newResizeBmp = ImageUtil.getSmallBitmap(picPath);
            if (newResizeBmp == null || newResizeBmp.isRecycled()) {
                progressDialog.dismiss();
                return;
            }

            new Thread() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    phoneTxtName = "head" + System.currentTimeMillis();
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, RefundSubmit.this);
                    if (newResizeBmp != null)
                        newResizeBmp.recycle();
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addBtn:
                selectPicDialog.show();
                break;
            case R.id.submitBtn:
                String reasonString=reasonEdt.getText().toString().trim();
                if(reasonString.length()==0){
                    ToastUtils.displayTextShort(this, "请填写原因");
                }else {
                    StringBuilder imgString=new StringBuilder();
                    for(String imgStr:imgs){
                        imgString.append(imgStr+",");
                    }
                    if(imgString.length()>0)
                        imgString.delete(imgString.length()-1, imgString.length());
                    mHandler.refundOrder(orderDetailBean.getOrder_number(), reasonString, imgString.toString());
                }
                break;
        }
    }
}
