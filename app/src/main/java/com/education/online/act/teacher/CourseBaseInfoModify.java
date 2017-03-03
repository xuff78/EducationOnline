package com.education.online.act.teacher;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.login.SubjectSelector;
import com.education.online.act.upyun.UploadTask;
import com.education.online.adapter.CourseWareEditAdapter;
import com.education.online.adapter.VideoUploadProgressAdapter;
import com.education.online.bean.AddClassBean;
import com.education.online.bean.CourseDetailBean;
import com.education.online.bean.CourseExtm;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.UploadVideoProgress;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.CourseUpdateListener;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.OpenfileUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.util.VideoUtil;
import com.education.online.view.SelectPicDialog;
import com.education.online.view.SelectVideoDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/2.
 */

public class CourseBaseInfoModify extends BaseFrameAct implements View.OnClickListener {


    String phoneTxtName = "";
    private String course="";
    private Dialog progressDialog;
    private TextView submitCourseBtn, uploadBtn, subjectTxt, priceTxt, joinNum, useExample, insertTypeTxt, feedbackTxt;
    private ImageView courseImg;
    private EditText courseName, courseDesc;
    private int type = 0; // 1课件， 2视频, 3直播
    private View uploadLayout, joinNumLayout, feedbackLayout, insertTypeLayout;
    private SelectPicDialog selectPicDialog;
    private AddClassBean addClassBean;
    private CourseDetailBean courseDetail=new CourseDetailBean();
    private HttpHandler httpHandler;
    private CourseWareEditAdapter adapter;
    private ArrayList<UploadVideoProgress> uploadVideoProgresses = new ArrayList<>();
    private List<CourseUpdateListener> progressListeners = new ArrayList<>();
    private ListView listView;
    private final int hight = 51;
    final int getVideoDoc = 0x20;

    public void initiHandler(){
        httpHandler = new HttpHandler(this, new CallBack(this) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if (method.equals(Method.editCourse)) {
                    ToastUtils.displayTextShort(CourseBaseInfoModify.this, "修改成功");
                    setResult(0x22);
                    finish();
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_course_edit);

        _setHeaderTitle("课程编辑");
        initiHandler();
        courseDetail = (CourseDetailBean) getIntent().getSerializableExtra(CourseDetailBean.Name);
        if(courseDetail.getCourse_type()!=null)
            type=Integer.valueOf(courseDetail.getCourse_type());
        _setRightHomeGone();
        initView();
        initData();
    }

    private void initData() {
        addClassBean = new AddClassBean();
        addClassBean.setImg(courseDetail.getImg());
        addClassBean.setMax_follow(courseDetail.getMax_follow());
        addClassBean.setMin_follow(courseDetail.getMin_follow());
        addClassBean.setSubject_id(courseDetail.getSubject_id());
        addClassBean.setIntroduction(courseDetail.getIntroduction());
        addClassBean.setRefund(courseDetail.getRefund());
        addClassBean.setTransfer(courseDetail.getTransfer());
        addClassBean.setName(courseDetail.getCourse_name());

        subjectTxt.setText(courseDetail.getSubject_name());
        courseName.setText(courseDetail.getCourse_name());
        courseDesc.setText(courseDetail.getIntroduction());

        feedbackTxt.setText(ActUtil.getRefundState(courseDetail.getRefund()));
        insertTypeTxt.setText(ActUtil.getInsertState(courseDetail.getTransfer()));
        loader.displayImage(ImageUtil.getImageUrl(courseDetail.getImg()), courseImg);
        priceTxt.setText("现价 " + courseDetail.getPrice() + "  原价 " + courseDetail.getOriginal_price());
        joinNum.setText("最少 " + courseDetail.getMin_follow() + "/最多 " + courseDetail.getMax_follow());

        if(type!=3) {
            List<CourseExtm> courseExtms=courseDetail.getCourse_extm();
            int pos=0;
            for(CourseExtm extm:courseExtms) {
                UploadVideoProgress uploadVideoProgress = new UploadVideoProgress();
                uploadVideoProgress.setUrl(extm.getUrl());
                uploadVideoProgress.setId(extm.getId());
                uploadVideoProgress.setProgress(100);
                uploadVideoProgress.setDescription(extm.getName());
                uploadVideoProgresses.add(uploadVideoProgress);
                CourseUpdateListener upProgressListener = new CourseUpdateListener(pos++);
                progressListeners.add(upProgressListener);
            }

            adapter = new CourseWareEditAdapter(this, this, uploadVideoProgresses);
            int num = uploadVideoProgresses.size();
            listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ImageUtil.dip2px(CourseBaseInfoModify.this, (hight * num))));
            listView.setAdapter(adapter);
        }
    }

    private void initView() {

        subjectTxt = (TextView) findViewById(R.id.subjectTxt);
        priceTxt = (TextView) findViewById(R.id.priceTxt);
        courseImg = (ImageView) findViewById(R.id.courseImg);
        courseName = (EditText) findViewById(R.id.courseName);
        courseDesc = (EditText) findViewById(R.id.courseDesc);
        useExample = (TextView) findViewById(R.id.useExample);
        insertTypeTxt = (TextView) findViewById(R.id.insertTypeTxt);
        feedbackTxt = (TextView) findViewById(R.id.feedbackTxt);
        joinNum = (TextView) findViewById(R.id.joinNum);
        listView = (ListView) findViewById(R.id.uploadview);
        useExample.setOnClickListener(this);
        submitCourseBtn = (TextView) findViewById(R.id.submitCourseBtn);
        submitCourseBtn.setOnClickListener(this);
        uploadBtn = (TextView) findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(this);
        uploadLayout = findViewById(R.id.uploadLayout);
        if(type==3)
            uploadLayout.setVisibility(View.GONE);
        joinNumLayout = findViewById(R.id.joinNumLayout);
        feedbackLayout=findViewById(R.id.feedbackLayout);
        insertTypeLayout=findViewById(R.id.insertTypeLayout);
        joinNumLayout.setOnClickListener(this);
        findViewById(R.id.feedbackLayout).setOnClickListener(this);
        findViewById(R.id.insertTypeLayout).setOnClickListener(this);
        findViewById(R.id.subjectLayout).setOnClickListener(this);
        findViewById(R.id.priceLayout).setVisibility(View.GONE);
        findViewById(R.id.courseImgLayout).setOnClickListener(this);
        selectPicDialog = new SelectPicDialog(this);
        submitCourseBtn.setText("完成编辑");

        if (type == 1) {
            _setHeaderTitle("编辑课件");
        } else if (type == 2) {
            _setHeaderTitle("编辑视频课");
            uploadBtn.setVisibility(View.VISIBLE);
        } else if (type == 3) {
            uploadLayout.setVisibility(View.GONE);
            joinNumLayout.setVisibility(View.VISIBLE);
            insertTypeLayout.setVisibility(View.VISIBLE);
            feedbackLayout.setVisibility(View.VISIBLE);
            _setHeaderTitle("编辑直播课");
        }
    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent();
        switch (view.getId()) {
            case R.id.useExample:
                courseDesc.setText("课程面向【学生群体】，通过【教学方法】，在【课程时间】内，帮助学生达到【教学目标】的教学目标。");
                break;
            case R.id.joinNumLayout:
                i.setClass(CourseBaseInfoModify.this, CourseMemberEdit.class);
                i.putExtra("Max_follow", addClassBean.getMax_follow());
                i.putExtra("Min_follow", addClassBean.getMin_follow());
                startActivityForResult(i, 0x10);
                break;
            case R.id.subjectLayout:
                startActivityForResult(new Intent(CourseBaseInfoModify.this, SubjectSelector.class), 0x10);
                break;
            case R.id.priceLayout:
                i.setClass(CourseBaseInfoModify.this, CoursePriceEdit.class);
                i.putExtra("Price", addClassBean.getPrice());
                i.putExtra("Original_price", addClassBean.getOriginal_price());
                startActivityForResult(i, 0x10);
                break;
            case R.id.courseImgLayout://选择图片层
                selectPicDialog.show();
                break;
            case R.id.submitCourseBtn:
                addClassBean.setIntroduction(courseDesc.getText().toString().trim());
                addClassBean.setName(courseName.getText().toString());
                if (addClassBean.getName().length() == 0 || addClassBean.getIntroduction().length() == 0
                        || addClassBean.getSubject_id().length() == 0 || addClassBean.getImg().length() == 0||
                (type==3&&(addClassBean.getMin_follow().length() == 0 || addClassBean.getMax_follow().length() == 0))) {
                        Toast.makeText(CourseBaseInfoModify.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                } else {

                    JSONArray jsonArray=new JSONArray();
                    boolean iscompeleteupload = false;
                    if(type==3)
                        iscompeleteupload=true;
                    else {
                        for (int j = 0; j < uploadVideoProgresses.size(); j++) {
                            String url = uploadVideoProgresses.get(j).getUrl();
                            if (url.length() == 0) {
                                iscompeleteupload = false;
                                break;
                            }
                            iscompeleteupload = true;
                        }
                        for (int j = 0; j < uploadVideoProgresses.size(); j++) {
                            View childView = listView.getChildAt(j);
                            UploadVideoProgress progress=uploadVideoProgresses.get(j);
                            if (childView != null) {
                                JSONObject object=new JSONObject();
                                EditText editText = (EditText) childView.getTag(R.id.tag_videodescription);
                                String description = editText.getText().toString();
                                if (description.length() == 0)
                                    description = "课件" + (j + 1);

                                String url=progress.getUrl();
                                if (url.length() == 0) {
                                    iscompeleteupload = false;
                                    break;
                                }
                                try {
                                    object.put("name", description);
                                    object.put("url", url);
                                    jsonArray.put(object);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(iscompeleteupload)
                            addClassBean.setCourse_url(jsonArray.toString());
                    }

                    if(type!=3&&uploadVideoProgresses.size()==0) {
                        Toast.makeText(CourseBaseInfoModify.this, "请至少上传一个视频或课件！", Toast.LENGTH_SHORT).show();
                    }if (!iscompeleteupload) {
                        Toast.makeText(CourseBaseInfoModify.this, "请等待文件上传完成！", Toast.LENGTH_SHORT).show();
                    } else {
                        httpHandler.editCourse(addClassBean, courseDetail.getCourse_id());
                    }
                }
                break;
            case R.id.uploadBtn:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                 if(type==1)
                     intent.setType("video/*");
                else
                    intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, getVideoDoc);
                break;
            case R.id.delete:
                int pos = (int) view.getTag();
                uploadVideoProgresses.remove(pos);
                progressListeners.get(pos).setPos(1000);
                for (int k = pos + 1; k < progressListeners.size(); k++) {
                    CourseUpdateListener progress = progressListeners.get(k);
                    progress.setPos(progress.getPos() - 1);
                }
                progressListeners.remove(pos);
                int num = uploadVideoProgresses.size();
                listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ImageUtil.dip2px(CourseBaseInfoModify.this, (hight * num))));
                adapter.notifyDataSetChanged();
                break;


            case R.id.open:
                int pos2 = (int) view.getTag();

                //  Intent intent1 = new Intent(CourseWareBaseInfoEdit.this, VideoPlay.class);
                Uri uri = uploadVideoProgresses.get(pos2).getUri();
                if(uri!=null) {
                    String realFilePath = SelectVideoDialog.getPath(CourseBaseInfoModify.this, uri);
                    Intent intent1 = new Intent();
                    if (realFilePath != null)
                        intent1 = OpenfileUtil.openFile(realFilePath);
                    // intent1.putExtra("Uri", Uri);
                    startActivity(intent1);
                }else{
                    Intent intent1 = new Intent();
                    intent1.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(VideoUtil.getVideoUrl(uploadVideoProgresses.get(pos2).getUrl()));
                    intent1.setData(content_url);
                    startActivity(intent1);
                }

                break;
            case R.id.feedbackLayout:
                DialogUtil.showSelectDialog(this, "请选择退款类型", getResources().getStringArray(R.array.refund_typestr),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                addClassBean.setRefund(String.valueOf(which+1));
                                feedbackTxt.setText(ActUtil.getRefundState(addClassBean.getRefund()));
                            }
                        });
                break;
            case R.id.insertTypeLayout:
                DialogUtil.showSelectDialog(this, "选择插班类型", getResources().getStringArray(R.array.insert_typestr),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                addClassBean.setTransfer(String.valueOf(which+1));
                                insertTypeTxt.setText(ActUtil.getInsertState(addClassBean.getTransfer()));
                            }
                        });
                break;
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            File file = FileUtil.getFile(phoneTxtName + ".png", CourseBaseInfoModify.this);
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        course = result.substring(1);
                        LogUtil.d("Img", course);
                        // imageloader.displayImage(ImageUtil.getImageUrl(course), courseImg);
                        Toast.makeText(CourseBaseInfoModify.this,"图片上传成功！",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(CourseBaseInfoModify.this, "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "course/"+phoneTxtName + ".png");
            }
            Bitmap photo = BitmapFactory.decodeFile(file.toString());
            if (photo != null) {
                courseImg.setImageBitmap(photo);
                addClassBean.setImg("course/"+phoneTxtName + ".png");

            } else
                ToastUtils.displayTextShort(CourseBaseInfoModify.this, "找不到文件");
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
        }else if (resultCode==0x14){

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
                progressDialog.dismiss();
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
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, CourseBaseInfoModify.this);
                    if (newResizeBmp != null)
                        newResizeBmp.recycle();
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }  else if (requestCode == getVideoDoc) {
            if  (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                uploadVideo(uri);
                int num = uploadVideoProgresses.size();
                listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ImageUtil.dip2px(CourseBaseInfoModify.this, (hight * num))));
                adapter.notifyDataSetChanged();
            } else {
//                Log.e(TAG, "Error to get media, NULL");
            }
        }
    }

    private void uploadVideo(Uri uri) {
        final UploadVideoProgress uploadVideoProgress = new UploadVideoProgress();
        uploadVideoProgress.setUri(uri);
        uploadVideoProgresses.add(uploadVideoProgress);

        File file = new File(SelectVideoDialog.getPath(this, uri));

        CourseUpdateListener upProgressListener = new CourseUpdateListener(progressListeners.size()) {
            @Override
            public void onRequestProgress(long bytesWrite, long contentLength) {
                if (getPos() < listView.getChildCount()) {
                    View childView = listView.getChildAt(getPos());
                    if (childView != null) {
                        ProgressBar progressBar = (ProgressBar) childView.getTag(R.id.tag_progress_value1);
                        progressBar.setProgress((int) ((100 * bytesWrite) / contentLength));
                        uploadVideoProgresses.get(getPos()).setProgress((int) ((100 * bytesWrite) / contentLength));
                    }
//                textView.setText((100 * bytesWrite) / contentLength + "%");
                }
//                LogUtil.i(TAG, "pos: " + getPos() + "   pogress:  " + (100 * bytesWrite) / contentLength + "%");
            }

            @Override
            public void onComplete(boolean isSuccess, String result) {
                if (getPos() < listView.getChildCount()) {
                    View childView = listView.getChildAt(getPos());
                    if (childView != null) {
                        ProgressBar progressBar = (ProgressBar) childView.getTag(R.id.tag_progress_value1);
                        progressBar.setProgress(100);
//                        LogUtil.i(TAG, "upload complete!! result: " + result);
                        String url = "";
                        try {
                            JSONObject json = new JSONObject(result);
                            if (!json.isNull("url"))
                                url = json.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        uploadVideoProgress.setUrl(url);
                        //在这里解析result把视频url设置好吧//done!
                    }
                }
            }
        };

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, UploadTask.BUCKET);
        paramsMap.put(Params.SAVE_KEY, "courseware/" + file.getName());
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        progressListeners.add(upProgressListener);
        UploadManager.getInstance().formUpload(file, paramsMap, UploadTask.TEST_API_KEY, upProgressListener, upProgressListener);
    }
}
