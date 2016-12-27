package com.education.online.fragment.teacher;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.education.online.R;
import com.education.online.act.VideoPlay;
import com.education.online.act.upyun.UploadTask;
import com.education.online.adapter.TeacherImgAdapter;
import com.education.online.bean.VideoImgItem;
import com.education.online.fragment.BaseFragment;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;
import com.education.online.view.SelectVideoDialog;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vn.tungdx.mediapicker.MediaOptions;
import vn.tungdx.mediapicker.utils.MediaUtils;

/**
 * Created by Administrator on 2016/8/30.
 */
public class HomepageVideo extends BaseFragment {

    private RecyclerView teacherList;
    private ArrayList<VideoImgItem> items=new ArrayList<>();
    private TeacherImgAdapter adapter;
    private boolean edit=false;
    private HttpHandler mHandler;
    private String imgpath="";
    private int tobeDelPos=-1;
    private String dispose_type="add"; //1添加，0删除
    private MediaOptions mMediaOptions ;
    private AdapterCallback callback=new AdapterCallback() {
        @Override
        public void onClick(View v, int i) {
            Intent intent=new Intent(getActivity(), VideoPlay.class);
            intent.putExtra("Url", ImageUtil.getImageUrl(items.get(i).getVideoUrl()));
            startActivity(intent);

//            Uri uri = Uri.parse(ImageUtil.getImageUrl(items.get(i).getVideoUrl()));
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(uri, "video/*");
//            startActivity(intent);
        }

        @Override
        public void additem() {
            new SelectVideoDialog(getActivity()).show();
        }

        @Override
        public void delitem(View v, int i) {
            dispose_type="delete";
            tobeDelPos=i;
            mHandler.dispose(items.get(i).getVideoUrl(), "video", dispose_type);
        }
    };

    private void initHandler() {
        mHandler = new HttpHandler(getActivity(), new CallBack(getActivity()) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.dispose)){
                    if(dispose_type.equals("add")){
                        VideoImgItem item=new VideoImgItem();
                        item.setVideoUrl(imgpath);
                        items.add(item);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(),"图片上传成功！",Toast.LENGTH_SHORT);
                    }else if(dispose_type.equals("delete")){
                        items.remove(tobeDelPos);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);


        initView(view);
        initHandler();
        int padding= ImageUtil.dip2px(getActivity(), 10);
        view.setPadding(padding*2,0,padding,0);
        edit=false;
        return view;
    }

    public void setEdit(){
        edit=!edit;
        adapter.setEdit(edit);
    }


    private void initView(View v) {
        teacherList = (RecyclerView) v.findViewById(R.id.recyclerList);
        GridLayoutManager layoutManager = new GridLayoutManager (getActivity(),4);
        teacherList.setLayoutManager(layoutManager);
        adapter=new TeacherImgAdapter(getActivity(), items, true, callback);
        teacherList.setAdapter(adapter);
        MediaOptions.Builder builder = new MediaOptions.Builder();
        mMediaOptions = builder.selectVideo().canSelectMultiVideo(false).build();
    }

    private Dialog progressDialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && (requestCode == SelectVideoDialog.SELECT_VIDEO_BY_TACK_PHOTO ||
                requestCode == SelectVideoDialog.SELECT_VIDEO_BY_PICK_PHOTO)) {

            progressDialog = ProgressDialog.show(getActivity(), "", "处理中。。");
            final int code = checkValidVideo(data.getData());
            if(code!=1) {
                ToastUtils.displayTextShort(getActivity(), "获取视频失败");
                return;
            }
            final String picPath = SelectVideoDialog.getPath(getActivity(), data.getData());//SelectVideoDialog.doPhoto(getActivity(), requestCode, data);
            Log.i("Upload", "最终选择的视频: " + picPath);
            if (picPath == null) {
                ToastUtils.displayTextShort(getActivity(), "获取视频失败");
                return;
            }

            progressDialog.show();
            int last=picPath.lastIndexOf("/");
            String phoneTxtName = picPath.substring(last+1);
            File file = new File(picPath);
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        imgpath=result.substring(1);
                        dispose_type="add";
                        mHandler.dispose(imgpath, "video", dispose_type);
                        LogUtil.d("Video", imgpath);
                        // imageloader.displayImage(ImageUtil.getImageUrl(course), courseImg);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(getActivity(), "提示", "视频上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "video/"+phoneTxtName);
            }else{
                progressDialog.dismiss();
                ToastUtils.displayTextShort(getActivity(), "获取视频失败");
            }


//            new Thread() {
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    super.run();
//                    phoneTxtName = "head" + System.currentTimeMillis();
//                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, getActivity());
//                    if (newResizeBmp != null)
//                        newResizeBmp.recycle();
//                    handler.sendEmptyMessage(0);
//                }
//            }.start();
        }
    }

    public void setData(List<String> data) {
        for(int i=0;i<data.size();i++){
            VideoImgItem item=new VideoImgItem();
            item.setVideoUrl(data.get(i));
            items.add(item);
        }
        if(adapter!=null)
            adapter.notifyDataSetChanged();
    }

    private int checkValidVideo(Uri videoUri) {
        if (videoUri == null)
            return -2;
        long duration = MediaUtils.getDuration(getActivity(),
                MediaUtils.getRealVideoPathFromURI(getActivity().getContentResolver(), videoUri));
        if (duration == 0) {
            duration = MediaUtils
                    .getDuration(getActivity(), videoUri);
        }
        if (mMediaOptions.getMaxVideoDuration() != Integer.MAX_VALUE
                && duration >= mMediaOptions.getMaxVideoDuration() + 1000) {
            return 0;
        } else if (duration == 0
                || duration < mMediaOptions.getMinVideoDuration()) {
            return -1;
        }
        return 1;
    }

}
