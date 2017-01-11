package com.education.online.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.education.online.R;
import com.education.online.act.CourseMainPage;
import com.education.online.act.MainPage;
import com.education.online.act.SearchResultAct;
import com.education.online.act.VideoMainPage;
import com.education.online.act.video.LiveTelecast;
import com.education.online.act.video.VideoMain;
import com.education.online.bean.AdvertsBean;
import com.education.online.bean.CourseBean;
import com.education.online.bean.HomePageInfo;
import com.education.online.bean.LiveCourse;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.VideoCourse;
import com.education.online.bean.WareCourse;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ScreenUtil;
import com.education.online.view.ExtendedViewPager;
import com.education.online.view.MenuAnimationController;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/8/15.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity act;
    private LayoutInflater listInflater;
    private ImageLoader imageLoader;
    private int itemWidth = 0, itemHeight = 0, imgHeight = 0, courseWidth=0;
    private int padding10 = 0;
    private boolean animaShown = false;
    private HomePageInfo info;
    private ArrayList<CourseBean> courses=new ArrayList<>();
    private String loadingHint = "";

    public void setLoadingHint(String hint) {
        loadingHint = hint;
        notifyDataSetChanged();
    }

    public MainAdapter(Activity act, HomePageInfo info, ArrayList<CourseBean> courses) {
        this.act = act;
        this.info = info;
        imageLoader = ImageLoader.getInstance();
        listInflater = LayoutInflater.from(act);
        padding10 = ImageUtil.dip2px(act, 10);
        itemWidth = (ScreenUtil.getWidth(act) - 2 * padding10) / 5; //小图片和文字的形成点击区域的边长
        imgHeight = itemWidth - 2 * padding10; //小图片的边长
        courseWidth = (ScreenUtil.getWidth(act) - 5 * padding10) / 2;
        this.courses=courses;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return 6+courses.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return getItemCount() - 1 > position ? position : -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int pos) {
        if (vh instanceof FooterViewHolder) {
            FooterViewHolder fvh = (FooterViewHolder) vh;
            fvh.footerHint.setText(loadingHint);
            if(courses.size()==0)
            {
                fvh.footerHint.setText("");
            }
        }else if (pos == 0) {
            PagerHolder ivh = (PagerHolder) vh;
        } else if (pos == 1) {
            SubjectHolder ivh = (SubjectHolder) vh;
       /* } else if (pos == 2) {
            CourseHolder ivh = (CourseHolder) vh;
            ivh.subjectName.setText("直播");
            List<LiveCourse> liveCourses = info.getLive();
            if (liveCourses.size() > 0) {
                LiveCourse live = liveCourses.get(0);
                imageLoader.displayImage(ImageUtil.getImageUrl(live.getCourse_img()), ivh.courseImg1);
                ivh.titleTxt.setText(live.getCourse_name());
                ivh.priceTxt.setText(ActUtil.getPrice(live.getPrice()));
                ivh.timeTxt.setText(live.getCourseware_date());
                ivh.statusTxt.setText(live.getFollow() + "人在学习");
            } else
                ivh.item1.setVisibility(View.INVISIBLE);
            if (liveCourses.size() > 1) {
                LiveCourse live = liveCourses.get(1);
                imageLoader.displayImage(ImageUtil.getImageUrl(live.getCourse_img()), ivh.courseImg2);
                ivh.titleTxt2.setText(live.getCourse_name());
                ivh.priceTxt2.setText(ActUtil.getPrice(live.getPrice()));
                ivh.timeTxt2.setText(live.getCourseware_date());
                ivh.statusTxt2.setText(live.getFollow() + "人在学习");
            } else
                ivh.item2.setVisibility(View.INVISIBLE);
        } else if (pos == 3) {
            CourseHolder ivh = (CourseHolder) vh;
            ivh.subjectName.setText("视频");
            List<VideoCourse> liveCourses = info.getVideo();
            if (liveCourses.size() > 0) {
                VideoCourse live = liveCourses.get(0);
                imageLoader.displayImage(ImageUtil.getImageUrl(live.getCourse_img()), ivh.courseImg1);
                ivh.titleTxt.setText(live.getCourse_name());
                ivh.priceTxt.setText(ActUtil.getPrice(live.getPrice()));
                ivh.timeTxt.setVisibility(View.GONE);
                ivh.statusTxt.setText(live.getFollow() + "人在学习");
            } else
                ivh.item1.setVisibility(View.INVISIBLE);
            if (liveCourses.size() > 1) {
                VideoCourse live = liveCourses.get(1);
                imageLoader.displayImage(ImageUtil.getImageUrl(live.getCourse_img()), ivh.courseImg2);
                ivh.titleTxt2.setText(live.getCourse_name());
                ivh.priceTxt2.setText(ActUtil.getPrice(live.getPrice()));
                ivh.timeTxt2.setVisibility(View.GONE);
                ivh.statusTxt2.setText(live.getFollow() + "人在学习");
            } else
                ivh.item2.setVisibility(View.INVISIBLE);
        } else if (pos == 4) {
            CourseHolder ivh = (CourseHolder) vh;
            ivh.subjectName.setText("课件");
            List<WareCourse> liveCourses = info.getWare();
            if (liveCourses.size() > 0) {
                WareCourse live = liveCourses.get(0);
                imageLoader.displayImage(ImageUtil.getImageUrl(live.getCourse_img()), ivh.courseImg1);
                if(live.getCourse_img().length()==0)
                    ivh.courseImg1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                ivh.titleTxt.setText(live.getCourse_name());
                ivh.priceTxt.setText(ActUtil.getPrice(live.getPrice()));
                ivh.timeTxt.setVisibility(View.GONE);
                ivh.statusTxt.setText(live.getFollow() + "人在学习");
                ivh.imgMask1.setVisibility(View.GONE);
            } else
                ivh.item1.setVisibility(View.INVISIBLE);
            if (liveCourses.size() > 1) {
                WareCourse live = liveCourses.get(1);
                imageLoader.displayImage(ImageUtil.getImageUrl(live.getCourse_img()), ivh.courseImg2);
                if(live.getCourse_img().length()==0)
                    ivh.courseImg2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                ivh.titleTxt2.setText(live.getCourse_name());
                ivh.priceTxt2.setText(ActUtil.getPrice(live.getPrice()));
                ivh.timeTxt2.setVisibility(View.GONE);
                ivh.statusTxt2.setText(live.getFollow() + "人在学习");
                ivh.imgMask2.setVisibility(View.GONE);
            } else
                ivh.item2.setVisibility(View.INVISIBLE);*/
        } else if (pos > 5) {
            CourseItemHolder cvh = (CourseItemHolder) vh;
            CourseBean courseBean=courses.get(pos-6);
            ActUtil.getCourseTypeTxt(courseBean.getCourse_type(), cvh.typeTxt);
            cvh.titleTxt.setText(courseBean.getCourse_name());
            cvh.statusTxt.setText(courseBean.getFollow()+"人正在学习");
            cvh.priceTxt.setText(ActUtil.getPrice(courseBean.getPrice()));
            imageLoader.displayImage(ImageUtil.getImageUrl(courseBean.getImg()), cvh.courseImg);
            cvh.itemView.setTag(courseBean);
            cvh.itemView.setOnClickListener(listener);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int pos) {
        RecyclerView.ViewHolder vh = null;
        if (pos == 0) {
            View convertView = listInflater.inflate(R.layout.viewpager_layout, null);
            int height = (int) ((float) ScreenUtil.getWidth(act) / 640 * 300);  //240
            RecyclerView.LayoutParams alp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            convertView.setLayoutParams(alp);
            vh = new PagerHolder(convertView);
        } else if (pos == 1) {
            LinearLayout view = new LinearLayout(act);
            view.setOrientation(LinearLayout.VERTICAL);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new SubjectHolder(view, pos);
        } else if (pos == 2 || pos == 3 || pos == 4) {
            View convertView = listInflater.inflate(R.layout.home_course_layout, null);
            vh = new CourseLayoutHolder(convertView, pos);
        } else if (pos == 5) {
            View convertView = listInflater.inflate(R.layout.home_title_bar, null);
            convertView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new TitleHolder(convertView);
        } else if (pos > 5) {
            View convertView = listInflater.inflate(R.layout.course_item_all, null);
            convertView.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            vh = new CourseItemHolder(convertView);
        }else if (pos == -1) {
            View view = listInflater.inflate(R.layout.footer_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(act, 45)));
            vh = new FooterViewHolder(view);
        }
        return vh;
    }

    public class PagerHolder extends RecyclerView.ViewHolder {
        ExtendedViewPager mViewPager;

        public PagerHolder(View convertView) {
            super(convertView);
            mViewPager = (ExtendedViewPager) convertView.findViewById(R.id.galleryImgs);
            mViewPager.setAdapter(new ActivityTopGalleryAdapter(act, info.getAdverts_info()));
            mViewPager.startAutoScroll();
        }
    }

    public class SubjectHolder extends RecyclerView.ViewHolder {
        LinearLayout itemsLayout;

        public SubjectHolder(View v, int position) {
            super(v);
            itemsLayout = (LinearLayout) v;
            itemsLayout.setPadding(padding10, 0, padding10, padding10 * 2);
            LinearLayout.LayoutParams llpitem = new LinearLayout.LayoutParams(itemWidth, -2);
            LinearLayout linelayout = new LinearLayout(act);
            linelayout.setOrientation(LinearLayout.HORIZONTAL);
            int itemsize = info.getSubject_list().size();
            for (int i = 0; i < itemsize + 1; i++) {
                if (i == itemsize)
                    linelayout.addView(getSubjectItemView(null), llpitem);
                else {
                    SubjectBean subject = info.getSubject_list().get(i);
                    linelayout.addView(getSubjectItemView(subject), llpitem);
                }
                if (i % 5 == 4 || i == itemsize) {
                    itemsLayout.addView(linelayout);
                    setAnima(linelayout, i);
                    linelayout = new LinearLayout(act);
                }
            }
            animaShown = true;
        }

        private void setAnima(LinearLayout ll, int i) {
            if (!animaShown) {
                AnimationSet animation = new AnimationSet(false);
                animation.addAnimation(AnimationUtils.loadAnimation(act, android.R.anim.fade_in));
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1f, 0.0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(400);
                animation.addAnimation(scaleAnimation);
                animation.setInterpolator(act, android.R.anim.decelerate_interpolator);
//                animation.addAnimation(AnimationUtils.loadAnimation(act, android.R.anim.slide_in_left));
                int offset=((i+1)/5-1)*150;
                animation.setStartOffset(offset);
                LayoutAnimationController lac = new LayoutAnimationController(animation);
                lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
                lac.setDelay(0.2f);//注意这个地方是以秒为单位，是浮点型数据，所以要加f
                ll.setLayoutAnimation(lac);
                ll.startLayoutAnimation();
            }
        }

        private LinearLayout getSubjectItemView(SubjectBean subject) {
            LinearLayout.LayoutParams llpimg = new LinearLayout.LayoutParams(imgHeight, imgHeight);
            llpimg.bottomMargin = 5;
            LinearLayout layout = new LinearLayout(act);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(padding10, padding10 * 2, padding10, 0);
            ImageView img = new ImageView(act);
            img.setBackgroundResource(R.color.whitesmoke);
            layout.addView(img, llpimg);
            TextView txt = new TextView(act);
            txt.setTextSize(12);
            txt.setTextColor(Color.GRAY);
            txt.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(txt);
            if (subject != null)
                layout.setTag(subject);
            if (subject != null) {
                txt.setText(subject.getSubject_name());
                imageLoader.displayImage(ImageUtil.getImageUrl(subject.getSubject_img()), img);
            } else {
                img.setImageResource(R.mipmap.icon_menu_more);
                txt.setText("更多");
            }
            layout.setOnClickListener(listener);
            return layout;
        }

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Object object = view.getTag();
                if (object != null) {
                    SubjectBean subject = (SubjectBean) object;
                    Intent i = new Intent(act, SearchResultAct.class);
                    String ids=subject.getChild_subject_ids();
                    if(ids.length()==0)
                        ids="-1";
                    i.putExtra(Constant.SearchSubject, ids);
                    i.putExtra(Constant.SearchCate, subject.getSubject_id());
                    i.putExtra(Constant.SearchWords, subject.getSubject_name());
                    act.startActivity(i);
                }else{
                    ((MainPage)act).toSubjectList();
                }
            }
        };
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CourseBean course= (CourseBean) view.getTag();
            Intent intent=new Intent();
            if(course.getCourse_type().equals("3"))
                intent.setClass(act, CourseMainPage.class);
            else
                intent.setClass(act, VideoMainPage.class);
            intent.putExtra("course_name", course.getCourse_name());
            intent.putExtra("course_img", course.getImg());
            intent.putExtra("course_id", course.getCourse_id());
            act.startActivity(intent);
        }
    };


    public class CourseLayoutHolder extends RecyclerView.ViewHolder {

        LinearLayout itemlayout1, itemlayout2;
        TextView subjectName;
        int pos = 0;

        public CourseLayoutHolder(View v, int pos) {
            super(v);
            this.pos=pos;
            itemlayout1=(LinearLayout)v.findViewById(R.id.itemlayout1);
            itemlayout2=(LinearLayout)v.findViewById(R.id.itemlayout2);
            subjectName= (TextView) v.findViewById(R.id.subjectName);
            v.findViewById(R.id.mornBtn).setOnClickListener(listener);
            LinearLayout addLayout=itemlayout1;
            if (pos == 2) {
                subjectName.setText("直播");
                List<LiveCourse> liveCourses = info.getLive();
                for(int i=0;i<liveCourses.size();i++){
                    if(i>1)
                        addLayout=itemlayout2;
                    CourseViewHolder ivh=getCourseHolder(addLayout, i);
                    LiveCourse live = liveCourses.get(i);
                    imageLoader.displayImage(ImageUtil.getImageUrl(live.getCourse_img()), ivh.courseImg1);
                    ivh.titleTxt.setText(live.getCourse_name());
                    ivh.priceTxt.setText(ActUtil.getPrice(live.getPrice()));
                    ivh.timeTxt.setText(live.getCourseware_date());
                    ivh.statusTxt.setText(live.getFollow() + "人在学习");
                }
            } if (pos == 3) {
                subjectName.setText("视频");
                List<VideoCourse> liveCourses = info.getVideo();
                for(int i=0;i<liveCourses.size();i++){
                    if(i>1)
                        addLayout=itemlayout2;
                    CourseViewHolder ivh=getCourseHolder(addLayout, i);
                    VideoCourse live = liveCourses.get(i);
                    imageLoader.displayImage(ImageUtil.getImageUrl(live.getCourse_img()), ivh.courseImg1);
                    ivh.titleTxt.setText(live.getCourse_name());
                    ivh.priceTxt.setText(ActUtil.getPrice(live.getPrice()));
                    ivh.timeTxt.setVisibility(View.GONE);
                    ivh.statusTxt.setText(live.getFollow() + "人在学习");
                }
            } if (pos == 4) {
                subjectName.setText("课件");
                List<WareCourse> liveCourses = info.getWare();
                for(int i=0;i<liveCourses.size();i++){
                    if(i>1)
                        addLayout=itemlayout2;
                    CourseViewHolder ivh=getCourseHolder(addLayout, i);
                    WareCourse live = liveCourses.get(0);
                    imageLoader.displayImage(ImageUtil.getImageUrl(live.getCourse_img()), ivh.courseImg1);
                    if(live.getCourse_img().length()==0)
                        ivh.courseImg1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    ivh.titleTxt.setText(live.getCourse_name());
                    ivh.priceTxt.setText(ActUtil.getPrice(live.getPrice()));
                    ivh.timeTxt.setVisibility(View.GONE);
                    ivh.statusTxt.setText(live.getFollow() + "人在学习");
                    ivh.imgMask1.setVisibility(View.GONE);
                }
            }
        }

        public class CourseViewHolder{
            public TextView titleTxt, timeTxt, priceTxt, statusTxt;
            public ImageView courseImg1, imgMask1;
        }

        public CourseViewHolder getCourseHolder(LinearLayout linearLayout, int pos){
            CourseViewHolder holder=new CourseViewHolder();
            View v=listInflater.inflate(R.layout.home_course_item, null);
            holder.titleTxt = (TextView) v.findViewById(R.id.titleTxt);
            holder.timeTxt = (TextView) v.findViewById(R.id.timeTxt);
            holder.priceTxt = (TextView) v.findViewById(R.id.priceTxt);
            holder.statusTxt = (TextView) v.findViewById(R.id.statusTxt);
            holder.courseImg1 = (ImageView) v.findViewById(R.id.courseImg1);
            holder.imgMask1 = (ImageView) v.findViewById(R.id.imgMask1);
            v.setOnClickListener(listener);
            v.setTag(pos);
            LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(courseWidth, -2);
            llp.rightMargin=padding10;
            linearLayout.addView(v, llp);
            return holder;
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.mornBtn:
                        if (pos == 2)
                            act.startActivity(new Intent(act, LiveTelecast.class));
                        else if (pos == 3) {
                            Intent i = new Intent(act, SearchResultAct.class);
                            i.putExtra(Constant.TypeCourse, "coursevideo");
                            act.startActivity(i);
                        }else if (pos == 4){
                            Intent i = new Intent(act, SearchResultAct.class);
                            i.putExtra(Constant.TypeCourse, "courseware");
                            act.startActivity(i);
                        }
                        break;
                    case R.id.item1:
                    case R.id.item2:
                        Intent intent = new Intent();
                        int tag= (int) view.getTag();
                        if (pos == 2) {
                            LiveCourse course=info.getLive().get(tag);
                            intent.putExtra("course_name", course.getCourse_name());
                            intent.putExtra("course_img", course.getCourse_img());
                            intent.putExtra("course_id", course.getCourse_id());
                            intent.setClass(act, CourseMainPage.class);
                            act.startActivity(intent);
                        } else if (pos == 3) {
                            VideoCourse course=info.getVideo().get(tag);
                            intent.putExtra("course_name", course.getCourse_name());
                            intent.putExtra("course_img", course.getCourse_img());
                            intent.putExtra("course_id", course.getCourse_id());
                            intent.setClass(act, VideoMainPage.class);
                            act.startActivity(intent);
                        } else if (pos == 4) {
                            WareCourse course=info.getWare().get(tag);
                            intent.putExtra("course_name", course.getCourse_name());
                            intent.putExtra("course_img", course.getCourse_img());
                            intent.putExtra("course_id", course.getCourse_id());
                            intent.setClass(act, VideoMainPage.class);
                            act.startActivity(intent);
                        }
                        break;
                }
            }
        };
    }

   /* public class CourseHolder extends RecyclerView.ViewHolder {
        TextView subjectName, titleTxt, timeTxt, priceTxt, statusTxt, titleTxt2, timeTxt2, priceTxt2, statusTxt2;
        ImageView courseImg1, courseImg2, imgMask1, imgMask2;
        View item1, item2;
        View moreBtn;
        int pos = 0;

        public CourseHolder(View convertView, int pos) {
            super(convertView);
            this.pos = pos;
            subjectName = (TextView) convertView.findViewById(R.id.subjectName);
            titleTxt = (TextView) convertView.findViewById(R.id.titleTxt);
            timeTxt = (TextView) convertView.findViewById(R.id.timeTxt);
            priceTxt = (TextView) convertView.findViewById(R.id.priceTxt);
            priceTxt = (TextView) convertView.findViewById(R.id.priceTxt);
            statusTxt = (TextView) convertView.findViewById(R.id.statusTxt);
            titleTxt2 = (TextView) convertView.findViewById(R.id.titleTxt2);
            timeTxt2 = (TextView) convertView.findViewById(R.id.timeTxt2);
            priceTxt2 = (TextView) convertView.findViewById(R.id.priceTxt2);
            statusTxt2 = (TextView) convertView.findViewById(R.id.statusTxt2);
            courseImg1 = (ImageView) convertView.findViewById(R.id.courseImg1);
            courseImg2 = (ImageView) convertView.findViewById(R.id.courseImg2);
            imgMask1 = (ImageView) convertView.findViewById(R.id.imgMask1);
            imgMask2 = (ImageView) convertView.findViewById(R.id.imgMask2);

            item1 = convertView.findViewById(R.id.item1);
            item2 = convertView.findViewById(R.id.item2);

            item1.setOnClickListener(listener);
            item2.setOnClickListener(listener);
            moreBtn = convertView.findViewById(R.id.mornBtn);
            moreBtn.setOnClickListener(listener);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.mornBtn:
                        if (pos == 2)
                            act.startActivity(new Intent(act, LiveTelecast.class));
                        else if (pos == 3)
                            act.startActivity(new Intent(act, VideoMain.class));
                        else if (pos == 4)
                            act.startActivity(new Intent(act, VideoMain.class));
                        break;
                    case R.id.item1:
                    case R.id.item2:
                        Intent intent = new Intent();
                        if (pos == 2) {
                            LiveCourse course=new LiveCourse();
                            if(view.getId()==R.id.item1)
                                course=info.getLive().get(0);
                            else if(view.getId()==R.id.item2)
                                course=info.getLive().get(1);
                            intent.putExtra("course_name", course.getCourse_name());
                            intent.putExtra("course_img", course.getCourse_img());
                            intent.putExtra("course_id", course.getCourse_id());
                            intent.setClass(act, CourseMainPage.class);
                            act.startActivity(intent);
                        } else if (pos == 3) {
                            VideoCourse course=new VideoCourse();
                            if(view.getId()==R.id.item1)
                                course=info.getVideo().get(0);
                            else if(view.getId()==R.id.item2)
                                course=info.getVideo().get(1);
                            intent.putExtra("course_name", course.getCourse_name());
                            intent.putExtra("course_img", course.getCourse_img());
                            intent.putExtra("course_id", course.getCourse_id());
                            intent.setClass(act, VideoMainPage.class);
                            act.startActivity(intent);
                        } else if (pos == 4) {
                            WareCourse course=new WareCourse();
                            if(view.getId()==R.id.item1)
                                course=info.getWare().get(0);
                            else if(view.getId()==R.id.item2)
                                course=info.getWare().get(1);
                            intent.putExtra("course_name", course.getCourse_name());
                            intent.putExtra("course_img", course.getCourse_img());
                            intent.putExtra("course_id", course.getCourse_id());
                            intent.setClass(act, VideoMainPage.class);
                            act.startActivity(intent);
                        }
                        break;
                }
            }
        };
    }*/

public class TitleHolder extends RecyclerView.ViewHolder {
    TextView forYouTitle;

    public TitleHolder(View convertView) {
        super(convertView);
        forYouTitle = (TextView) convertView.findViewById(R.id.forYouTitle);
    }
}

public class CourseItemHolder extends RecyclerView.ViewHolder {
    TextView titleTxt, statusTxt, typeTxt, priceTxt;
    ImageView courseImg;

    public CourseItemHolder(View convertView) {
        super(convertView);
        courseImg = (ImageView) convertView.findViewById(R.id.courseImg);
        titleTxt = (TextView) convertView.findViewById(R.id.titleTxt);
        statusTxt = (TextView) convertView.findViewById(R.id.statusTxt);
        typeTxt = (TextView) convertView.findViewById(R.id.typeTxt);
        priceTxt = (TextView) convertView.findViewById(R.id.priceTxt);
    }
}
}
