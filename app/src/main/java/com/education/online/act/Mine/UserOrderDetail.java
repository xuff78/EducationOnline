package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVUser;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.AVUserCacheUtils;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.CourseMainPage;
import com.education.online.act.VideoMainPage;
import com.education.online.bean.CourseBean;
import com.education.online.bean.OrderDetailBean;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class UserOrderDetail extends BaseFrameAct {


    private ImageView teacherImg;
    private TextView payMoney, orderId, createTime, teacherName, courseTitle, courseNum, priceTxt, payTxt, labelTxt;
    private LinearLayout courseLayout;
    private OrderDetailBean orderDetailBean;
    private String jsonData="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_detail);

        jsonData=getIntent().getStringExtra("jsonData");
        orderDetailBean= JSON.parseObject(jsonData, OrderDetailBean.class);
        _setHeaderTitle("订单详情");
        initView();
    }

    private void initView() {
        String user_info=JsonUtil.getString(jsonData, "user_info");
        teacherImg= (ImageView) findViewById(R.id.teacherImg);
        ImageLoader.getInstance().displayImage(ImageUtil.getImageUrl(JsonUtil.getString(user_info, "avatar")), teacherImg);
        payMoney= (TextView) findViewById(R.id.payMoney);
        payMoney.setText("￥"+orderDetailBean.getPrice());
        orderId= (TextView) findViewById(R.id.orderId);
        orderId.setText("订单编号： "+orderDetailBean.getOrder_number());
        createTime= (TextView) findViewById(R.id.createTime);
        createTime.setText("下单时间： "+orderDetailBean.getBuy_time());
        teacherName= (TextView) findViewById(R.id.teacherName);
        teacherName.setText(JsonUtil.getString(user_info, "user_name"));
        courseTitle= (TextView) findViewById(R.id.courseTitle);
        courseTitle.setText(orderDetailBean.getSubject_name()+"  -  "+orderDetailBean.getCourse_name());
        courseTitle.setOnClickListener(listener);
        courseNum= (TextView) findViewById(R.id.courseNum);
        priceTxt= (TextView) findViewById(R.id.priceTxt);
        priceTxt.setText("￥"+orderDetailBean.getOrder_price());
        payTxt= (TextView) findViewById(R.id.payTxt);
        payTxt.setText("￥"+orderDetailBean.getPrice());
        labelTxt= (TextView) findViewById(R.id.labelTxt);
        ActUtil.getCourseTypeTxt(orderDetailBean.getCourse_type(), labelTxt);

        courseLayout= (LinearLayout) findViewById(R.id.courseLayout);
        courseLayout.setOnClickListener(listener);
        LayoutInflater inflater=LayoutInflater.from(this);
        try {
            JSONArray array=new JSONObject(jsonData).getJSONArray("course_extm");
            courseNum.setText(array.length()+"节课");
            for(int i=0;i<array.length();i++){
                View v=inflater.inflate(R.layout.item_course_layout, null);
                TextView courseNum= (TextView) v.findViewById(R.id.courseNum);
                courseNum.setText((i+1)+"节课");
                courseLayout.addView(v);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findViewById(R.id.totalk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeanchatUser user = AVUser.newAVUser(LeanchatUser.class, null);
                String imgUrl=JsonUtil.getString(JsonUtil.getString(jsonData, "user_info"),"avatar");
                user.put("avatar", ImageUtil.getImageUrl(imgUrl));
                user.put("username", orderDetailBean.getUser_name());
                user.put("user_type ", "2");
                user.setObjectId(orderDetailBean.getUsercode());
                AVUserCacheUtils.cacheUser(user.getObjectId(), user);
                ActUtil.goChat(orderDetailBean.getUsercode(), UserOrderDetail.this, orderDetailBean.getUser_name());
            }
        });
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            if(orderDetailBean.getCourse_type().equals("3"))
                intent.setClass(UserOrderDetail.this, CourseMainPage.class);
            else
                intent.setClass(UserOrderDetail.this, VideoMainPage.class);
            intent.putExtra("course_name", orderDetailBean.getCourse_name());
            intent.putExtra("course_img", orderDetailBean.getImg());
            intent.putExtra("course_id", orderDetailBean.getCourse_id());
            startActivity(intent);
        }
    };
}
