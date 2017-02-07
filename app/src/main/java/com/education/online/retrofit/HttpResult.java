package com.education.online.retrofit;

/**
 * Created by liukun on 16/3/5.
 */
public class HttpResult<T> {
    private int return_code;
    private String return_message;
    private T data;

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
