package com.education.online.download;

import java.io.Serializable;

/**
 * Created by 可爱的蘑菇 on 2016/12/4.
 */
public class FileInfo implements Serializable {

    private int id;
    private String url;
    private String fileName;
    private int length;
    private int finished;
    private String name="";
    private int status=0; //0 没选择， 1已选择， 2已下载或下载中

    public FileInfo() {

    }

    public FileInfo(int id, String url, String fileName, int length,
                    int finished) {
        super();
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo [id=" + id + ", url=" + url + ", fileName=" + fileName
                + ", length=" + length + ", finished=" + finished + "]";
    }

}
