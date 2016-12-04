package com.education.online.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 可爱的蘑菇 on 2016/12/4.
 */
public class DownloadService extends Service {
    private final String tag = getClass().getName();
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()+"/mDownLoads/";
    public static final int MSG_INIT = 0;

    private DownloadTask task = null;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获得Activity传来的参数
        if(ACTION_START.equals(intent.getAction())){
            FileInfo fileInfo = (FileInfo)intent.getSerializableExtra("fileInfo");
            Log.i(tag,"Start+"+fileInfo.toString());
            new InitThread(fileInfo).start();
        }else if(ACTION_STOP.equals(intent.getAction())){
            FileInfo fileInfo = (FileInfo)intent.getSerializableExtra("fileInfo");
            Log.i(tag,"Stop+"+fileInfo.toString());
            if(task!=null){
                task.setPause(true);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo)msg.obj;
                    Log.i(tag,"INIT"+fileInfo.toString());
                    //启动下载任务
                    task = new DownloadTask(DownloadService.this,fileInfo);
                    task.download();
                    break;
            }
        }
    };

    /**
     * 初始化子线程
     */
    class InitThread extends Thread{
        private FileInfo fileInfo;

        public InitThread(FileInfo fileInfo) {
            super();
            this.fileInfo = fileInfo;
        }
        public void run(){
            HttpURLConnection coon = null;
            RandomAccessFile raf = null;
            try{
                //连接网络文件
                URL url = new URL(fileInfo.getUrl());
                coon = (HttpURLConnection)url.openConnection();
                coon.setConnectTimeout(3000);
                coon.setRequestMethod("GET");
                int length = -1;
                if (coon.getResponseCode()== HttpStatus.SC_OK){
                    //获取文件长度
                    length = coon.getContentLength();
                }
                if (length<0){
                    return;
                }


                File dir = new File(DOWNLOAD_PATH);
                if(!dir.exists()){
                    dir.mkdir();
                }
                //本地创建文件
                File file = new File(dir,fileInfo.getFileName());
                raf = new  RandomAccessFile(file,"rwd");
                //设置文件长度
                raf.setLength(length);
                fileInfo.setLength(length);
                //利用handler将信息从线程中回传给service
                mHandler.obtainMessage(MSG_INIT,fileInfo).sendToTarget();
            }catch(Exception e){
                e.printStackTrace();
            }finally{

                try {
                    coon.disconnect();
                    raf.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}