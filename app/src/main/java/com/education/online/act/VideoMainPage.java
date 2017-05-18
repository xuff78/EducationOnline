package com.education.online.act;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.order.SubmitOrder;
import com.education.online.act.teacher.CourseBaseInfoModify;
import com.education.online.act.video.VideoMain;
import com.education.online.adapter.CommentsAdapter;
import com.education.online.adapter.DetailsAdapter;
import com.education.online.adapter.DirectoryAdapter;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.CourseExtm;
import com.education.online.bean.EvaluateBean;
import com.education.online.bean.EvaluateListBean;
import com.education.online.bean.JsonMessage;
import com.education.online.download.DownloadService;
import com.education.online.download.ThreadDAOImpl;
import com.education.online.download.ThreadInfo;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.OpenfileUtil;
import com.education.online.util.SHA;
import com.education.online.util.ScreenUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.util.VideoThumbnailLoader;
import com.education.online.util.VideoUtil;
import com.education.online.view.DownLoadDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoMainPage extends BaseFrameAct implements DownLoadDialog.DownloadCallback, OnInfoListener {

    int currentPos = 0;
    private LinearLayout addfavorite_layout, share_layout, download_layout, dellayout;
    private TextView textaddfavorite, textshare, textdownload, textaddorbuy;
    private ImageView addfavorite, share, download, background;
    private View lastSelectedView = null, editlayout;
    private LinearLayoutManager layoutManager;
    private TextView paytips, payBtn;
    private LinearLayout details, directory, comments;
    private TextView textdetails, textdirectory, textcomments;
    private View viewdetails, viewdirectory, viewcomments, bottomLayout;
    private ImageView roundLeftBack;
    private RecyclerView recyclerList;
    private View lastSelectedview;
    private int lastSelectedPosition;
    private boolean warnVideo=false;
    private boolean warnDownload=false;
//    private RelativeLayout videorelated;
    String path = "rtmp://live.hkstv.hk.lxdns.com/live/hks/";
    private VideoView upVideoView;
//    private SeekBar seekbar;
    private ImageView /*playBtn, expandBtn,*/ video_play, videoMask;
    RelativeLayout.LayoutParams mVideoParams;
    RelativeLayout relativelayout1;
    private List<EvaluateBean> evaluateList = new ArrayList<>();
    private int page = 1;
    private String pageSize = "20";
    private boolean onloading = false;
    private ImageLoader imageLoader;
    private ThreadDAOImpl mDao;
    private CourseDetailBean courseDetailBean = new CourseDetailBean();
    private EvaluateListBean evaluateListBean = new EvaluateListBean();
    private int openFilePos=-1;
    public  ArrayList<ThreadInfo> files=new ArrayList<>();
    private String type = "";

    private String course_id;
    private boolean flag = false;
    Intent intent;
    HttpHandler httpHandler;

    private CommentsAdapter commentsAdapter;
    private DirectoryAdapter directoryAdapter;
    private DetailsAdapter detailsAdapter;
    private View.OnClickListener listener;
    private String my_usercode = "";
    private DownLoadDialog downLoadDialog;
    private DownloadService.DownloadBinder myBinder;
    private MediaController mediaController;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (DownloadService.DownloadBinder) service;
        }
    };

    public void initiHandler() {
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if (method.equals(Method.getCourseDtail)) {
                    JsonUtil.getCourseDetail(jsonData, courseDetailBean);
                    queryDB();
                    // get layout Manager
                    String ispaid = courseDetailBean.getIs_buy();
                    //set Status
                    if (ispaid.equals("0")&&!my_usercode.equals(courseDetailBean.getUsercode())) {//没买
                        //do sth
                        paytips.setVisibility(View.VISIBLE);
                        payBtn.setVisibility(View.VISIBLE);
                        textaddorbuy.setOnClickListener(listener);
                        download_layout.setVisibility(View.INVISIBLE);
                        payBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(VideoMainPage.this, SubmitOrder.class);
                                i.putExtra(CourseDetailBean.Name, courseDetailBean);
                                startActivity(i);
                            }
                        });
                    } else {
                        if (intent.hasExtra("Edit")) {
                            if (intent.getStringExtra("status").equals("1")) {
                                textaddorbuy.setText("已审核");
                            } else if (intent.getStringExtra("status").equals("0")) {
                                textaddorbuy.setText("待审核");
                            } else if (intent.getStringExtra("status").equals("2")) {
                                textaddorbuy.setText("已拒绝");
                            }
                            textaddorbuy.setOnClickListener(null);
                        }else {
                            textaddorbuy.setText("继续学习");
                            textaddorbuy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (type == "video") {
                                        if (video_play.isShown())
                                            if (ActUtil.isWifi(VideoMainPage.this)) {
                                                playVideo();
                                            }else{
                                                DialogUtil.showConfirmDialog(VideoMainPage.this, "提示", "正在使用流量，是否继续？", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        warnVideo=true;
                                                        dialogInterface.dismiss();
                                                        playVideo();
                                                    }
                                                });
                                            }
                                    }
                                }
                            });
                        }
//                        video_play.setVisibility(View.VISIBLE);
//                        video_play.setClickable(false);
                        paytips.setVisibility(View.INVISIBLE);
                        payBtn.setVisibility(View.INVISIBLE);
                        payBtn.setClickable(false);
//                        expandBtn.setClickable(false);
                    }

                    _setHeaderTitle(courseDetailBean.getCourse_name());

                    if (my_usercode.equals(courseDetailBean.getUsercode())) {
                        editlayout.setVisibility(View.VISIBLE);
                        dellayout.setVisibility(View.VISIBLE);
                        addfavorite_layout.setVisibility(View.GONE);
                        download_layout.setVisibility(View.GONE);
                        share_layout.setVisibility(View.GONE);
                    }

                    detailsAdapter.notifyDataSetChanged();
                    if (courseDetailBean.getCourse_extm().size() > 0) {
                        SetplayerOrImageState(0);//初始化状态
                        //  }
                    }
                    if (courseDetailBean.getIs_collection().equals("0")) {
                        flag = false;
                        addfavorite.setSelected(false);

                    } else {
                        flag = true;
                        addfavorite.setSelected(true);
                    }
                    httpHandler.getEvaluateList(course_id, null, pageSize, "1");

                } else if (method.equals(Method.getEvaluateList)) {
                    onloading = false;
                    evaluateListBean = JsonUtil.getEvaluateList(jsonData);
                    evaluateList.addAll(evaluateListBean.getEvaluateList());
                    commentsAdapter.notifyDataSetChanged();
                    page++;
                    //   Toast.makeText(CourseMainPage.this,"success",Toast.LENGTH_SHORT).show();
                } else if (method.equals(Method.addCollection)) {
                    if (flag)
                        Toast.makeText(VideoMainPage.this, "收藏成功！", Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(VideoMainPage.this, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                }else if(method.equals(Method.deleteCourse)){
                    ToastUtils.displayTextShort(VideoMainPage.this, "删除成功");
                    finish();
                }

                //  DialogUtil.showInfoDailog(CourseMainPage.this, "提示", "发布课程成功!");
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage, String jsonData) {
                super.onFailure(method, jsonMessage, jsonData);
                if (method.equals(Method.getEvaluateList)) {
                    onloading = false;
                    commentsAdapter.setLoadingHint("加载失败");
                }
            }

            @Override
            public void onHTTPException(String method, String jsonMessage) {
                super.onHTTPException(method, jsonMessage);
                if (method.equals(Method.getEvaluateList)) {
                    onloading = false;
                    commentsAdapter.setLoadingHint("加载失败");
                }
            }
        });
        //
    }

    private void queryDB() {
        files.clear();
        for (CourseExtm extm:courseDetailBean.getCourse_extm()){
            ThreadInfo fileInfo=new ThreadInfo();
            fileInfo.setSubname(extm.getName());
            fileInfo.setCoursename(courseDetailBean.getCourse_name());
            fileInfo.setCourseimg(courseDetailBean.getImg());
            fileInfo.setCourseid(courseDetailBean.getCourse_id());
            fileInfo.setEnd(1); //防止除0
            fileInfo.setTotalfilecount(courseDetailBean.getCourse_extm().size());
            int pos=extm.getUrl().lastIndexOf("/");
            if(pos>0)
                fileInfo.setFileName(extm.getUrl().substring(pos));
            else
                fileInfo.setFileName(SHA.getSHA(extm.getUrl()));
            fileInfo.setUrl(ImageUtil.getImageUrl(extm.getUrl()));
            List<ThreadInfo> listThread=mDao.getThreadsByUrl(fileInfo.getUrl());
            if(listThread.size()>0){
                ThreadInfo threadInfo=listThread.get(0);
                fileInfo.setStatus(threadInfo.getComplete()+2);
                fileInfo.setStart(threadInfo.getStart());
                fileInfo.setFinished(threadInfo.getFinished());
                fileInfo.setEnd(threadInfo.getEnd());
            }
            files.add(fileInfo);
        }
    }

    private void SetplayerOrImageState(int i) {
        boolean isSamePos=openFilePos==i?true:false;
        openFilePos=i;
        String relativepath = courseDetailBean.getCourse_extm().get(i).getUrl();
        path = VideoUtil.getVideoUrl(relativepath);

        if(files.get(openFilePos).getStatus()==3&&isSamePos){  //再次点击同一个条目就调用系统支持的应用打开
            File file = new File(DownloadService.DOWNLOAD_PATH+files.get(openFilePos).getFileName());
            FileUtil.openFile(this, file);
        }else if (relativepath.length() > 0){
            type = OpenfileUtil.getFiletype(relativepath);
            if (type == "image") {
                if (upVideoView.isPlaying()) {
                    upVideoView.pause();
                }
                upVideoView.setVisibility(View.INVISIBLE);
                background.setVisibility(View.VISIBLE);
                if (courseDetailBean.getIs_buy().equals("1")||my_usercode.equals(courseDetailBean.getUsercode()))
                    imageLoader.displayImage(ImageUtil.getImageUrl(relativepath), background);
                videoMask.setVisibility(View.GONE);
            } else if (type == "video") {
                payBtn.setClickable(true);
                //       upVideoView.pause();
                upVideoView.setVisibility(View.VISIBLE);
                if (courseDetailBean.getIs_buy().equals("1")||my_usercode.equals(courseDetailBean.getUsercode())) {
                    upVideoView.stopPlayback();

                    VideoThumbnailLoader.getIns().display(this, path, videoMask,
                            100, 100, new VideoThumbnailLoader.ThumbnailListener() {
                                @Override
                                public void onThumbnailLoadCompleted(String url, ImageView iv, Bitmap bitmap) {
                                    if (bitmap != null)
                                        iv.setImageBitmap(bitmap);
                                }
                            });

                    video_play.setVisibility(View.VISIBLE);

//                    mp.setClickIsFullScreenListener(new MediaController.onClickIsFullScreenListener() {
//                        @Override
//                        public void setOnClickIsFullScreen() {
//                            upVideoView.fullScreen(VideoMainPage.this);
//                        }
//                    });
                }else {
                    videoMask.setVisibility(View.GONE);
                }
//                upVideoView.start();
                //暂停看风景
                //upVideoView.pause();
            } else {
                if (upVideoView.isPlaying()) {
                    upVideoView.pause();
                }
                upVideoView.setVisibility(View.INVISIBLE);
                videoMask.setVisibility(View.GONE);
                background.setVisibility(View.VISIBLE);
                imageLoader.displayImage(ImageUtil.getImageUrl(courseDetailBean.getImg()), background);
                if(files.get(openFilePos).getStatus()==3){
                    File file = new File(DownloadService.DOWNLOAD_PATH+files.get(openFilePos).getFileName());
                    FileUtil.openFile(this, file);
                }
            }
        } else {
            if (upVideoView.isPlaying()) {
                upVideoView.pause();
            }
            upVideoView.setVisibility(View.INVISIBLE);
            background.setVisibility(View.VISIBLE);
            imageLoader.displayImage(ImageUtil.getImageUrl(courseDetailBean.getImg()), background);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.videodetail);


        EventBus.getDefault().register(this);
        _setHeaderGone();
        mDao = new ThreadDAOImpl(this);
        initiHandler();
        initView();


        Intent startIntent=new Intent(this, DownloadService.class);
        startService(startIntent);

        Intent bindIntent = new Intent(this, DownloadService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
        httpHandler.getCourseDetail(course_id);
    }

    private void initView() {

        bottomLayout=findViewById(R.id.bottomLayout);
        my_usercode = SharedPreferencesUtil.getUsercode(this);
        listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.addfavoritelayout:

                        if (!flag) {
                            addfavorite.setSelected(true);
                            flag = true;
                        } else {
                            addfavorite.setSelected(false);
                            flag = false;
                        }
                        httpHandler.addCollection(course_id);
                        break;


                    case R.id.sharelayout:
                        //do sth;

                        break;
                    case R.id.downloadlayout:
                        if(!warnDownload&&SharedPreferencesUtil.getValue(VideoMainPage.this, Constant.WIFIWarning, true)){
                            if (ActUtil.isWifi(VideoMainPage.this)) {
                                selectDownload();
                            }else{
                                DialogUtil.showConfirmDialog(VideoMainPage.this, "提示", "正在使用流量，是否继续？", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        warnDownload=true;
                                        dialogInterface.dismiss();
                                        selectDownload();
                                    }
                                });
                            }
                        }else
                            selectDownload();
                        break;
                    case R.id.addorbuy:
                        //do sth;
                        Intent i = new Intent(VideoMainPage.this, SubmitOrder.class);
                        i.putExtra(CourseDetailBean.Name, courseDetailBean);
                        startActivity(i);
                        break;
                    case R.id.details:
                        recyclerList.setAdapter(detailsAdapter);
                        if (view != lastSelectedView)
                            setStatusFalse(lastSelectedPosition);
                        lastSelectedview = details;
                        lastSelectedPosition = 0;
                        textdetails.setTextColor(getResources().getColor(R.color.dark_orange));
                        viewdetails.setVisibility(View.VISIBLE);
                        break;
                    case R.id.directory:
                        directoryAdapter=new DirectoryAdapter(VideoMainPage.this, files, listener, myBinder);
                        recyclerList.setAdapter(directoryAdapter);
                        if (view != lastSelectedView)
                            setStatusFalse(lastSelectedPosition);
                        lastSelectedview = directory;
                        lastSelectedPosition = 1;
                        textdirectory.setTextColor(getResources().getColor(R.color.dark_orange));
                        viewdirectory.setVisibility(View.VISIBLE);

                        break;
                    case R.id.comments:
                        recyclerList.setAdapter(commentsAdapter);
                        if (view != lastSelectedView)
                            setStatusFalse(lastSelectedPosition);
                        lastSelectedview = comments;
                        lastSelectedPosition = 2;
                        textcomments.setTextColor(getResources().getColor(R.color.dark_orange));
                        viewcomments.setVisibility(View.VISIBLE);
                        break;
                    case R.id.video_play:
                        if (upVideoView.isPlaying()) {
                            stopPlay();
                        } else {
//                            MediaController mediaController=new MediaController(VideoMainPage.this);
                            if(!warnVideo&&SharedPreferencesUtil.getValue(VideoMainPage.this, Constant.WIFIWarning, true)){
                                if (ActUtil.isWifi(VideoMainPage.this)) {
                                    playVideo();
                                }else{
                                    DialogUtil.showConfirmDialog(VideoMainPage.this, "提示", "正在使用流量，是否继续？", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            warnVideo=true;
                                            dialogInterface.dismiss();
                                            playVideo();
                                        }
                                    });
                                }
                            }else
                                playVideo();

                        }
                        break;
                    case R.id.expendBtn:
                        if(!upVideoView.isFullState) {
                            relativelayout1.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                            upVideoView.fullScreen(VideoMainPage.this);
                            upVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_ORIGIN, 0);
                        }else{
                            int width = ScreenUtil.getWidth(VideoMainPage.this);
                            int height = (int) (((float) width) * 9 / 16);
                            relativelayout1.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                            upVideoView.fullScreen(VideoMainPage.this);
                        }
                        break;
                    case R.id.roundLeftBack:

                        onBackPressed();
                        break;
                    case R.id.textholder:
                        int pos = (int) view.getTag();
                        SetplayerOrImageState(pos);
                        break;
                    case R.id.editlayout:
                        Intent intent=new Intent(VideoMainPage.this, CourseBaseInfoModify.class);
                        intent.putExtra(CourseDetailBean.Name, courseDetailBean);
                        startActivityForResult(intent, 0x13);
                        break;
                    case R.id.dellayout:
                        DialogUtil.showConfirmDialog(VideoMainPage.this, "提示", "确认删除该课程?", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                httpHandler.deleteCourse(courseDetailBean.getCourse_id());
                            }
                        });
                        break;
                }

            }
        };

        dellayout = (LinearLayout) findViewById(R.id.dellayout);
        dellayout.setOnClickListener(listener);
        mediaController= (MediaController) findViewById(R.id.mediaController);
        mediaController.setExpendListener(listener);
        imageLoader = ImageLoader.getInstance();
        intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        addfavorite = (ImageView) findViewById(R.id.addfavorite);
        share = (ImageView) findViewById(R.id.share);
        video_play = (ImageView) findViewById(R.id.video_play);
        video_play.setOnClickListener(listener);
        download = (ImageView) findViewById(R.id.download);
        textaddfavorite = (TextView) findViewById(R.id.textAddFavorite);
        textshare = (TextView) findViewById(R.id.textShare);
        textdownload = (TextView) findViewById(R.id.textDownload);
        textaddorbuy = (TextView) findViewById(R.id.addorbuy);
        addfavorite_layout = (LinearLayout) findViewById(R.id.addfavoritelayout);
        addfavorite_layout.setOnClickListener(listener);
        share_layout = (LinearLayout) findViewById(R.id.sharelayout);
        share_layout.setOnClickListener(listener);
        download_layout = (LinearLayout) findViewById(R.id.downloadlayout);
        download_layout.setOnClickListener(listener);

        editlayout=findViewById(R.id.editlayout);
        editlayout.setOnClickListener(listener);
        recyclerList = (RecyclerView) findViewById(R.id.courseRecycleView);
        details = (LinearLayout) findViewById(R.id.details);
        details.setOnClickListener(listener);
        directory = (LinearLayout) findViewById(R.id.directory);
        directory.setOnClickListener(listener);
        comments = (LinearLayout) findViewById(R.id.comments);
        comments.setOnClickListener(listener);
        background = (ImageView) findViewById(R.id.background);
        paytips = (TextView) findViewById(R.id.payTips);
        payBtn = (TextView) findViewById(R.id.payBtn);
        roundLeftBack = (ImageView) findViewById(R.id.roundLeftBack);
        roundLeftBack.setOnClickListener(listener);
        textdetails = (TextView) findViewById(R.id.textdetails);
        textdirectory = (TextView) findViewById(R.id.textdirectory);
        textcomments = (TextView) findViewById(R.id.textcomments);

        viewdetails = findViewById(R.id.viewdetails);
        viewdirectory = findViewById(R.id.viewdirectory);
        viewcomments = findViewById(R.id.viewcomments);
        relativelayout1 = (RelativeLayout) findViewById(R.id.relativelayout1);

        videoMask = (ImageView) findViewById(R.id.videoMask);
        upVideoView = (VideoView) findViewById(R.id.upVideoView);
        upVideoView.setMediaController(mediaController);
        upVideoView.setBufferSize(1024*1024*5);
//        upVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_ORIGIN, 0);
        upVideoView.setOnInfoListener(this);
        upVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
                upVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_FIT_PARENT, 0);
            }
        });
//        upVideoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        int width = ScreenUtil.getWidth(this);
        int height = (int) (((float) width) * 9 / 16);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        //  relativelayout1.setLayoutParams(params);
        relativelayout1.setLayoutParams(new LinearLayout.LayoutParams(width, height));
//        upVideoView.setLayoutParams(params);


        //
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.addOnScrollListener(srcollListener);
        lastSelectedview = details;
        lastSelectedPosition = 0;
        commentsAdapter = new CommentsAdapter(this, courseDetailBean, evaluateList);
        detailsAdapter = new DetailsAdapter(this, courseDetailBean);
        recyclerList.setAdapter(detailsAdapter);

    }

    private void selectDownload() {
        downLoadDialog=new DownLoadDialog(VideoMainPage.this, VideoMainPage.this, files,
                ImageUtil.dip2px(VideoMainPage.this, 90)+recyclerList.getHeight());
        downLoadDialog.show();
    }

    private void playVideo() {
        if(files.get(openFilePos).getStatus()==3){
            path = DownloadService.DOWNLOAD_PATH+files.get(openFilePos).getFileName();
            upVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        }else
            upVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_LOW);
        upVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                upVideoView.stopPlayback();
                upVideoView.openVideo();
//                upVideoView.pause();
            }
        });
        upVideoView.setVideoPath(path);
        //开始播放
        upVideoView.start();
        videoMask.setVisibility(View.GONE);
        video_play.setVisibility(View.GONE);
    }

    private void stopPlay() {
        //暂停播放
        upVideoView.stopPlayback();
    }

    public void setStatusFalse(int pos) {
        switch (pos) {
            case 0:
                textdetails.setTextColor(getResources().getColor(R.color.light_gray));
                viewdetails.setVisibility(View.INVISIBLE);
                break;
            case 1:
                textdirectory.setTextColor(getResources().getColor(R.color.light_gray));
                viewdirectory.setVisibility(View.INVISIBLE);
                break;
            case 2:
                textcomments.setTextColor(getResources().getColor(R.color.light_gray));
                viewcomments.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bottomLayout.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            bottomLayout.setVisibility(View.VISIBLE);
            upVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_FIT_PARENT, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (upVideoView.isFullState) {
            //退出全屏
            int width = ScreenUtil.getWidth(VideoMainPage.this);
            int height = (int) (((float) width) * 9 / 16);
            relativelayout1.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            upVideoView.exitFullScreen(this);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(upVideoView.isPlaying())
            upVideoView.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(upVideoView.isPlaying())
            upVideoView.pause();
    }

    RecyclerView.OnScrollListener srcollListener = new RecyclerView.OnScrollListener() {

        int lastVisibleItem = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == commentsAdapter.getItemCount()) {
                if (!onloading) {
                    if (evaluateListBean.getCurrent_page() < evaluateListBean.getPagetotal()) {
                        onloading = true;
                        httpHandler.getEvaluateList(course_id, null, pageSize, String.valueOf(page));
                        commentsAdapter.setLoadingHint("正在加载");
                    } else
                        commentsAdapter.setLoadingHint("共"+evaluateList.size()+"条评论");
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    };

    public void onEventMainThread(ThreadInfo info) {
        info.setStatus(3);
    }

    @Override
    protected void onDestroy() {
        upVideoView.stopPlayback();
        unbindService(connection);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void startDownload(ArrayList<ThreadInfo> fileInfos) {
        for (ThreadInfo info : files) {
            if (info.getStatus() == 1) {
                info.setStatus(2);
                myBinder.startDownload(info, null);
            }
        }
        this.files=fileInfos;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(lastSelectedPosition==1){
                    listener.onClick(directory);
                }
            }
        },1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==0x22){
            httpHandler.getCourseDetail(course_id);
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (upVideoView.isPlaying()) {
                    upVideoView.pause();
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                upVideoView.start();
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                break;
        }
        return true;
    }
}
