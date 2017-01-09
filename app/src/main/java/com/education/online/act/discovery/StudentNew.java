package com.education.online.act.discovery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVUser;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.AVUserCacheUtils;
import com.education.online.R;
import com.education.online.bean.TeacherBean;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.DragHeaderLayout;
import com.education.online.view.QrcodeDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/9.
 */
public class StudentNew extends AppCompatActivity implements View.OnClickListener{

    private HttpHandler mHandler;
    private TeacherBean teacherInfo=new TeacherBean();
    private ImageLoader imageLoader;
    private String myUsercode;
    private ImageView starIcon;

    private DragHeaderLayout draglayout;
    private ScrollView mScrollView;
    private ViewPager mViewPager;
    private ImageView topbg;
    private RelativeLayout topLayout;
    private float rate=1;

    private void initHandler() {
        mHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                if(method.equals(Method.nearUser)){
                    teacherInfo = JSON.parseObject(jsonData, TeacherBean.class);
                    initView();
                }else if(method.equals(Method.getUserInfo)){

                }else if(method.equals(Method.addAttention)){
                    if(teacherInfo.getIs_attention().equals("0")) {
                        starIcon.setImageResource(R.mipmap.icon_star_red);
                        teacherInfo.setIs_attention("1");
                        ToastUtils.displayTextShort(StudentNew.this, "成功关注");
                    }else if(teacherInfo.getIs_attention().equals("1")) {
                        starIcon.setImageResource(R.mipmap.icon_star);
                        teacherInfo.setIs_attention("0");
                        ToastUtils.displayTextShort(StudentNew.this, "取消关注");
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_new);

        initHandler();
        myUsercode= SharedPreferencesUtil.getUsercode(this);
        imageLoader=ImageLoader.getInstance();
        if(getIntent().hasExtra("jsonData")) {
            teacherInfo = JSON.parseObject(getIntent().getStringExtra("jsonData"), TeacherBean.class);
            initView();
        }else
            mHandler.getUserInfo(getIntent().getStringExtra("usercode"));

    }

    private void initView() {
        topLayout = (RelativeLayout) findViewById(R.id.topLayout);
        topbg= (ImageView) findViewById(R.id.topbg);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        rate=ScreenUtil.getWidth(this)/Float.valueOf(ImageUtil.dip2px(this, 240));
        int imgheight=ImageUtil.dip2px(StudentNew.this, 180);
        mViewPager.setLayoutParams(new LinearLayout.LayoutParams((int)(rate*imgheight),ImageUtil.dip2px(this, 380)));
        mViewPager.setAdapter(new Adapter());

        mScrollView= (ScrollView) findViewById(R.id.mScrollView);
        draglayout= (DragHeaderLayout) findViewById(R.id.draglayout);
        int scrollOffset=ImageUtil.dip2px(this, 100);
        mScrollView.setAlpha(0);
        mScrollView.setTranslationY(scrollOffset);

        draglayout.setListLayoutInfo(mScrollView, scrollOffset, ImageUtil.dip2px(this, 440), ScreenUtil.getWidth(this),
                ImageUtil.dip2px(this, 240), new DragHeaderLayout.DragViewCallback() {
                    @Override
                    public void showTopImage(Bitmap bmp) {
                        if(bmp!=null)
                            topbg.setImageBitmap(bmp);
                        topLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void hideTopImage() {
                        topLayout.setVisibility(View.INVISIBLE);
                    }
                });
        
        starIcon= (ImageView) findViewById(R.id.starIcon);
        if(teacherInfo.getIs_attention().equals("0")) {
            starIcon.setImageResource(R.mipmap.icon_star);
        }else if(teacherInfo.getIs_attention().equals("1")) {
            starIcon.setImageResource(R.mipmap.icon_star_red);
        }
        if(myUsercode.equals(teacherInfo.getUsercode()))
            findViewById(R.id.bottomLayout).setVisibility(View.GONE);
        findViewById(R.id.myQrcode).setOnClickListener(this);
        findViewById(R.id.toChatBtn).setOnClickListener(this);
        findViewById(R.id.payattentionBtn).setOnClickListener(this);
        ImageView teacherImg= (ImageView) findViewById(R.id.teacherImg);
        imageLoader.displayImage(ImageUtil.getImageUrl(teacherInfo.getAvatar()), teacherImg);
        TextView nameTxt= (TextView) findViewById(R.id.nameTxt);
        findViewById(R.id.roundLeftBack).setOnClickListener(this);
        nameTxt.setText(teacherInfo.getName());
        TextView sexTxt= (TextView) findViewById(R.id.sexTxt);
        if(teacherInfo.getGender().equals("1")){
            sexTxt.setText("男");
        }else if(teacherInfo.getGender().equals("0")){
            sexTxt.setText("女");
        }
        TextView descTxt= (TextView) findViewById(R.id.descTxt);
        descTxt.setText(teacherInfo.getIntroduction());
        TextView attentionNum= (TextView) findViewById(R.id.attentionNum);
        attentionNum.setText(teacherInfo.getTo_attention_count());
        TextView fansNum= (TextView) findViewById(R.id.fansNum);
        fansNum.setText(teacherInfo.getAttention_count());
        TextView nickName= (TextView) findViewById(R.id.nickName);
        nickName.setText(teacherInfo.getName());
        TextView sexType= (TextView) findViewById(R.id.sexType);
        if(teacherInfo.getGender().equals("1")){
            sexType.setText("男");
        }else if(teacherInfo.getGender().equals("0")){
            sexType.setText("女");
        }
        TextView xingzuo= (TextView) findViewById(R.id.xingzuo);
        if(teacherInfo.getBirthday().length()>0&&teacherInfo.getBirthday().contains("-")) {
            String[] birthday = teacherInfo.getBirthday().split("-");
            xingzuo.setText(ActUtil.getConstellation(Integer.valueOf(birthday[1]), Integer.valueOf(birthday[2])));
        }
        TextView detailTxt= (TextView) findViewById(R.id.detailTxt);
        detailTxt.setText(teacherInfo.getIntroduction());
        TextView interestingCourse= (TextView) findViewById(R.id.interestingCourse);
        interestingCourse.setText(teacherInfo.getInterest_info());
        TextView askId= (TextView) findViewById(R.id.askId);
        askId.setText(teacherInfo.getUsercode());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toChatBtn:
                LeanchatUser user = AVUser.newAVUser(LeanchatUser.class, null);
                user.put("avatar", ImageUtil.getImageUrl(teacherInfo.getAvatar()));
                user.put("username", teacherInfo.getName());
                user.put("user_type ", teacherInfo.getUser_type());
                user.setObjectId(teacherInfo.getUsercode());
                AVUserCacheUtils.cacheUser(user.getObjectId(), user);
                ActUtil.goChat(teacherInfo.getUsercode(), StudentNew.this, teacherInfo.getName());
//                startActivity(new Intent(StudentNew.this, ChatPage.class));
                break;
            case R.id.myQrcode:
                new QrcodeDialog(StudentNew.this, teacherInfo.getUsercode()).show();
                break;
            case R.id.payattentionBtn:
                mHandler.addAttention(teacherInfo.getUsercode());
                break;
            case R.id.roundLeftBack:
                onBackPressed();
                break;
        }
    }

    class Adapter extends PagerAdapter {
        public Adapter() {
            views = new ArrayList<>();
            views.add(View.inflate(StudentNew.this, R.layout.draglayout, null));

        }

        private ArrayList<View> views;

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
