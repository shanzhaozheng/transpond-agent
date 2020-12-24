package com.szzii.cn.entity;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
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


    public static RequestEntity buildRequestEntity(Object request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> requestClass = request.getClass();

        Method getServletPath = requestClass.getMethod("getServletPath");
        String servletPath = (String)getServletPath.invoke(request);

        Method getMethod = requestClass.getMethod("getMethod");
        String method = (String)getMethod.invoke(request);

        Method getParameterMap = requestClass.getMethod("getParameterMap");
        Map<String, String[]> parameterMap = (Map<String, String[]>)getParameterMap.invoke(request);

        Method getHeaderNames = requestClass.getMethod("getHeaderNames");
        Enumeration<String> headerNames = (Enumeration<String>)getHeaderNames.invoke(request);

        Method getHeader = requestClass.getMethod("getHeader",String.class);

        Method getReader = requestClass.getMethod("getReader");
        BufferedReader reader = (BufferedReader)getReader.invoke(request);

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(servletPath);
        requestEntity.setMethod(method);
        requestEntity.setParameterMap(parameterMap);


        // 头信息
        HashMap<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            headerMap.put(headerName,(String) getHeader.invoke(request,headerName));
        }
        requestEntity.setHeaderMap(headerMap);

        // body信息
        StringBuilder body = null;
        try {
            body = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null)
            {
                body.append(str);
            }
            requestEntity.setBody(body.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestEntity;
    }


    @Override
    public String toString() {
       return "url = " + url + "\n" + "method = " + method + "\n" + "headerMap = " + headerMap + "\n" + "parameterMap = " + parameterMap + "\n" + "body = " + body + "\n";
    }


    public RequestEntity deepClone(){

        ByteArrayInputStream bis = null;
        ObjectInputStream ois=null;

        ByteArrayOutputStream bos= null;
        ObjectOutputStream oos =null;

        try {
            bos=new ByteArrayOutputStream();
            oos=new ObjectOutputStream(bos);
            oos.writeObject(this);

            bis=new ByteArrayInputStream(bos.toByteArray());
            ois=new ObjectInputStream(bis);
            Object object = ois.readObject();
            return (RequestEntity)object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                bis.close();
                oos.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
