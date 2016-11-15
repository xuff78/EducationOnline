package com.education.online.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.util.ImageUtil;

/**
 * Created by Administrator on 2016/11/15.
 */
public class FooterViewHolder extends RecyclerView.ViewHolder {

    public TextView footerHint;
    public ProgressBar progressIcon;

    public FooterViewHolder(View v) {
        super(v);
        footerHint= (TextView) v.findViewById(R.id.footerHint);
        progressIcon= (ProgressBar) v.findViewById(R.id.progressIcon);
    }
}
