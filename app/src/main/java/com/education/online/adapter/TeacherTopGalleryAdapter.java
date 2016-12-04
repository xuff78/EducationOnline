package com.education.online.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.education.online.R;
import com.education.online.act.CourseMainPage;
import com.education.online.act.VideoMainPage;
import com.education.online.act.teacher.AdvertPage;
import com.education.online.bean.AdvertsBean;
import com.education.online.bean.TeacherBean;
import com.education.online.fragment.teacher.TeacherInfoPage;
import com.education.online.util.ImageUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TeacherTopGalleryAdapter extends PagerAdapter {
//	private int defualtRes;
	DisplayImageOptions options;
    private List<TeacherBean> imgs;
	ImageLoader imageDownloader = ImageLoader.getInstance();
	Context con;

	public TeacherTopGalleryAdapter(Activity con,  List<TeacherBean> arrayList) {
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
    	final String url = imgs.get(position).getAvatar();
        imageDownloader.displayImage(ImageUtil.getImageUrl(url), img, options);
        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setClass(con, TeacherInfoPage.class);
               intent.putExtra("user_code",imgs.get(position).getUsercode());
                con.startActivity(intent);
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
