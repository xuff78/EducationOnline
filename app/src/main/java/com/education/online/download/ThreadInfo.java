package com.education.online.download;

import java.io.Serializable;

/**
 * Created by 可爱的蘑菇 on 2016/12/4.
 */
public class ThreadInfo implements Serializable {
    private int id;
    private String url;
    private int start;
    private int end;//文件长度
    private int finished;//任务中断时的进度

    public ThreadInfo() {
        super();
    }

    public ThreadInfo(int id, String url, int start, int end, int finished) {
        super();
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo [id=" + id + ", url=" + url + ", start=" + start
                + ", end=" + end + ", finished=" + finished + "]";
    }

}
