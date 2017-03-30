package com.education.online.act.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.avos.avoscloud.AVUser;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.AVUserCacheUtils;
import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.CourseMainPage;
import com.education.online.act.VideoMainPage;
import com.education.online.bean.CourseBean;
import com.education.online.bean.OrderDetailBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class UserOrderDetail extends BaseFrameAct {


    private ImageView teacherImg;
    private Button refundBtn;
    private TextView payMoney, orderId, createTime, teacherName, courseTitle, courseNum, priceTxt, payTxt, labelTxt,orderStatus, cashHint;
    private LinearLayout courseLayout;
    private OrderDetailBean orderDetailBean;
    private String jsonData="";
    private HttpHandler handler;

    private void initHandler() {
        handler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData){
                if(method.equals(Method.getOrderDetail)){
                    orderDetailBean= JSON.parseObject(jsonData, OrderDetailBean.class);
                    initView();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_detail);

        initHandler();
        jsonData=getIntent().getStringExtra("jsonData");
        orderDetailBean= JSON.parseObject(jsonData, OrderDetailBean.class);
        _setHeaderTitle("订单详情");
        initView();
    }

    private void initView() {
        String user_info=JsonUtil.getString(jsonData, "user_info");
        teacherImg= (ImageView) findViewById(R.id.teacherImg);
        refundBtn= (Button) findViewById(R.id.refundBtn);
        refundBtn.setOnClickListener(listener);
        if(orderDetailBean.getCourse_type().equals("3")) {
            String orderstate=orderDetailBean.getState();
            if(orderstate.equals("2"))
                refundBtn.setVisibility(View.VISIBLE);
            else
                refundBtn.setVisibility(View.GONE);
        }

        payMoney= (TextView) findViewById(R.id.payMoney);
        cashHint= (TextView) findViewById(R.id.cashHint);
        TextView refundTime= (TextView) findViewById(R.id.refundTime);
        TextView refundHint= (TextView) findViewById(R.id.refundHint);
        if(orderDetailBean.getState().equals("3")||orderDetailBean.getState().equals("4")) {
            refundHint.setVisibility(View.VISIBLE);
            refundHint.setText("退款状态：    "+ActUtil.getRefund(orderDetailBean.getRefund_state())+"   "
                    +orderDetailBean.getApprover_info());
            cashHint.setText("退款金额：");
            payMoney.setText("￥" + orderDetailBean.getRefund_price());
            refundTime.setText(orderDetailBean.getRefund_time());
        }else if(orderDetailBean.getState().equals("0")||orderDetailBean.getState().equals("1")){
            refundHint.setVisibility(View.GONE);
            cashHint.setText("待付金额：");
            payMoney.setText("￥" + orderDetailBean.getPrice());
        }else {
            refundHint.setVisibility(View.GONE);
            cashHint.setText("实付金额：");
            payMoney.setText("￥" + orderDetailBean.getOrder_price());
        }
        ImageLoader.getInstance().displayImage(ImageUtil.getImageUrl(JsonUtil.getString(user_info, "avatar")), teacherImg);
        orderStatus=(TextView) findViewById(R.id.orderStatus);
        orderStatus.setText(ActUtil.getOrderStatsTxts(orderDetailBean.getState()));
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
        priceTxt.setText("￥"+orderDetailBean.getPrice());
        payTxt= (TextView) findViewById(R.id.payTxt);
        payTxt.setText("￥"+orderDetailBean.getOrder_price());
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
            switch (view.getId()){
                case R.id.courseLayout:
                    if(orderDetailBean.getCourse_type().equals("3"))
                        intent.setClass(UserOrderDetail.this, CourseMainPage.class);
                    else
                        intent.setClass(UserOrderDetail.this, VideoMainPage.class);
                    intent.putExtra("course_name", orderDetailBean.getCourse_name());
                    intent.putExtra("course_img", orderDetailBean.getImg());
                    intent.putExtra("course_id", orderDetailBean.getCourse_id());
                    startActivity(intent);
                    break;
                case R.id.refundBtn:
                    intent.setClass(UserOrderDetail.this, RefundSubmit.class);
                    intent.putExtra("Order", orderDetailBean);
                    startActivityForResult(intent, 0x11);
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x12){
            handler.getOrderDetail(orderDetailBean.getOrder_number());
        }
    }
}
