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
import com.education.online.act.Mine.UserOrderDetail;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/10/4.
 */
public class MyFavorityAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private Activity activity;
    private LayoutInflater inflater;
    private int imgLength=0;
    private LinearLayout.LayoutParams llp;
    private ArrayList<Boolean> addlist=new ArrayList<>();

    public MyFavorityAdapter(Activity activity){
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        imgLength= (ScreenUtil.getWidth(activity)- ImageUtil.dip2px(activity, 70))/4; //左右边距40， 中间3*10
        llp=new LinearLayout.LayoutParams(imgLength, imgLength);
        llp.rightMargin=ImageUtil.dip2px(activity, 10);
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
        View view=inflater.inflate(R.layout.favorit_listitem, null);
        vh = new FavoritItem(view, pos);
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        FavoritItem favor = (FavoritItem) holder;
        if(addlist.get(position)){
            favor.checkBox.setImageResource(R.mipmap.icon_faver_selected);
        }else{
            favor.checkBox.setImageResource(R.mipmap.icon_faver_add);
        }

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class FavoritItem extends RecyclerView.ViewHolder
    {
        ImageView userIcon, checkBox;
        TextView nameTxt, typeTxt;
        public FavoritItem(View v, final int position)
        {
            super(v);
            userIcon = (ImageView) v.findViewById(R.id.userIcon);
            checkBox = (ImageView) v.findViewById(R.id.checkBox);
            nameTxt = (TextView) v.findViewById(R.id.nameTxt);
            typeTxt = (TextView) v.findViewById(R.id.typeTxt);
            addlist.add(true);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addlist.set(position, !addlist.get(position));
                    notifyDataSetChanged();
                }
            });
        }

    }
}
