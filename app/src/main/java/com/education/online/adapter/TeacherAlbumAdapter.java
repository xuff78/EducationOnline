package com.education.online.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.ExtendedViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/30.
 */
public class TeacherAlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private int itemWidth = 0, itemHeight = 0, imgHeight = 0;
    private int padding10 = 0;


    public TeacherAlbumAdapter(Activity act, String jason) {
        this.activity = act;
        inflater = LayoutInflater.from(activity);
        padding10 = ImageUtil.dip2px(act, 5);
        itemWidth = (ScreenUtil.getWidth(act) - 2 * padding10) / 4; //小图片和文字的形成点击区域的边长
        imgHeight = itemWidth - 2 * padding10; //小图片的边长
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        LinearLayout view = new LinearLayout(activity);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        vh = new TeacherAlbumAdapter.AlbumHolder(view, viewType);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AlbumHolder vh = (AlbumHolder) holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {

        LinearLayout itemsLayout;


        public AlbumHolder(View itemView, int pos) {
            super(itemView);
            itemsLayout = (LinearLayout) itemView;
            itemsLayout.setPadding(padding10, 0, padding10, padding10 * 2);
            LinearLayout.LayoutParams llpitem = new LinearLayout.LayoutParams(itemWidth, -2);
            int itemsize = 15;
            LinearLayout linelayout = new LinearLayout(activity);
            linelayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int i = 0; i < itemsize + 1; i++) {
                    linelayout.addView(getSubjectItemView( ""), llpitem);
                if (i % 4 == 3 || i == itemsize) {
                    itemsLayout.addView(linelayout);
                    linelayout = new LinearLayout(activity);

                }
            }
        }
///这部分代码我不是很懂，暂且贴上，回头看一下
        private LinearLayout getSubjectItemView( String imgUrl) {
            LinearLayout.LayoutParams llpimg = new LinearLayout.LayoutParams(imgHeight, imgHeight);
            llpimg.bottomMargin = 5;
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(padding10, padding10 * 2, padding10, 0);
            ImageView img = new ImageView(activity);
            img.setBackgroundResource(R.color.whitesmoke);
            layout.addView(img, llpimg);
           // TextView txt = new TextView(activity);
           // txt.setTextSize(12);
            //txt.setTextColor(Color.GRAY);
            //txt.setText(name);
            //txt.setGravity(Gravity.CENTER_HORIZONTAL);
           // layout.addView(txt);
            return layout;
        }
    }
}


