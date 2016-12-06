//package com.education.online.fragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.education.online.R;
//import com.education.online.adapter.CommentsAdapter;
//import com.education.online.adapter.CourseAdapter;
//import com.education.online.adapter.DetailsAdapter;
//import com.education.online.adapter.DirectoryAdapter;
//import com.education.online.bean.CourseDetailBean;
//import com.education.online.bean.EvaluateBean;
//import com.education.online.bean.EvaluateListBean;
//import com.education.online.util.Constant;
//import com.education.online.util.ScreenUtil;
//import com.education.online.util.VideoUtil;
//import com.upyun.upplayer.widget.UpVideoView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import tv.danmaku.ijk.media.player.IMediaPlayer;
//
///**
// * Created by Administrator on 2016/8/25.
// */
//
//public class VideoPage extends BaseFragment implements View.OnClickListener {
//
//
//    private boolean Ispaid = false;
//    private TextView paytips, payBtn;
//    private LinearLayout details, directory, comments;
//    private TextView textdetails, textdirectory, textcomments;
//    private View viewdetails, viewdirectory, viewcomments;
//    private ImageView roundLeftBack;
//    private RecyclerView recyclerList;
//    private View lastSelectedview;
//    private int lastSelectedPosition;
//    private RelativeLayout videorelated;
//    private List<EvaluateBean> evaluateList = new ArrayList<>();
//    private CourseDetailBean courseDetailBean;
//    private EvaluateListBean evaluateListBean;
//    String path = "rtmp://live.hkstv.hk.lxdns.com/live/hks/";
//    private UpVideoView upVideoView;
//    private SeekBar seekbar;
//    private ImageView playBtn, expandBtn, video_play;
//    RelativeLayout.LayoutParams mVideoParams;
//    RelativeLayout relativelayout1;
//private View.OnClickListener listener;
//    public void setCourseDetailBean(CourseDetailBean courseDetailBean) {
//        this.courseDetailBean = courseDetailBean;
//    }
//
//    public void setEvaluateListBean(EvaluateListBean evaluateListBean) {
//        this.evaluateListBean = evaluateListBean;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.course_detail_main, container, false);
//
//        initView(view);
//        evaluateList.addAll(evaluateListBean.getEvaluateList());
//        recyclerList.setAdapter(new DetailsAdapter(getActivity(), courseDetailBean));
//        return view;
//    }
//
//    public void setPaidStatus(boolean flag) {
//        this.Ispaid = flag;
//    }
//
//
//    private void initView(View v) {
//        recyclerList = (RecyclerView) v.findViewById(R.id.courseRecycleView);
//        details = (LinearLayout) v.findViewById(R.id.details);
//        details.setOnClickListener(this);
//        directory = (LinearLayout) v.findViewById(R.id.directory);
//        directory.setOnClickListener(this);
//        comments = (LinearLayout) v.findViewById(R.id.comments);
//        comments.setOnClickListener(this);
//
//        paytips = (TextView) v.findViewById(R.id.payTips);
//        payBtn = (TextView) v.findViewById(R.id.payBtn);
//        roundLeftBack = (ImageView) v.findViewById(R.id.roundLeftBack);
//
//        textdetails = (TextView) v.findViewById(R.id.textdetails);
//        textdirectory = (TextView) v.findViewById(R.id.textdirectory);
//        textcomments = (TextView) v.findViewById(R.id.textcomments);
//
//        viewdetails = v.findViewById(R.id.viewdetails);
//        viewdirectory = v.findViewById(R.id.viewdirectory);
//        viewcomments = v.findViewById(R.id.viewcomments);
//        relativelayout1 = (RelativeLayout) v.findViewById(R.id.relativelayout1);
//        videorelated = (RelativeLayout) v.findViewById(R.id.videorelated);
//        playBtn = (ImageView) v.findViewById(R.id.playBtn);
//        playBtn.setOnClickListener(this);
//        expandBtn = (ImageView) v.findViewById(R.id.expandBtn);
////        expandBtn.setOnClickListener(this);
//        seekbar = (SeekBar) v.findViewById(R.id.seekbar);
//
//        upVideoView = (UpVideoView) v.findViewById(R.id.upVideoView);
//        int width = ScreenUtil.getWidth(getActivity());
//        int height = (int)(((float)width)*9/16);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
//      //  relativelayout1.setLayoutParams(params);
//        relativelayout1.setLayoutParams(new LinearLayout.LayoutParams(width,height));
//        upVideoView.setLayoutParams(params);
//        ///播放完成时动作
//        upVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(IMediaPlayer mp) {
//                video_play.setVisibility(View.VISIBLE);
//                video_play.setClickable(true);
//            }
//        });
//        video_play = (ImageView) v.findViewById(R.id.video_play);
//        video_play.setOnClickListener(this);
//
//        // get layout Manager
//        String ispaid = courseDetailBean.getIs_buy();
//        //set Status
//        if (ispaid.equals("0")) {//没买
//            //do sth
//            payBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //do sth跳转到购买课程页面
//                }
//            });
//        } else {
//            video_play.setVisibility(View.VISIBLE);
//            video_play.setClickable(false);
//            videorelated.setVisibility(View.VISIBLE);
//            paytips.setVisibility(View.INVISIBLE);
//            payBtn.setVisibility(View.INVISIBLE);
//            payBtn.setClickable(false);
//            expandBtn.setClickable(false);
//           String relativepath = courseDetailBean.getCourse_extm().get(0).getUrl();
//            path= VideoUtil.getVideoUrl(relativepath);
//
//            if (relativepath.length() > 0)
//            //设置背景图片
////        upVideoView.setImage(R.drawable.dog);
//            //设置播放地址
//            {
//                video_play.setClickable(true);
//                payBtn.setClickable(true);
//                expandBtn.setClickable(true);
//                upVideoView.setVisibility(View.VISIBLE);
//                upVideoView.setVideoPath(path);
//
//                //开始播放
//                upVideoView.start();
//                //暂停看风景
//                upVideoView.pause();
//            }//else
//            //  {
//            //  playBtn.setClickable(false);
//            //  video_play.setClickable(false);
//            //  expandBtn.setClickable(false);
//            //  }
//        }
//
//        //
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerList.setLayoutManager(layoutManager);
//        lastSelectedview = details;
//        lastSelectedPosition = 0;
//        listener =  new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        };
//
//    }
//
//    public void setStatusFalse(int pos) {
//        switch (pos) {
//            case 0:
//                textdetails.setTextColor(getResources().getColor(R.color.light_gray));
//                viewdetails.setVisibility(View.INVISIBLE);
//                break;
//            case 1:
//                textdirectory.setTextColor(getResources().getColor(R.color.light_gray));
//                viewdirectory.setVisibility(View.INVISIBLE);
//                break;
//            case 2:
//                textcomments.setTextColor(getResources().getColor(R.color.light_gray));
//                viewcomments.setVisibility(View.INVISIBLE);
//                break;
//        }
//    }
//
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//        }
//        super.onConfigurationChanged(newConfig);
//    }
//
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        upVideoView.release(true);
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        if (view != lastSelectedview) {
//            setStatusFalse(lastSelectedPosition);
//            switch (view.getId()) {
//                case R.id.details:
//                    recyclerList.setAdapter(new DetailsAdapter(getActivity(), courseDetailBean));
//                    lastSelectedview = details;
//                    lastSelectedPosition = 0;
//                    textdetails.setTextColor(getResources().getColor(R.color.dark_orange));
//                    viewdetails.setVisibility(View.VISIBLE);
//                    break;
//                case R.id.directory:
//                    recyclerList.setAdapter(new DirectoryAdapter(getActivity(), courseDetailBean,listener));
//                    lastSelectedview = directory;
//                    lastSelectedPosition = 1;
//                    textdirectory.setTextColor(getResources().getColor(R.color.dark_orange));
//                    viewdirectory.setVisibility(View.VISIBLE);
//
//                    break;
//                case R.id.comments:
//                    recyclerList.setAdapter(new CommentsAdapter(getActivity(), courseDetailBean,evaluateList));
//                    lastSelectedview = comments;
//                    lastSelectedPosition = 2;
//                    textcomments.setTextColor(getResources().getColor(R.color.dark_orange));
//                    viewcomments.setVisibility(View.VISIBLE);
//                    break;
//                case R.id.video_play:
//                case R.id.playBtn:
//                    if (upVideoView.isPlaying()) {
//
//                        //暂停播放
//                        upVideoView.pause();
//                        video_play.setVisibility(View.VISIBLE);
//
//                    } else {
//
//                        //开始播放
//                        upVideoView.start();
//                        video_play.setVisibility(View.INVISIBLE);
//
//                    }
//                    break;
//                case R.id.expandBtn:
//                    Activity act=getActivity();
//                    if (act.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//                        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    }
//                    act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    DisplayMetrics metrics = new DisplayMetrics();
//                    act.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
//                    mVideoParams = (RelativeLayout.LayoutParams) upVideoView.getLayoutParams();
//                    upVideoView.setLayoutParams(params);
//                    upVideoView.getTrackInfo();
//                    break;
//            }
//        }
//    }
//
//}
