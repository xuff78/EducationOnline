package com.education.online.act.discovery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baidu.mapapi.model.LatLng;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.bean.TeacherBean;
import com.education.online.bean.UserInfo;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/9/27.
 */
public class StudentIntroduction extends BaseFrameAct implements View.OnClickListener{

    private HttpHandler mHandler;
    private TeacherBean teacherInfo;
    private ImageLoader imageLoader;
    private String myUsercode;
    private ImageView starIcon;

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                if(method.equals(Method.nearUser)){

                }else if(method.equals(Method.getUserInfo)){

                }else if(method.equals(Method.addAttention)){
                    if(teacherInfo.getIs_attention().equals("0")) {
                        starIcon.setImageResource(R.mipmap.icon_star_red);
                        teacherInfo.setIs_attention("1");
                        ToastUtils.displayTextShort(StudentIntroduction.this, "成功关注");
                    }else if(teacherInfo.getIs_attention().equals("1")) {
                        starIcon.setImageResource(R.mipmap.icon_star);
                        teacherInfo.setIs_attention("0");
                        ToastUtils.displayTextShort(StudentIntroduction.this, "取消关注");
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_introduction);

        myUsercode= SharedPreferencesUtil.getUsercode(this);
        imageLoader=ImageLoader.getInstance();
        teacherInfo= JSON.parseObject(getIntent().getStringExtra("jsonData"), TeacherBean.class);
        initHandler();
        _setHeaderGone();

        initView();
    }

    private void initView() {
        starIcon= (ImageView) findViewById(R.id.starIcon);
        if(myUsercode.equals(teacherInfo.getUsercode()))
            findViewById(R.id.bottomLayout).setVisibility(View.GONE);
        findViewById(R.id.myQrcode).setOnClickListener(this);
        findViewById(R.id.toChatBtn).setOnClickListener(this);
        findViewById(R.id.payattentionBtn).setOnClickListener(this);
        ImageView teacherImg= (ImageView) findViewById(R.id.teacherImg);
        imageLoader.displayImage(ImageUtil.getImageUrl(teacherInfo.getAvatar()), teacherImg);
        TextView nameTxt= (TextView) findViewById(R.id.nameTxt);
        nameTxt.setText(teacherInfo.getName());
        TextView sexTxt= (TextView) findViewById(R.id.sexTxt);
        if(teacherInfo.getGender().equals("1")){
            sexTxt.setText("男");
        }else if(teacherInfo.getGender().equals("0")){
            sexTxt.setText("女");
        }
        TextView descTxt= (TextView) findViewById(R.id.descTxt);
        descTxt.setText(teacherInfo.getIntroduction());
        TextView attentionNum= (TextView) findViewById(R.id.attentionNum);
        attentionNum.setText(teacherInfo.getTo_attention_count());
        TextView fansNum= (TextView) findViewById(R.id.fansNum);
        fansNum.setText(teacherInfo.getAttention_count());
        TextView nickName= (TextView) findViewById(R.id.nickName);
        nickName.setText(teacherInfo.getName());
        TextView sexType= (TextView) findViewById(R.id.sexType);
        if(teacherInfo.getGender().equals("1")){
            sexType.setText("男");
        }else if(teacherInfo.getGender().equals("0")){
            sexType.setText("女");
        }
        TextView xingzuo= (TextView) findViewById(R.id.xingzuo);
        if(teacherInfo.getBirthday().length()>0&&teacherInfo.getBirthday().contains("-")) {
            String[] birthday = teacherInfo.getBirthday().split("-");
            xingzuo.setText(ActUtil.getConstellation(Integer.valueOf(birthday[1]), Integer.valueOf(birthday[2])));
        }
        TextView detailTxt= (TextView) findViewById(R.id.detailTxt);
        detailTxt.setText(teacherInfo.getIntroduction());
        TextView interestingCourse= (TextView) findViewById(R.id.interestingCourse);
        interestingCourse.setText(teacherInfo.getInterest_info());
        TextView askId= (TextView) findViewById(R.id.askId);
        askId.setText(teacherInfo.getUsercode());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toChatBtn:
                ActUtil.goChat(teacherInfo.getUsercode(), StudentIntroduction.this);
//                startActivity(new Intent(StudentIntroduction.this, ChatPage.class));
                break;
            case R.id.myQrcode:
                break;
            case R.id.payattentionBtn:
                break;
        }
    }
}
