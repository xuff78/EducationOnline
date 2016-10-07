package com.education.online.act.ucloud.task;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.education.online.act.ucloud.UFileRequest;

/**
 * Created by jerry on 15/12/14.
 */
public class WriteAsyncTask extends HttpAsyncTask {

    private final static String TAG = WriteAsyncTask.class.getSimpleName();

    private String upload;

    public WriteAsyncTask(String url, UFileRequest uFileRequest, HttpCallback callback, String upload) {
        super(url, uFileRequest, callback);
        this.upload = upload;
    }

    @Override
    protected void onWrite(OutputStream outputStream) throws Exception {
        Log.i(TAG, "onWrite length=" + upload.length() + " " + upload);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(upload);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

}
