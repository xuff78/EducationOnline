package com.education.online.download;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.education.online.util.Constant;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 可爱的蘑菇 on 2016/12/4.
 */
public class DownloadTask {
    private Context mContext = null;
    private ThreadInfo mFileInfo = null;
    private ThreadDAOImpl mDao = null;
    private int mFinished = 0;
    private boolean isPause = false;
    private Handler handler;

    public DownloadTask(Context mContext, ThreadInfo mFileInfo, FinishCallback fcb) {
        super();
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        mDao = new ThreadDAOImpl(mContext);
        this.fcb=fcb;
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    public void download(){
        //读取数据库线程信息
        List<ThreadInfo> threadInfos = mDao.getThreadsByUrl(mFileInfo.getUrl());
        boolean insert=false;
        if(threadInfos.size()==0){
            //初始化线程信息对象
            insert=true;
        }else{
            mFileInfo = threadInfos.get(0);
        }
        //创建子线程下载
        new DownloadThread(mFileInfo, insert).start();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    class DownloadThread extends Thread{
        private ThreadInfo mThreadInfo = null;
        boolean insert=false;
        public DownloadThread(ThreadInfo mThreadInfo, boolean insert) {
            super();
            this.insert=insert;
            this.mThreadInfo = mThreadInfo;
        }
        public void run(){


            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");

                int start = 0;
                if(insert){
                    int  length = conn.getContentLength();
                    if (length<0){
                        return;
                    }
                    mThreadInfo.setStart(0);
                    mThreadInfo.setEnd(length);
                    mDao.insertThread(mThreadInfo);
                    File dir = new File(DownloadService.DOWNLOAD_PATH);
                    if(!dir.exists()){
                        dir.mkdir();
                    }
                }else if(handler!=null){
                    handler.sendEmptyMessage(Constant.resumeDownload);
                    //设置下载位置
                    start = mThreadInfo.getStart()+mThreadInfo.getFinished();
                    conn.setRequestProperty("Range", "bytes="+start+"-"+mThreadInfo.getEnd());
                }

                //写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH,mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.seek(start);
                mFinished+=mThreadInfo.getFinished();
                //开始下载,返回值206
                if(conn.getResponseCode()== HttpStatus.SC_PARTIAL_CONTENT||conn.getResponseCode()== HttpStatus.SC_OK){
                    //读取数据
                    input = conn.getInputStream();
                    byte[] bytes = new byte[1024];
                    int len = -1;
                    long time = System.currentTimeMillis();//减慢进度条刷新时间,减少ui负载
                    while((len = input.read(bytes))!=-1){
                        //写入文件
                        raf.write(bytes,0,len);
                        //把下载进度发送广播给Activity
                        mFinished += len;
                        if(System.currentTimeMillis()-time>200){
                            time = System.currentTimeMillis();
                            Message msg=new Message();
                            Bundle bundle=new Bundle();
                            bundle.putLong("finished",mFinished);
                            msg.setData(bundle);
                            msg.what=Constant.updateDownload;
                            if(handler!=null)
                                handler.sendMessage(msg);
                        }
                        //下载暂停时，保存下载进度
                        if(isPause){
                            mDao.updateThread(mThreadInfo.getUrl(), mFinished);
                            if(handler!=null)
                                handler.sendEmptyMessage(Constant.pouseDownload);
                            return;
                        }
                    }
                    //删除数据库中的线程信息
                    mDao.updateThreadComplete(mThreadInfo.getUrl(), 1);
                    if(handler!=null)
                        handler.sendEmptyMessage(Constant.finishDownload);
                }

            } catch (Exception e) {
                if(handler!=null)
                    handler.sendEmptyMessage(Constant.errorDownload);
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                fcb.onfinsh(mThreadInfo);
                try {conn.disconnect();
                    input.close();
                    raf.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        }
    }

    FinishCallback fcb;

    public interface FinishCallback{
        void onfinsh(ThreadInfo info);
    }
}
