package com.education.online.download;

/**
 * Created by Administrator on 2016/12/7.
 */

import android.os.Handler;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 初始化子线程
 */
class InitThread extends Thread{
    private ThreadInfo fileInfo;
    private Handler mHandler;

    public InitThread(ThreadInfo fileInfo, Handler mHandler ) {
        super();
        this.fileInfo = fileInfo;
        this.mHandler=mHandler;
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


            File dir = new File(DownloadService.DOWNLOAD_PATH);
            if(!dir.exists()){
                dir.mkdir();
            }
            //本地创建文件
            File file = new File(dir,fileInfo.getFileName());
            raf = new  RandomAccessFile(file,"rwd");
            //设置文件长度
            raf.setLength(length);
            fileInfo.setEnd(length);
            //利用handler将信息从线程中回传给service
            mHandler.obtainMessage(DownloadService.MSG_INIT,fileInfo).sendToTarget();
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
