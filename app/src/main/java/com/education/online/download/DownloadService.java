package com.education.online.download;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 可爱的蘑菇 on 2016/12/4.
 */
public class DownloadService extends Service implements DownloadTask.FinishCallback{
    private final String tag = getClass().getName();
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()+"/mDownLoads/";
    public static final int MSG_INIT = 0;
    private DownloadBinder mBinder = new DownloadBinder();

    private Map<String, DownloadTask> downloadTaskMap=new HashMap<>();
//    private Map<String, Handler> handlerMap=new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return mBinder;
    }

    @Override
    public void onfinsh(ThreadInfo info) {
        downloadTaskMap.remove(info.getUrl());
    }


    public class DownloadBinder extends Binder {

        public void startDownload(ThreadInfo fileInfo, Handler handler) {
            Log.d("TAG", "startDownload() executed");
            Log.i(tag,"Start+"+fileInfo.toString());

            DownloadTask task = new DownloadTask(DownloadService.this,fileInfo, DownloadService.this);
            downloadTaskMap.put(fileInfo.getUrl(), task);
            task.setHandler(handler);
            task.download();
//            handlerMap.put(fileInfo.getUrl(), handler);
//            new InitThread(fileInfo, mHandler).start();
        }

        public void pauseDownload(ThreadInfo fileInfo){
            Log.i(tag,"Stop+"+fileInfo.toString());
            DownloadTask task=downloadTaskMap.get(fileInfo.getUrl());
            if(task!=null){
                task.setPause(true);
            }
            downloadTaskMap.remove(fileInfo.getUrl());
        }

        public boolean setCallbackHandker(String url, Handler handler) {
            if(downloadTaskMap.containsKey(url)){
                DownloadTask task=downloadTaskMap.get(url);
                task.setHandler(handler);
                return true;
            }else
                return false;
        }

        public void removeDownload(ThreadInfo fileInfo) {
            Log.i(tag,"Remove+"+fileInfo.toString());
            DownloadTask task=downloadTaskMap.get(fileInfo.getUrl());
            if(task!=null){
                task.setPause(true);
            }
            downloadTaskMap.remove(fileInfo.getUrl());
        }
    }

//    Handler mHandler = new Handler(){
//        public void handleMessage(Message msg){
//            switch(msg.what){
//                case MSG_INIT:
//                    ThreadInfo fileInfo = (ThreadInfo)msg.obj;
//                    Log.i(tag,"INIT"+fileInfo.toString());
//                    //启动下载任务
//                    DownloadTask task = new DownloadTask(DownloadService.this,fileInfo, DownloadService.this);
//                    downloadTaskMap.put(fileInfo.getUrl(), task);
//                    task.setHandler(handlerMap.get(fileInfo.getUrl()));
//                    handlerMap.remove(fileInfo.getUrl());
//                    task.download();
//                    break;
//            }
//        }
//    };
}