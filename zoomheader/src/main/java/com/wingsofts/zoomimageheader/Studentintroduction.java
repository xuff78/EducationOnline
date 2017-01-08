package com.wingsofts.zoomimageheader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/9/27.
 */
public class Studentintroduction extends Activity implements View.OnClickListener{

    private TeacherBean teacherInfo=new TeacherBean();
    private DragHeaderLayout draglayout;
    private ScrollView mScrollView;
    private ViewPager mViewPager;
    private ImageView topbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_introduction);

        initView();
    }

    private void initView() {
        topbg= (ImageView) findViewById(R.id.topbg);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new Adapter());
        mScrollView= (ScrollView) findViewById(R.id.mScrollView);
        draglayout= (DragHeaderLayout) findViewById(R.id.draglayout);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        draglayout.setListLayoutInfo(mScrollView, dip2px(this, 440), dm.widthPixels, dip2px(this, 240), new DragHeaderLayout.DragViewCallback() {
            @Override
            public void showTopImage(Bitmap bmp) {
                topbg.setImageBitmap(bmp);
            }
        });
        findViewById(R.id.myQrcode).setOnClickListener(this);
        findViewById(R.id.toChatBtn).setOnClickListener(this);
        findViewById(R.id.payattentionBtn).setOnClickListener(this);
//        ImageView teacherImg= (ImageView) findViewById(R.id.teacherImg);
//        TextView nameTxt= (TextView) findViewById(R.id.nameTxt);
//        findViewById(R.id.roundLeftBack).setOnClickListener(this);
//        nameTxt.setText(teacherInfo.getName());
//        TextView sexTxt= (TextView) findViewById(R.id.sexTxt);
//        if(teacherInfo.getGender().equals("1")){
//            sexTxt.setText("男");
//        }else if(teacherInfo.getGender().equals("0")){
//            sexTxt.setText("女");
//        }
//        TextView descTxt= (TextView) findViewById(R.id.descTxt);
//        descTxt.setText(teacherInfo.getIntroduction());
//        TextView attentionNum= (TextView) findViewById(R.id.attentionNum);
//        attentionNum.setText(teacherInfo.getTo_attention_count());
//        TextView fansNum= (TextView) findViewById(R.id.fansNum);
//        fansNum.setText(teacherInfo.getAttention_count());
//        TextView nickName= (TextView) findViewById(R.id.nickName);
//        nickName.setText(teacherInfo.getName());
//        TextView sexType= (TextView) findViewById(R.id.sexType);
//        if(teacherInfo.getGender().equals("1")){
//            sexType.setText("男");
//        }else if(teacherInfo.getGender().equals("0")){
//            sexType.setText("女");
//        }
//        TextView xingzuo= (TextView) findViewById(R.id.xingzuo);
//        TextView detailTxt= (TextView) findViewById(R.id.detailTxt);
//        detailTxt.setText(teacherInfo.getIntroduction());
//        TextView interestingCourse= (TextView) findViewById(R.id.interestingCourse);
//        interestingCourse.setText(teacherInfo.getInterest_info());
//        TextView askId= (TextView) findViewById(R.id.askId);
//        askId.setText(teacherInfo.getUsercode());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toChatBtn:
                break;
            case R.id.myQrcode:
                break;
            case R.id.payattentionBtn:
                break;
            case R.id.roundLeftBack:
                onBackPressed();
                break;
        }
    }

    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    class Adapter extends PagerAdapter {
        public Adapter() {
            views = new ArrayList<>();
            views.add(View.inflate(Studentintroduction.this, R.layout.draglayout, null));

        }

        private ArrayList<View> views;
        private int[] imgs = { R.drawable.pizza, R.drawable.pic2, R.drawable.pic3 };

        @Override public int getCount() {
            return views.size();
        }

        @Override public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));

            return views.get(position);
        }

        @Override public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }
}
