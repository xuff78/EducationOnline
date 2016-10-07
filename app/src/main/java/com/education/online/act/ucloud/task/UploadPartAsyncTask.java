package com.education.online.act.ucloud.task;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import com.education.online.act.ucloud.UFileRequest;

/**
 * Created by jerry on 15/12/14.
 */
public class UploadPartAsyncTask extends HttpAsyncTask {

    private final static String TAG = UploadPartAsyncTask.class.getSimpleName();

    private int partNumber;
    private long blk_size;
    private File file;

    public UploadPartAsyncTask(String url, UFileRequest uFileRequest, HttpCallback callback, File file, int partNumber, long blk_size) {
        super(url, uFileRequest, callback);
        this.partNumber = partNumber;
        this.blk_size = blk_size;
        this.file = file;
    }

    @Override
    protected void onWrite(OutputStream outputStream) throws Exception {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        long start = partNumber * blk_size;
        long write = 0;

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        randomAccessFile.seek(start);

        byte[] bytes = new byte[1024];
        int len;
        long size = blk_size;
        while (size > 0 && !isCancelled()) {
            int byteCount = (size > 1024) ? 1024 : (int)size;
            if ((len = randomAccessFile.read(bytes, 0, byteCount)) != -1) {
                write += len;
                dataOutputStream.write(bytes, 0, len);
                size = size - len;
                publishProgress(WRITE, write);
            } else {
                break;
            }
        }

        Log.i(TAG, "write part " + partNumber + " from " + start + " to " + (start + write) + " write " + write + " " + (blk_size == write));
        dataOutputStream.flush();
        dataOutputStream.close();
    }
}
