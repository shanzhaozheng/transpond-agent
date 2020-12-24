package com.szzii.cn.entity;

import java.io.*;
import java.util.Map;

public class RequestEntity implements Cloneable,Serializable {

    private String url;

    private String method;

    private Map<String,String> headerMap;

    private Map<String,String[]> parameterMap;

    private String body;

    public RequestEntity() {
    }

    public RequestEntity(String url, String method, Map<String, String> headerMap, Map<String, String[]> parameterMap, String body) {
        this.url = url;
        this.method = method;
        this.headerMap = headerMap;
        this.parameterMap = parameterMap;
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }




    @Override
    public String toString() {
       return "url = " + url + "\n" + "method = " + method + "\n" + "headerMap = " + headerMap + "\n" + "parameterMap = " + parameterMap + "\n" + "body = " + body + "\n";
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public RequestEntity clonee()  {
        try {
            return (RequestEntity) this.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }


}
