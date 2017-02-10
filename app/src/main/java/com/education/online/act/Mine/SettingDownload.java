package com.education.online.act.Mine;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.act.VideoMainPage;
import com.education.online.download.DownloadService;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.FileUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/9.
 */

public class SettingDownload extends BaseFrameAct {

    @Bind(R.id.videoSize)TextView videoSize;
    @Bind(R.id.imgSize)TextView imgSize;
    @Bind(R.id.warnCheckbox)AppCompatCheckBox warnCheckbox;
    private Subscription subscription, loadFileSize;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_download);
        ButterKnife.bind(this);
        _setHeaderTitle("下载设置");

        if(SharedPreferencesUtil.getValue(this, Constant.WIFIWarning, true))
            warnCheckbox.setChecked(true);
        initData();
    }

    private void initData() {
        Observable<Long> sizeVideo=Observable.create(new Observable.OnSubscribe<Long>() {

            @Override
            public void call(Subscriber<? super Long> subscriber) {
                long size=0;
                File dec=new File(DownloadService.DOWNLOAD_PATH);
                if(dec.exists())
                    size=FileUtil.getFileSizes(dec);
                subscriber.onNext(size);
            }
        });
        Observable<Long> sizeImage=Observable.create(new Observable.OnSubscribe<Long>() {

            @Override
            public void call(Subscriber<? super Long> subscriber) {
                long size1=0, size2=0;
                File dec1=loader.getDiskCache().getDirectory();
                File dec2=new File(Constant.SavePath);
                if(dec1!=null)
                    size1=FileUtil.getFileSizes(dec1);
                if(dec2.exists())
                    size2=FileUtil.getFileSizes(dec2);
                subscriber.onNext(size1+size2);
            }
        });
        loadFileSize=Observable.zip(sizeVideo, sizeImage, new Func2<Long, Long, List<String>>(){

                    @Override
                    public List<String> call(Long aLong, Long aLong2) {
                        List<String> list=new ArrayList<>();
                        list.add(ActUtil.twoDecimal(Double.valueOf(aLong)/(1024*1024))+"MB");
                        list.add(ActUtil.twoDecimal(Double.valueOf(aLong2)/(1024*1024))+"MB");
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> s) {
                        videoSize.setText(s.get(0));
                        imgSize.setText(s.get(1));
                    }
                });
    }

    @OnCheckedChanged(R.id.warnCheckbox)
    public void onCheckChange(boolean check){
        if(check)
            SharedPreferencesUtil.setValue(this, Constant.WIFIWarning, true);
        else
            SharedPreferencesUtil.setValue(this, Constant.WIFIWarning, false);

    }

    @OnClick(R.id.videoDelBtn)
    public void delVideo() {
        subscription=Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        FileUtil.deleteDirectory(DownloadService.DOWNLOAD_PATH);
                        subscriber.onNext("下载已清除");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void onDestroy() {
        cancelTask();
        if(!loadFileSize.isUnsubscribed())
            loadFileSize.unsubscribe();
        super.onDestroy();
    }

    @OnClick(R.id.imgDelBtn)
    public void delImage() {
        subscription=Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        loader.clearDiskCache();
                        FileUtil.deleteDirectory(Constant.SavePath);
                        subscriber.onNext("图片已清除");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    private void cancelTask() {
        if(subscription!=null&&!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    Subscriber observer=new Subscriber<String>() {

        @Override
        public void onStart() {
            progressDialog = ProgressDialog.show(SettingDownload.this, "", "处理中..", true, true);
            progressDialog.setOnCancelListener(cancelListener);
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            progressDialog.dismiss();
            ToastUtils.displayTextLong(SettingDownload.this, "清除失败");
        }

        @Override
        public void onNext(String hint) {
            progressDialog.dismiss();
            initData();
            ToastUtils.displayTextLong(SettingDownload.this, hint);
        }
    };

    DialogInterface.OnCancelListener cancelListener= new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            cancelTask();
        }
    };
}
