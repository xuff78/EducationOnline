package com.education.online.act.ucloud.task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.education.online.act.ucloud.UFileRequest;
import com.education.online.act.ucloud.UFileSDK;
import com.education.online.act.ucloud.UFileUtils;

/**
 * Created by jerry on 15/12/14.
 */
public class HttpAsyncTask extends AsyncTask<Object, Object, Object> {
    private final static String TAG = HttpAsyncTask.class.getSimpleName();
    public final static String READ = "read";
    public final static String WRITE = "write";
    private String url;
    private UFileRequest uFileRequest;
    private HttpCallback callback;

    public HttpAsyncTask(String url, UFileRequest uFileRequest, HttpCallback callback) {
        this.url = url;
        this.uFileRequest = uFileRequest;
        this.callback = callback;
    }

    public boolean isRunning() {
        return this.getStatus() == Status.RUNNING;
    }

    public void cancel() {
        Log.i(TAG, "user cancel" + this.getStatus());
        this.cancel(false);
    }

    @Override
    protected Object doInBackground(Object... params) {
        HttpURLConnection conn = null;
        JSONObject response = new JSONObject();
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setRequestProperty("UserAgent", "UFile Android/" + UFileSDK.VERSION_NAME);

            conn.setRequestProperty("Content-Type", ""); 
            if (uFileRequest.getAuthorization() != null)
                conn.setRequestProperty("Authorization", uFileRequest.getAuthorization());
            if (uFileRequest.getContentMD5() != null)
                conn.setRequestProperty("Content-MD5", uFileRequest.getContentMD5());
            if (uFileRequest.getContentType() != null)
                conn.setRequestProperty("Content-Type", uFileRequest.getContentType());
            else conn.setRequestProperty("Content-Type", "");
            String method = uFileRequest.getHttpMethod();
            conn.setRequestMethod(method);
            if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("DELETE")) {
                conn.setDoInput(true);
                conn.setDoOutput(true);
                onWrite(conn.getOutputStream());
            }
            int httpCode = conn.getResponseCode();
            response.put("httpCode", httpCode);
            Map<String, List<String>> responseHeaders = conn.getHeaderFields();
            JSONObject response_headers = UFileUtils.passHeaders(responseHeaders);
            response.put("headers", response_headers);
            InputStream is;
            if (httpCode == HttpURLConnection.HTTP_OK || httpCode == HttpURLConnection.HTTP_NO_CONTENT) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }
            int response_length = conn.getContentLength();
            if (response_length > 0) {
                onRead(is, response);
            }
            Log.i(TAG, "response " + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    protected void onWrite(OutputStream outputStream) throws Exception {
    }

    protected void onRead(InputStream inputStream, JSONObject response) throws Exception {
        BufferedInputStream stream = new BufferedInputStream(inputStream);
        StringBuilder answer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null && !isCancelled()) {
            answer.append(line);
        }
        reader.close();
        String as = answer.toString();
        if (!as.isEmpty()) {
            if ("application/json".equals(response.getJSONObject("headers").getString("Content-Type"))) {
                JSONObject body = new JSONObject(as);
                response.put("body", body);
            } else {
                response.put("body", as);
            }
        } else {
            Log.e(TAG, "read null!!!");
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", "user cancel, response is null");
            callback.onPostExecute(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Object... progress) {
        super.onProgressUpdate(progress);
        callback.onProgressUpdate(progress);
    }

    @Override
    protected void onPostExecute(Object response) {
        super.onPostExecute(response);
        if (response == null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "http async task on post execute, response is null");
                callback.onPostExecute(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            callback.onPostExecute((JSONObject) response);
        }
    }

    public interface HttpCallback {
        //UI thread
        void onProgressUpdate(Object... progress);

        //UI thread
        void onPostExecute(JSONObject response);
    }
}
