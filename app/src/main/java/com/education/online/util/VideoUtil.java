package com.education.online.util;

import com.education.online.act.upyun.UploadTask;

/**
 * Created by Great Gao on 2016/11/10.
 */
public class VideoUtil {

    public static String getVideoUrl(String video) {
        return UploadTask.UPLOAD_URL+"/"+video;
    }
}
