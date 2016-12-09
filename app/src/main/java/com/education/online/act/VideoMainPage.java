package com.education.online.act;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.order.SubmitOrder;
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
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.OpenfileUtil;
import com.education.online.util.SHA;
import com.education.online.util.ScreenUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.VideoThumbnailLoader;
import com.education.online.util.VideoUtil;
import com.education.online.view.DownLoadDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.upyun.upplayer.widget.UpVideoView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoMainPage extends BaseFrameAct implements DownLoadDialog.DownloadCallback{

    int currentPos = 0;
    private LinearLayout addfavorite_layout, share_layout, download_layout;
    private TextView textaddfavorite, textshare, textdownload, textaddorbuy, currentTime;
    private ImageView addfavorite, share, download, background;
    private View lastSelectedView = null;
    private LinearLayoutManager layoutManager;
    private TextView paytips, payBtn;
    private LinearLayout details, directory, comments;
    private TextView textdetails, textdirectory, textcomments, totalTime;
    private View viewdetails, viewdirectory, viewcomments;
    private ImageView roundLeftBack;
    private RecyclerView recyclerList;
    private View lastSelectedview;
    private int lastSelectedPosition;
    private RelativeLayout videorelated;
    String path = "rtmp://live.hkstv.hk.lxdns.com/live/hks/";
    private UpVideoView upVideoView;
    private SeekBar seekbar;
    private ImageView playBtn, expandBtn, video_play, videoMask;
    RelativeLayout.LayoutParams mVideoParams;
    RelativeLayout relativelayout1;
    private List<EvaluateBean> evaluateList = new ArrayList<>();
    private int page = 1;
    private String pageSize = "20";
    private boolean onloading = false;
    private ImageLoader imageLoader;
    private long totaltime=0;
    private ThreadDAOImpl mDao;
    private CourseDetailBean courseDetailBean = new CourseDetailBean();
    private EvaluateListBean evaluateListBean = new EvaluateListBean();
    private int openFilePos=0;
    public  ArrayList<ThreadInfo> files=new ArrayList<>();

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
                        video_play.setVisibility(View.INVISIBLE);
                        video_play.setClickable(false);
                        videorelated.setVisibility(View.GONE);
                        textaddorbuy.setOnClickListener(listener);
                        download_layout.setVisibility(View.GONE);
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
                        }else {
                            textaddorbuy.setText("");
                        }
                        textaddorbuy.setOnClickListener(null);
                        video_play.setVisibility(View.VISIBLE);
                        video_play.setClickable(false);
                        videorelated.setVisibility(View.VISIBLE);
                        paytips.setVisibility(View.INVISIBLE);
                        payBtn.setVisibility(View.INVISIBLE);
                        payBtn.setClickable(false);
                        expandBtn.setClickable(false);
                    }

                    _setHeaderTitle(courseDetailBean.getCourse_name());

                    if (my_usercode.equals(courseDetailBean.getUsercode())) {
                        share.setVisibility(View.INVISIBLE);
                        addfavorite.setVisibility(View.INVISIBLE);
                        download.setVisibility(View.INVISIBLE);
                        textaddfavorite.setVisibility(View.INVISIBLE);
                        textshare.setVisibility(View.INVISIBLE);
                        textdownload.setVisibility(View.INVISIBLE);
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
                }

                //  DialogUtil.showInfoDailog(CourseMainPage.this, "提示", "发布课程成功!");
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
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
        openFilePos=i;
        String relativepath = courseDetailBean.getCourse_extm().get(i).getUrl();
        path = VideoUtil.getVideoUrl(relativepath);

        if (relativepath.length() > 0){
            String type = "";
            type = OpenfileUtil.getFiletype(relativepath);
            if (type == "image") {
                if (upVideoView.isPlaying()) {
                    upVideoView.pause();
                    upVideoView.release(true);
                }
                video_play.setVisibility(View.INVISIBLE);
                upVideoView.setVisibility(View.INVISIBLE);
                background.setVisibility(View.VISIBLE);
                if (courseDetailBean.getIs_buy().equals("1")||my_usercode.equals(courseDetailBean.getUsercode()))
                    imageLoader.displayImage(ImageUtil.getImageUrl(relativepath), background);
                videorelated.setVisibility(View.GONE);
                videoMask.setVisibility(View.GONE);
            } else if (type == "video") {
                if (courseDetailBean.getIs_buy().equals("1")||my_usercode.equals(courseDetailBean.getUsercode())) {//没买
                    videorelated.setVisibility(View.VISIBLE);
                    video_play.setVisibility(View.VISIBLE);
                }
                video_play.setClickable(true);
                payBtn.setClickable(true);
                expandBtn.setClickable(true);
                //       upVideoView.pause();
                upVideoView.setVisibility(View.VISIBLE);
                upVideoView.setVideoPath(path);
                upVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(IMediaPlayer mp) {
                        totaltime=mp.getDuration();
                        totalTime.setText(ActUtil.getTimeFormat(totaltime/1000));
                    }
                });
                upVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(IMediaPlayer mp) {
                        video_play.setVisibility(View.VISIBLE);
                        video_play.setClickable(true);
                        playBtn.setImageResource(R.mipmap.icon_play);
                        currentTime.setText("00:00:00");
                        seekbar.setProgress(0);
                        timer.cancel();
                    }
                });
                videoMask.setVisibility(View.VISIBLE);
                if (courseDetailBean.getIs_buy().equals("1")||my_usercode.equals(courseDetailBean.getUsercode()))
                    VideoThumbnailLoader.getIns().display(this, path, videoMask,
                        100, 100, new VideoThumbnailLoader.ThumbnailListener() {
                            @Override
                            public void onThumbnailLoadCompleted(String url, ImageView iv, Bitmap bitmap) {
                                if (bitmap != null)
                                    iv.setImageBitmap(bitmap);
                            }
                        });
                else {
                    videoMask.setVisibility(View.GONE);
                    video_play.setVisibility(View.INVISIBLE);
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
                video_play.setVisibility(View.INVISIBLE);
                background.setVisibility(View.VISIBLE);
                imageLoader.displayImage(ImageUtil.getImageUrl(courseDetailBean.getImg()), background);
                videorelated.setVisibility(View.GONE);
            }
        } else {
            if (upVideoView.isPlaying()) {
                upVideoView.pause();
            }
            video_play.setVisibility(View.INVISIBLE);
            upVideoView.setVisibility(View.INVISIBLE);
            videorelated.setVisibility(View.GONE);
            background.setVisibility(View.VISIBLE);
            imageLoader.displayImage(ImageUtil.getImageUrl(courseDetailBean.getImg()), background);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videodetail);

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
                        downLoadDialog=new DownLoadDialog(VideoMainPage.this, VideoMainPage.this, files,
                                ImageUtil.dip2px(VideoMainPage.this, 90)+recyclerList.getHeight());
                        downLoadDialog.show();
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
                    case R.id.playBtn:
                        if (upVideoView.isPlaying()) {
                            playBtn.setImageResource(R.mipmap.icon_play);
                            //暂停播放
                            upVideoView.pause();
                            video_play.setVisibility(View.VISIBLE);
                            timer.cancel();
                        } else {
                            if(files.get(openFilePos).getStatus()==3){
                                path = DownloadService.DOWNLOAD_PATH+files.get(openFilePos).getFileName();
                                upVideoView.setVideoPath(path);
                                upVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(IMediaPlayer mp) {
                                        totaltime=mp.getDuration();
                                        totalTime.setText(ActUtil.getTimeFormat(totaltime/1000));
                                    }
                                });
                            }
                            timer=new Timer();
                            timer.schedule(new TimerTask() {

                                @Override
                                public void run() {
                                    handler.sendEmptyMessage(WHAT);
                                }
                            }, 0, 1000);
                            playBtn.setImageResource(R.mipmap.icon_video_stop);
                            //开始播放
                            upVideoView.start();
                            video_play.setVisibility(View.INVISIBLE);
                            videoMask.setVisibility(View.GONE);
                        }
                        break;
                    case R.id.expandBtn:
                        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        }
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        DisplayMetrics metrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
                        mVideoParams = (RelativeLayout.LayoutParams) upVideoView.getLayoutParams();
                        upVideoView.setLayoutParams(params);
                        upVideoView.getTrackInfo();
                        break;
                    case R.id.roundLeftBack:

                        onBackPressed();
                        break;
                    case R.id.textholder:
                        int pos = (int) view.getTag();
                        SetplayerOrImageState(pos);
                        break;
                }

            }
        };

        imageLoader = ImageLoader.getInstance();
        intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        addfavorite = (ImageView) findViewById(R.id.addfavorite);
        share = (ImageView) findViewById(R.id.share);
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
        totalTime = (TextView) findViewById(R.id.totalTime);
        currentTime = (TextView) findViewById(R.id.currentTime);

        viewdetails = findViewById(R.id.viewdetails);
        viewdirectory = findViewById(R.id.viewdirectory);
        viewcomments = findViewById(R.id.viewcomments);
        relativelayout1 = (RelativeLayout) findViewById(R.id.relativelayout1);
        videorelated = (RelativeLayout) findViewById(R.id.videorelated);
        playBtn = (ImageView) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(listener);
        expandBtn = (ImageView) findViewById(R.id.expandBtn);
//        expandBtn.setOnClickListener(this);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int lastProgress=0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                lastProgress=seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(upVideoView.isPlaying()){
                    int progress=seekBar.getProgress();
                    upVideoView.seekTo((int) (totaltime/100*progress));
                }else
                    seekBar.setProgress(lastProgress);
            }
        });

        videoMask = (ImageView) findViewById(R.id.videoMask);
        upVideoView = (UpVideoView) findViewById(R.id.upVideoView);
        int width = ScreenUtil.getWidth(this);
        int height = (int) (((float) width) * 9 / 16);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        //  relativelayout1.setLayoutParams(params);
        relativelayout1.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        upVideoView.setLayoutParams(params);

        video_play = (ImageView) findViewById(R.id.video_play);
        video_play.setOnClickListener(listener);

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
        }
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public void onStop() {
        super.onStop();
        upVideoView.release(true);
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


    private Timer timer = null;
    private final static int WHAT = 0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case WHAT:
                    int currentPlayer = upVideoView.getCurrentPosition();
                    if (currentPlayer > 0) {
                        currentTime.setText(ActUtil.getTimeFormat(currentPlayer/1000));

                        // 让seekBar也跟随改变
                        int progress = (int) ((currentPlayer / (float) totaltime) * 100);

                        seekbar.setProgress(progress);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(connection);
        if(timer!=null)
            timer.cancel();
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
}
