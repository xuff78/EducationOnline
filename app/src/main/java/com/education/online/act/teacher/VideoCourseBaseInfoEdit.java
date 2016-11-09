package com.education.online.act.teacher;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.login.SubjectSelector;
import com.education.online.act.upyun.UploadTask;
import com.education.online.adapter.VideoUploadProgressAdapter;
import com.education.online.bean.AddClassBean;
import com.education.online.bean.JsonMessage;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.UploadVideoProgress;
import com.education.online.bean.VideoImgItem;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.inter.CourseUpdate;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;
import com.education.online.view.SelectVideoDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.SignatureListener;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;
import com.upyun.library.utils.UpYunUtils;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.tungdx.mediapicker.MediaItem;
import vn.tungdx.mediapicker.MediaOptions;
import vn.tungdx.mediapicker.activities.MediaPickerActivity;

/**
 * Created by Administrator on 2016/9/5.
 */
public class VideoCourseBaseInfoEdit extends BaseFrameAct {


    private static final String TAG ="VideoCourseBaseInfoEdit";
    String phoneTxtName = "";
    private String course = "";
    private Dialog progressDialog;
    private TextView submitCourseBtn, uploadBtn, subjectTxt, priceTxt, joinNum;
    private ImageView courseImg;
    private EditText courseName, courseDesc;
    private int type = 0; // 0课件， 1视频, 2直播
    private View uploadLayout, joinNumLayout;
    private SelectPicDialog selectPicDialog;
    private ImageLoader imageloader;
    private ListView listView;
    private AddClassBean addClassBean;
    final int getVedioDoc = 0x20;
    HttpHandler httpHandler;
    private View.OnClickListener listener;
    private VideoUploadProgressAdapter adapter;
    private List<CourseUpdateListener> progressListeners=new ArrayList<>();

    private final int hight = 51;

    private ArrayList<UploadVideoProgress> uploadVideoProgresses = new ArrayList<>();
    public static final String EXTRA_MEDIA_OPTIONS = "extra_media_options";
    ////////////////mediapicker
    private static final int REQUEST_MEDIA = 100;
    private List<MediaItem> mMediaSelectedList = new ArrayList<>();
    SignatureListener signatureListener = new SignatureListener() {
        @Override
        public String getSignature(String raw) {
            return UpYunUtils.md5(raw + UploadTask.TEST_API_KEY);
        }
    };

    public VideoCourseBaseInfoEdit() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_course_edit);
        type = getIntent().getIntExtra("Type", 0);
        _setRightHomeGone();
        initView();
    }

    private void initView() {

     //   UploadVideoProgress progress = new UploadVideoProgress();
       // uploadVideoProgresses.add(progress);
       // uploadVideoProgresses.add(progress);
       // uploadVideoProgresses.add(progress);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.joinNumLayout:
                        startActivityForResult(new Intent(VideoCourseBaseInfoEdit.this, CourseMemberEdit.class), 0x10);
                        break;
                    case R.id.subjectLayout:
                        startActivityForResult(new Intent(VideoCourseBaseInfoEdit.this, SubjectSelector.class), 0x10);
                        break;
                    case R.id.priceLayout:
                        startActivityForResult(new Intent(VideoCourseBaseInfoEdit.this, CoursePriceEdit.class), 0x10);
                        break;
                    case R.id.courseImgLayout://选择图片层
                        selectPicDialog.show();
                        break;
                    case R.id.submitCourseBtn:
                        addClassBean.setIntroduction(courseDesc.getText().toString().trim());
                        addClassBean.setName(subjectTxt.getText().toString());
                        if (addClassBean.getName().length() == 0 || addClassBean.getIntroduction().length() == 0 || addClassBean.getCourse_type().length() == 0 || addClassBean.getSubject_id().length() == 0
                                || addClassBean.getOriginal_price().length() == 0 || addClassBean.getPrice().length() == 0 || addClassBean.getMin_follow().length() == 0
                                || addClassBean.getMax_follow().length() == 0 || addClassBean.getImg().length() == 0) {
                            Toast.makeText(VideoCourseBaseInfoEdit.this, "请填写完整信息", Toast.LENGTH_SHORT).show();

                        } else {
                            addClassBean.setName(courseName.getText().toString());
                            httpHandler.addClass(addClassBean);

                        }
                        break;
                    case R.id.uploadBtn:


                        MediaOptions.Builder builder = new MediaOptions.Builder();
                        MediaOptions options = null;
                        options = builder.selectVideo().canSelectMultiVideo(true).build();
                        if (options != null) {
                            // clearImages();
                            MediaPickerActivity.open(VideoCourseBaseInfoEdit.this, REQUEST_MEDIA, options);
                        }

                        // selectPicDialog.show();
                        // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        // if(type==1)
                        //     intent.setType("video/*");
                        // else intent.setType("*/*");
                        // intent.addCategory(Intent.CATEGORY_OPENABLE);
                        // startActivityForResult(intent,getVedioDoc);


                        break;
                    case R.id.delete:

                        int pos = (int) v.getTag();
                        uploadVideoProgresses.remove(pos);
                        progressListeners.get(pos).setPos(1000);
                        for (int i=pos+1;i<progressListeners.size();i++){
                            CourseUpdateListener progress=progressListeners.get(i);
                            progress.setPos(progress.getPos()-1);
                        }
                        progressListeners.remove(pos);
                        int num = uploadVideoProgresses.size();
                        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ImageUtil.dip2px(VideoCourseBaseInfoEdit.this, (hight * num))));
                        adapter.notifyDataSetChanged();
                        break;


                    case R.id.open:
                        int pos2 = (int) v.getTag();
                        break;
                }
            }
        };
        initiHandler();
        subjectTxt = (TextView) findViewById(R.id.subjectTxt);
        priceTxt = (TextView) findViewById(R.id.priceTxt);
        courseImg = (ImageView) findViewById(R.id.courseImg);
        courseName = (EditText) findViewById(R.id.courseName);
        courseDesc = (EditText) findViewById(R.id.courseDesc);
        joinNum = (TextView) findViewById(R.id.joinNum);
        submitCourseBtn = (TextView) findViewById(R.id.submitCourseBtn);
        submitCourseBtn.setOnClickListener(listener);
        uploadBtn = (TextView) findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(listener);
        uploadLayout = findViewById(R.id.uploadLayout);
        joinNumLayout = findViewById(R.id.joinNumLayout);
        joinNumLayout.setOnClickListener(listener);
        findViewById(R.id.subjectLayout).setOnClickListener(listener);
        findViewById(R.id.priceLayout).setOnClickListener(listener);
        findViewById(R.id.courseImgLayout).setOnClickListener(listener);
        addClassBean = new AddClassBean();
        selectPicDialog = new SelectPicDialog(this);
        listView = (ListView) findViewById(R.id.uploadview);

        adapter = new VideoUploadProgressAdapter(this, listener, uploadVideoProgresses);
        int num = uploadVideoProgresses.size();
        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ImageUtil.dip2px(VideoCourseBaseInfoEdit.this, (hight * num))));
        listView.setAdapter(adapter);


        if (type == 0) {
            uploadBtn.setText("上传课件");
            _setHeaderTitle("课件");
            addClassBean.setCourse_type("courseware");
        } else if (type == 1) {
            uploadBtn.setText("上传视频");
            _setHeaderTitle("视频课");
            addClassBean.setCourse_type("video");
            uploadBtn.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            uploadLayout.setVisibility(View.GONE);
            joinNumLayout.setVisibility(View.VISIBLE);
            submitCourseBtn.setText("下一步");
            _setHeaderTitle("开设直播课");
            addClassBean.setCourse_type("live");
        }
    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            File file = FileUtil.getFile(phoneTxtName + ".png", VideoCourseBaseInfoEdit.this);
            progressDialog.dismiss();
            if (file.exists()) {

                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        course = result.substring(1);
                        LogUtil.d("Img", course);
                        // imageloader.displayImage(ImageUtil.getImageUrl(course), courseImg);
                        Toast.makeText(VideoCourseBaseInfoEdit.this, "图片上传成功！", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(VideoCourseBaseInfoEdit.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "course/" + phoneTxtName + ".png");
            }
            Bitmap photo = BitmapFactory.decodeFile(file.toString());
            if (photo != null) {
                courseImg.setImageBitmap(photo);
                addClassBean.setImg("course/" + phoneTxtName + ".png");

            } else
                ToastUtils.displayTextShort(VideoCourseBaseInfoEdit.this, "找不到文件");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x11) {
            SubjectBean subjectBean = (SubjectBean) data.getSerializableExtra(SubjectBean.Name);
            // addClassBean.setName(subjectBean.getSubject_name());
            addClassBean.setSubject_id(subjectBean.getSubject_id());
            subjectTxt.setText(subjectBean.getSubject_name());
        } else if (resultCode == 0x14) {

            finish();
        } else if (resultCode == 0x12) {
            priceTxt.setText("现价 " + data.getStringExtra("cp") + "  原价 " + data.getStringExtra("op"));
            addClassBean.setPrice(data.getStringExtra("cp"));
            addClassBean.setOriginal_price(data.getStringExtra("op"));
        } else if (resultCode == 0x13) {
            joinNum.setText("最少 " + data.getStringExtra("min") + "/最多 " + data.getStringExtra("max"));
            addClassBean.setMin_follow(data.getStringExtra("min"));
            addClassBean.setMax_follow(data.getStringExtra("max"));
        } else if (resultCode == RESULT_OK && (requestCode == SelectPicDialog.SELECT_PIC_BY_TACK_PHOTO ||
                requestCode == SelectPicDialog.SELECT_PIC_BY_PICK_PHOTO)) {

            progressDialog = ProgressDialog.show(this, "", "处理中。。");
            final String picPath = SelectPicDialog.doPhoto(this, requestCode, data);
            Log.i("Upload", "最终选择的图片=" + picPath);
            if (picPath == null) {
                ToastUtils.displayTextShort(this, "获取图片失败");
                return;
            }
            final Bitmap newResizeBmp = ImageUtil.getSmallBitmap(picPath);
            if (newResizeBmp == null || newResizeBmp.isRecycled()) {
                progressDialog.dismiss();
                return;
            }

            new Thread() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    phoneTxtName = "head" + System.currentTimeMillis();
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, VideoCourseBaseInfoEdit.this);
                    if (newResizeBmp != null)
                        newResizeBmp.recycle();
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }else if (requestCode == REQUEST_MEDIA) {
            if (resultCode == RESULT_OK) {
                mMediaSelectedList = MediaPickerActivity
                        .getMediaItemSelected(data);
                if (mMediaSelectedList != null) {
                    for (MediaItem mediaItem : mMediaSelectedList) {
                        uploadVideo(mediaItem);
                    }
                    int num = uploadVideoProgresses.size();
                    listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ImageUtil.dip2px(VideoCourseBaseInfoEdit.this, (hight * num))));
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Error to get media, NULL");
                }
            }
        }
    }

    private void uploadVideo(MediaItem item) {
        UploadVideoProgress uploadVideoProgress =new UploadVideoProgress();
        uploadVideoProgress.setUri(item.getUriOrigin());
        uploadVideoProgresses.add(uploadVideoProgress);

        File file=new File(SelectVideoDialog.getPath(this, item.getUriOrigin()));
        CourseUpdateListener upProgressListener=new CourseUpdateListener(progressListeners.size()) {
            @Override
            public void onRequestProgress(long bytesWrite, long contentLength) {
                if(getPos()<listView.getChildCount()) {
                    View childView=listView.getChildAt(getPos());
                    if(childView!=null) {
                        ProgressBar progressBar = (ProgressBar) childView.getTag();
                        progressBar.setProgress((int) ((100 * bytesWrite) / contentLength));
                    }
//                textView.setText((100 * bytesWrite) / contentLength + "%");
                }
                LogUtil.i(TAG, "pos: "+getPos()+"   pogress:  " +(100 * bytesWrite) / contentLength + "%");
            }

            @Override
            public void onComplete(boolean isSuccess, String result) {
                if(getPos()<listView.getChildCount()) {
                    View childView=listView.getChildAt(getPos());
                    if(childView!=null) {
                        ProgressBar progressBar = (ProgressBar) childView.getTag();
                        progressBar.setProgress(100);
                        LogUtil.i(TAG, "upload complete!! result: " + result);
                    }
                }
            }
        };

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, UploadTask.BUCKET);
        paramsMap.put(Params.SAVE_KEY, "video/"+file.getName());
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        progressListeners.add(upProgressListener);
        UploadManager.getInstance().formUpload(file, paramsMap, UploadTask.TEST_API_KEY, upProgressListener, upProgressListener);
    }

    public void initiHandler() {
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void onSuccess(String method, String jsonMessage) throws JSONException {
                super.onSuccess(method, jsonMessage);
                Intent intent = new Intent();
                intent.putExtra("success", true);
                setResult(0x14, intent);
                finish();
            }

            @Override
            public void onFailure(String method, JsonMessage jsonMessage) {
                super.onFailure(method, jsonMessage);
                //
            }
        });
    }

    public class CourseUpdateListener implements UpCompleteListener, UpProgressListener{

        private int pos=-1;

        public CourseUpdateListener(int pos) {
            this.pos = pos;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        @Override
        public void onComplete(boolean b, String s) {

        }

        @Override
        public void onRequestProgress(long l, long l1) {

        }
    }

}
