package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.OrderDetailBean;
import com.education.online.bean.OrderPayInfo;
import com.education.online.bean.TeacherOrderBean;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class OrderPayListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;
    private int imgLength=0;
    private LinearLayout.LayoutParams llp;
    private String loadingHint = "";
    private List<OrderPayInfo> datalist=new ArrayList<>();
    private ImageLoader loader;
    private DisplayImageOptions options;
    private View.OnClickListener listener;

    public void setLoadingHint(String hint) {
        loadingHint = hint;
        notifyDataSetChanged();
    }

    public OrderPayListAdapter(Activity activity, List<OrderPayInfo> datalist, ImageLoader loader,
                               View.OnClickListener listener){
        this.datalist=datalist;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        imgLength= (ScreenUtil.getWidth(activity)- ImageUtil.dip2px(activity, 70))/4; //左右边距40， 中间3*10
        llp=new LinearLayout.LayoutParams(imgLength, imgLength);
        llp.rightMargin=ImageUtil.dip2px(activity, 10);
        this.loader=loader;
        options=ImageUtil.getImageOption(-1);
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return datalist.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return getItemCount() - 1 > position ? position : -1;
    }

    @Override
    //创建一个视图
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        RecyclerView.ViewHolder vh =null;
        if (pos == -1) {
            View view = inflater.inflate(R.layout.footer_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(activity, 45)));
            vh = new FooterViewHolder(view);
        } else {
            View convertView = inflater.inflate(R.layout.order_payinfo_item, null);
            convertView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new OrderItem(convertView, pos);
        }
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder fvh = (FooterViewHolder) holder;
            fvh.footerHint.setText(loadingHint);
        }else{
            OrderItem ivh = (OrderItem) holder;
            OrderPayInfo msg=datalist.get(position);
            ivh.orderId.setText(msg.getOrder_number());
            ivh.orderStatus.setText(ActUtil.getOrderStatsTxts(msg.getState()));
            loader.displayImage(ImageUtil.getImageUrl(msg.getAvatar()), ivh.headIcon, options);
            ivh.courseNum.setText(ActUtil.getCourseTypeTxt(msg.getCourse_type())+"   "+msg.getCourse_count()+" 课时");
            ivh.orderTime.setText(msg.getBuy_time());
            ivh.totalPrice.setText("总价: "+ActUtil.twoDecimal(msg.getOriginal_price()));
            ivh.payPrice.setText(ActUtil.twoDecimal(msg.getPrice()));
            ivh.teacherName.setText(msg.getUser_name());
        }


    }

    public class OrderItem extends RecyclerView.ViewHolder{
        ImageView headIcon;
        TextView orderId, orderStatus, courseNum, orderTime, totalPrice, payPrice, teacherName;
        public OrderItem(View v, final int position)
        {
            super(v);
            headIcon = (ImageView) v.findViewById(R.id.teacherImg);
            orderId = (TextView) v.findViewById(R.id.orderId);
            orderStatus = (TextView) v.findViewById(R.id.orderStatus);
            courseNum = (TextView) v.findViewById(R.id.courseNum);
            orderTime = (TextView) v.findViewById(R.id.orderTime);
            totalPrice = (TextView) v.findViewById(R.id.totalPrice);
            teacherName = (TextView) v.findViewById(R.id.teacherName);
            payPrice = (TextView) v.findViewById(R.id.payPrice);
            View totalk=v.findViewById(R.id.totalk);
            totalk.setTag(position);
            headIcon.setTag(position);
            totalk.setOnClickListener(listener);
            headIcon.setOnClickListener(listener);
        }

    }
}
