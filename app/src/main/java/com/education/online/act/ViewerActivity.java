package com.education.online.act;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.transition.ChangeImageTransform;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.education.online.R;
import com.education.online.util.ImageUtil;
import com.education.online.util.StatusBarCompat;
import com.education.online.view.ExtendedViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class ViewerActivity extends Activity {
	
	/**
	 * Step 1: Download and set up v4 support library: http://developer.android.com/tools/support-library/setup.html
	 * Step 2: Create ExtendedViewPager wrapper which calls TouchImageView.canScrollHorizontallyFroyo
	 * Step 3: ExtendedViewPager is a custom view and must be referred to by its full package name in XML
	 * Step 4: Write TouchImageAdapter, located below
	 * Step 5. The ViewPager in the XML should be ExtendedViewPager
	 */

    private ArrayList<String> imgs=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.fitPage(this);
        ImageUtil.initImageLoader(this);
        int pos=getIntent().getIntExtra("pos", 0);
        imgs=getIntent().getStringArrayListExtra("Images");
        setContentView(R.layout.activity_viewpager_example);
        ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter());
        mViewPager.setCurrentItem(pos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageUtil.initImageLoader(this);
    }

    class TouchImageAdapter extends PagerAdapter {

        ImageLoader loader=ImageLoader.getInstance();


        @Override
        public int getCount() {
        	return imgs.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final TouchImageView img = new TouchImageView(container.getContext());
            String url=imgs.get(position);
            if(!url.startsWith("http"))
                url=ImageUtil.getImageUrl(imgs.get(position));
            loader.loadImage(url, ImageUtil.getImageOption(0),
                    new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    img.setImageBitmap(loadedImage);
                }
            });
            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
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
}
