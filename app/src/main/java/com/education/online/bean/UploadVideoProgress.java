package com.education.online.bean;

import android.net.Uri;

/**
 * Created by Great Gao on 2016/11/3.
 */
public class UploadVideoProgress {
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String progress="";
    private Uri uri = getUri();
    private String description;

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
