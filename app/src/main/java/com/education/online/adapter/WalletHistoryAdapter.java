package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.WalletInfoBean;
import com.education.online.bean.WalletLogBean;
import com.education.online.util.ImageUtil;

import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class WalletHistoryAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;
    private List<WalletLogBean> walletLogBeanList;
    private String loadingHint = "";

    public WalletHistoryAdapter(Activity activity, List<WalletLogBean>  walletLogBeanList){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.walletLogBeanList = walletLogBeanList;


    }
    public void setLoadingHint(String hint) {
        loadingHint = hint;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return getItemCount()-1>position?position:-1;
    }

    @Override
    //创建一个视图
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        RecyclerView.ViewHolder vh =null;
        if(pos==-1){
            View view = inflater.inflate(R.layout.footer_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(activity, 45)));
            vh = new FooterViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.wallet_history_item, null);
            vh = new CourseItemHolder(view, pos);
        }
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder fvh = (FooterViewHolder) holder;
            fvh.footerHint.setText(loadingHint);
            if(getItemCount()==1)
                fvh.footerHint.setText("暂无数据");
        } else {
            CourseItemHolder fvh = (CourseItemHolder) holder;
            WalletLogBean walletLogBean = walletLogBeanList.get(position);
            fvh.balanceTxt.setText("￥"+walletLogBean.getBalance());
            fvh.costTxt.setText("￥"+walletLogBean.getAmount());
            fvh.dateTxt.setText(walletLogBean.getCreated_at());
            fvh.courseName.setText(walletLogBean.getContent());

        }
    }

    @Override
    public int getItemCount() {
        return walletLogBeanList.size()+1;
    }



    public class CourseItemHolder extends RecyclerView.ViewHolder
    {
        TextView courseName, balanceTxt, dateTxt, costTxt;
        public CourseItemHolder(View v, int position)
        {
            super(v);
            courseName = (TextView) v.findViewById(R.id.courseName);
            balanceTxt = (TextView) v.findViewById(R.id.balanceTxt);
            dateTxt = (TextView) v.findViewById(R.id.dateTxt);
            costTxt = (TextView) v.findViewById(R.id.costTxt);

        }


    }
}
