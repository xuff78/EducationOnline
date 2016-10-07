package com.education.online.act.ucloud.task;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import com.education.online.act.ucloud.UFileRequest;

/**
 * Created by jerry on 15/12/14.
 */
public class PutFileAsyncTask extends HttpAsyncTask {

    private final static String TAG = PutFileAsyncTask.class.getSimpleName();

    private File file;

    public PutFileAsyncTask(String url, UFileRequest uFileRequest, File file, HttpCallback callback) {
        super(url, uFileRequest, callback);
        this.file = file;
    }

    @Override
    protected void onWrite(OutputStream outputStream) throws Exception {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len;
            long total = 0;
            while ((len = fileInputStream.read(bytes)) != -1 && !isCancelled()) {
                total += len;
                dataOutputStream.write(bytes, 0, len);
                publishProgress(WRITE, total);
            }
        } finally {
            if (fileInputStream != null)
                fileInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();
        }
    }
}
