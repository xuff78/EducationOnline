package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.TransferHistory;
import com.education.online.bean.WalletLogBean;
import com.education.online.util.ImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class TransHistoryAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;
    private List<TransferHistory> walletLogBeanList;
    private String loadingHint = "";

    public TransHistoryAdapter(Activity activity, List<TransferHistory>  walletLogBeanList){
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
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new FooterViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.transfer_history_item, null);
            vh = new HistroyHolder(view, pos);
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
            HistroyHolder fvh = (HistroyHolder) holder;
            TransferHistory walletLogBean = walletLogBeanList.get(position);
            fvh.balanceTxt.setText("￥"+walletLogBean.getAmount());
            fvh.costTxt.setText(walletLogBean.getStatus());
            fvh.dateTxt.setText(walletLogBean.getCreate_at());
            fvh.courseName.setText("订单号： "+walletLogBean.getTransfer_sn());
            fvh.accountType.setText(walletLogBean.getPayment_name());
            if(walletLogBean.getApprover_info().length()>0)
                fvh.approver_info.setText(walletLogBean.getApprover_info());

        }
    }

    @Override
    public int getItemCount() {
        return walletLogBeanList.size()+1;
    }



    public class HistroyHolder extends RecyclerView.ViewHolder
    {
        TextView courseName, balanceTxt, dateTxt, costTxt, accountType, approver_info;
        public HistroyHolder(View v, int position)
        {
            super(v);
            courseName = (TextView) v.findViewById(R.id.courseName);
            balanceTxt = (TextView) v.findViewById(R.id.balanceTxt);
            dateTxt = (TextView) v.findViewById(R.id.dateTxt);
            costTxt = (TextView) v.findViewById(R.id.costTxt);
            accountType = (TextView) v.findViewById(R.id.accountType);
            approver_info = (TextView) v.findViewById(R.id.approver_info);

        }
    }
}
