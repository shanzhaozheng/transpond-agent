package com.szzii.cn.util;

import com.szzii.cn.entity.RequestEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CommonUtil {

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
}
