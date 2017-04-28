package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.Mine.MyOrderUser;
import com.education.online.act.Mine.UserOrderDetail;
import com.education.online.act.order.OrderPay;
import com.education.online.act.video.Comment;
import com.education.online.bean.OrderDetailBean;
import com.education.online.inter.SimpleAdapterCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/9/17.
 */
public class UserOrderAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;
    private int imgLength=0;
    private LinearLayout.LayoutParams llp;
    private SimpleAdapterCallback cb;
    private List<OrderDetailBean> orders=new ArrayList<>();
    private ImageLoader imageLoader=ImageLoader.getInstance();

    public UserOrderAdapter(Activity activity, List<OrderDetailBean> orders, SimpleAdapterCallback cb){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        imgLength= (ScreenUtil.getWidth(activity)- ImageUtil.dip2px(activity, 70))/4; //左右边距40， 中间3*10
        llp=new LinearLayout.LayoutParams(imgLength, imgLength);
        llp.rightMargin=ImageUtil.dip2px(activity, 10);
        this.orders=orders;
        this.cb=cb;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    //创建一个视图
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        RecyclerView.ViewHolder vh =null;
        View view=inflater.inflate(R.layout.user_order_item, null);
        vh = new CourseItemHolder(view, pos);
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CourseItemHolder courseHolder= (CourseItemHolder) holder;
        OrderDetailBean bean=orders.get(position);
        imageLoader.displayImage(ImageUtil.getImageUrl(bean.getImg()), courseHolder.CourseImage);
        courseHolder.CourseName.setText(bean.getCourse_name());
        courseHolder.teacherName.setText(bean.getUser_name());
        courseHolder.typeName.setText(bean.getSubject_name());
        ActUtil.getCourseTypeTxt(bean.getCourse_type(), courseHolder.CoursePrice);
        courseHolder.CoursePrice.setText(courseHolder.CoursePrice.getText().toString()+"   "+ActUtil.getPrice(bean.getPrice()));
        courseHolder.statusTxt.setText(ActUtil.getOrderStatsTxts(bean.getState()));
        if(bean.getState().equals("2")){
            if(bean.getIs_evaluate().equals("1"))
                courseHolder.addEvaluate.setVisibility(View.VISIBLE);
        }else if(bean.getState().equals("1")){
            courseHolder.addEvaluate.setVisibility(View.VISIBLE);
            courseHolder.addEvaluate.setText("去支付");
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class CourseItemHolder extends RecyclerView.ViewHolder
    {
        ImageView CourseImage;
        TextView teacherName, typeName, CoursePrice, CourseName, addEvaluate, statusTxt;
        LinearLayout imgsLayout;
        public CourseItemHolder(final View v, final int position)
        {
            super(v);
            final OrderDetailBean bean=orders.get(position);
            CourseImage = (ImageView) v.findViewById(R.id.CourseImage);
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            typeName = (TextView) v.findViewById(R.id.typeName);
            CoursePrice = (TextView) v.findViewById(R.id.CoursePrice);
            CourseName = (TextView) v.findViewById(R.id.CourseName);
            addEvaluate = (TextView) v.findViewById(R.id.addEvaluate);
            statusTxt = (TextView) v.findViewById(R.id.statusTxt);
            addEvaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bean.getState().equals("2")) {
                        Intent intent=new Intent(activity, Comment.class);
                        intent.putExtra("courseImg", bean.getImg());
                        intent.putExtra("courseName", bean.getCourse_name());
                        intent.putExtra("courseIntroduction", bean.getIntroduction());
                        intent.putExtra("course_id", bean.getCourse_id());
                        activity.startActivity(intent);
                    }else if(bean.getState().equals("1")){
                        Intent intent = new Intent(activity, OrderPay.class);
                        intent.putExtra("Order", bean);
                        activity.startActivity(intent);
                    }
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cb!=null)
                        cb.onClick(v, position);
                }
            });
        }

    }
}
