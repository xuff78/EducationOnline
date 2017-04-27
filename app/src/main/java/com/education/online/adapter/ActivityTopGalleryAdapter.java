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
import com.education.online.act.teacher.TeacherInformationPage;
import com.education.online.bean.AdvertsBean;
import com.education.online.fragment.teacher.TeacherInfoPage;
import com.education.online.util.ImageUtil;
import com.education.online.weex.AdvPageWeex;
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
                AdvertsBean advertsBean = imgs.get(position);
                String advert_type = advertsBean.getAdvert_type();
                Intent intent = new Intent();
                String details_count = advertsBean.getDetails_count();
                if(details_count.equals("1")) {
                    if (advert_type.equals("0")) {//课程
                        //          intent.setClass(con,)
                        if(advertsBean.getCourse_type().equals("live")){
                            intent.setClass(con, CourseMainPage.class);
                            intent.putExtra("course_id",advertsBean.getRelation_ids());
                         //   intent.putExtra("course_id")
                        }else {
                            intent.setClass(con, VideoMainPage.class);
                            intent.putExtra("course_id",advertsBean.getRelation_ids());
                           // intent.putExtra("",);
                        }
                        con.startActivity(intent);
                    } else if (advert_type.equals("1")) {
                        intent.setClass(con, TeacherInformationPage.class);
                        intent.putExtra("usercode",advertsBean.getRelation_ids());
                        con.startActivity(intent);

                    }
                }else if(Integer.parseInt(details_count)>1){
                    intent.putExtra("advert_id",advertsBean.getAdvert_id());
                    intent.setClass(con,AdvPageWeex.class);
//                    intent.setClass(con,AdvertPage.class);
                    con.startActivity(intent);


                }



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
