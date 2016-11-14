package com.education.online.fragment.teacher;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.act.ViewerActivity;
import com.education.online.act.upyun.UploadTask;
import com.education.online.adapter.TeacherImgAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.bean.VideoImgItem;
import com.education.online.fragment.BaseFragment;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.DialogUtil;
import com.education.online.util.FileUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.SelectPicDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public class HomepageImg extends BaseFragment {

    private RecyclerView teacherList;
    private ArrayList<VideoImgItem> items=new ArrayList<>();
    private TeacherImgAdapter adapter;
    private boolean edit=false;
    private ImageLoader imageLoader;
    private HttpHandler mHandler;
    private String imgpath="";
    private int tobeDelPos=-1;
    private String dispose_type="add"; //1添加，0删除
    private AdapterCallback callback=new AdapterCallback() {
        @Override
        public void onClick(View v, int pos) {
            Intent i=new Intent(getActivity(), ViewerActivity.class);
            ArrayList<String> images=new ArrayList<>();
            for(VideoImgItem item:items){
                images.add(item.getImgUrl());
            }
            i.putStringArrayListExtra("Images", images);
            i.putExtra("pos", pos);
            ActUtil.startAnimActivity(getActivity(), i, v, "imgbig");
        }

        @Override
        public void additem() {
            new SelectPicDialog(getActivity()).show();
        }

        @Override
        public void delitem(View v, int i) {
            dispose_type="delete";
            tobeDelPos=i;
            mHandler.dispose(items.get(i).getImgUrl(), "photo", dispose_type);
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
                        item.setImgUrl(imgpath);
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

        imageLoader= ImageLoader.getInstance();
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
        adapter=new TeacherImgAdapter(getActivity(), items, false, callback);
        teacherList.setAdapter(adapter);
    }

    private Dialog progressDialog;
    String phoneTxtName = "";
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            File file = FileUtil.getFile(phoneTxtName + ".png", getActivity());
            progressDialog.dismiss();
            if (file.exists()) {
                new UploadTask(new UploadTask.UploadCallBack() {

                    @Override
                    public void onSuccess(String result) {
                        progressDialog.dismiss();
                        imgpath=result.substring(1);
                        dispose_type="add";
                        mHandler.dispose(imgpath, "photo", dispose_type);
                        LogUtil.d("Img", imgpath);
                        // imageloader.displayImage(ImageUtil.getImageUrl(course), courseImg);
                    }

                    @Override
                    public void onFailed() {
                        DialogUtil.showInfoDailog(getActivity(), "提示", "图片上传失败!");
                        progressDialog.dismiss();
                        // mFaceImagePath.delete();
                    }
                }).execute(file, "avatar/"+phoneTxtName + ".png");
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && (requestCode == SelectPicDialog.SELECT_PIC_BY_TACK_PHOTO ||
                requestCode == SelectPicDialog.SELECT_PIC_BY_PICK_PHOTO)) {

            progressDialog = ProgressDialog.show(getActivity(), "", "处理中。。");
            final String picPath = SelectPicDialog.doPhoto(getActivity(), requestCode, data);
            Log.i("Upload", "最终选择的图片=" + picPath);
            if (picPath == null) {
                ToastUtils.displayTextShort(getActivity(), "获取图片失败");
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
                    FileUtil.saveBitmap(newResizeBmp, phoneTxtName, getActivity());
                    if (newResizeBmp != null)
                        newResizeBmp.recycle();
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
    }

    public void setData(List<String> data) {
        for(int i=0;i<data.size();i++){
            VideoImgItem item=new VideoImgItem();
            item.setImgUrl(data.get(i));
            items.add(item);
        }
        if(adapter!=null)
            adapter.notifyDataSetChanged();
    }
}
