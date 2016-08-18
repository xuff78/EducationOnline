package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class HoriListAdapter extends RecyclerView.Adapter<ViewHolder>{
	
	Activity act;
//	ArrayList<CategoryBean> arrayList;
	ImageLoader imageDownloader = ImageLoader.getInstance();
	int height, width;
	int padding=0;
	
	public HoriListAdapter(Activity act){
		this.act=act;
//		this.arrayList=arrayList;
		width=ScreenUtil.getWidth(act)/2;
		padding= ImageUtil.dip2px(act, 20);
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return 2;
	}


	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		// TODO Auto-generated method stub
		CourseHolder vh=(CourseHolder) viewHolder;
//		CategoryBean item = arrayList.get(position);
//        imageDownloader.displayImage(ActUtil.getImageUrl(act, item.getLogo())+item.getLogo(),
//        		vh.img, ImageUtil.getImageOptions(act));
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int position) {
		View view = LayoutInflater.from(act).inflate(R.layout.video_items_vertical, null);
		RecyclerView.LayoutParams rlp=new RecyclerView.LayoutParams(width, -2);
		view.setLayoutParams(rlp);
		CourseHolder vh=new CourseHolder(view);
		if(position+1==6)
			view.setPadding(padding, 0, padding, 0);
		return vh;
	}
	
	public class CourseHolder extends RecyclerView.ViewHolder
    {
		TextView titleTxt, timeTxt, priceTxt, statusTxt;
		ImageView courseImg1;

		public CourseHolder(View convertView) {
			super(convertView);
			titleTxt = (TextView) convertView.findViewById(R.id.titleTxt);
			timeTxt = (TextView) convertView.findViewById(R.id.timeTxt);
			priceTxt = (TextView) convertView.findViewById(R.id.priceTxt);
			statusTxt = (TextView) convertView.findViewById(R.id.statusTxt);

			courseImg1 = (ImageView) convertView.findViewById(R.id.courseImg1);
		}

		View.OnClickListener listener=new View.OnClickListener(){
			@Override
			public void onClick(View view) {

			}
		};
    }

}
