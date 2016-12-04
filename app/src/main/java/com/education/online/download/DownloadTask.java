package com.education.online.download;

import android.content.Context;
import android.content.Intent;

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
    private FileInfo mFileInfo = null;
    private ThreadDAO mDao = null;
    private int mFinished = 0;
    private boolean isPause = false;
    public DownloadTask(Context mContext, FileInfo mFileInfo) {
        super();
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        mDao = new ThreadDAOImpl(mContext);
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }

    public void download(){
        //读取数据库线程信息
        List<ThreadInfo> threadInfos = mDao.getThreads(mFileInfo.getUrl());
        ThreadInfo threadInfo = null;
        if(threadInfos.size()==0){
            //初始化线程信息对象
            threadInfo = new ThreadInfo(0,mFileInfo.getUrl(),0,mFileInfo.getLength(),0);
        }else{
            threadInfo = threadInfos.get(0);
        }
        //创建子线程下载
        new DownloadThread(threadInfo).start();
    }
    class DownloadThread extends Thread{
        private ThreadInfo mThreadInfo = null;

        public DownloadThread(ThreadInfo mThreadInfo) {
            super();
            this.mThreadInfo = mThreadInfo;
        }
        public void run(){
            //数据库插入线程信息
            if(!mDao.isExtists(mThreadInfo.getUrl(),mThreadInfo.getId())){
                mDao.insertThread(mThreadInfo);
            }

            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = mThreadInfo.getStart()+mThreadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes="+start+"-"+mThreadInfo.getEnd());
                //写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH,mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.seek(start);
                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                mFinished+=mThreadInfo.getFinished();
                //开始下载,返回值206
                if(conn.getResponseCode()== HttpStatus.SC_PARTIAL_CONTENT){
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
                        if(System.currentTimeMillis()-time>100){
                            time = System.currentTimeMillis();
                            intent.putExtra("finished", mFinished*100/mFileInfo.getLength());
                            mContext.sendBroadcast(intent);
                        }
                        //下载暂停时，保存下载进度
                        if(isPause){
                            mDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mFinished);
                            return;
                        }
                    }
                    //删除数据库中的线程信息
                    mDao.deleteThread(mThreadInfo.getUrl(), mThreadInfo.getId());
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{

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
}
