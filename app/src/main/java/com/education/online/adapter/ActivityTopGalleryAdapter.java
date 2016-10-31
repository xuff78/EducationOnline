package com.education.online.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.education.online.R;
import com.education.online.bean.AdvertsBean;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ActivityTopGalleryAdapter extends PagerAdapter {
//	private int defualtRes;
	DisplayImageOptions options;
	private List<AdvertsBean> imgs=new ArrayList<>();
	ImageLoader imageDownloader = ImageLoader.getInstance();
	Context con;

	public ActivityTopGalleryAdapter(Activity con, List<AdvertsBean> arrayList) {
		this.imgs=arrayList;
		this.con=con;
		options = ImageUtil.getImageOption(0);

	}

    @Override
    public int getCount() {
    	return imgs.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
    	ImageView img = new ImageView(con);
    	img.setScaleType(ScaleType.CENTER_CROP);
        img.setBackgroundResource(R.color.whitesmoke);
    	final String url = imgs.get(position).getImg();
        imageDownloader.displayImage(ImageUtil.getImageUrl(url), img, options);
        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

			}
		});
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
