package com.education.online.act.ucloud;

import java.io.Serializable;

/**
 * UFileRequest
 *
 * Name	            Type	Description	                Required
 * HttpMethod	    String	http method 	              Yes
 * Authorization	String	请求的授权签名	              Yes
 * Content-Type	    String	请求body部分即待上传文件的类型    No
 * Content-MD5	    String	文件内容的MD5摘要	              No
 *
 * Created by jerry on 15/12/17.
 */
public class UFileRequest implements Serializable {

    //http request method
    private String httpMethod;

    //http headers
    private String authorization;
    private String contentType;
    private String contentMD5;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentMD5() {
        return contentMD5;
    }

    public void setContentMD5(String contentMD5) {
        this.contentMD5 = contentMD5;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public String toString() {
        return "UFileRequest{" +
                ", httpMethod='" + httpMethod + '\'' +
                ", authorization='" + authorization + '\'' +
                ", contentType='" + contentType + '\'' +
                ", contentMD5='" + contentMD5 + '\'' +
                '}';
    }
}
