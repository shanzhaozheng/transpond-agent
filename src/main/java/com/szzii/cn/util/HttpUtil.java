package com.szzii.cn.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author szz
 */
public class HttpUtil {


    private static HttpClientBuilder httpClientBuilder;

    /**
     * get
     *
     * @param host
     * @param path
     *
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doGet(String host, String path, Map<String, String> headers, Map<String, String[]> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        return httpClient.execute(request);
    }

    /**
     * Post String
     *
     * @param host
     * @param path
     *
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPost(String host, String path, Map<String, String> headers, Map<String, String[]> querys,
                                      String body) throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (body != null && !body.equals("")) {
            request.addHeader("Content-Type","application/json; charset=UTF-8");
            request.setEntity(new StringEntity(body, "utf-8"));

        }
        return httpClient.execute(request);
    }


    /**
     * Put String
     *
     * @param host
     * @param path
     *
     * @param headers
     * @param querys
     * @param body
     * @return
     * @throws Exception
     */
    public static HttpResponse doPut(String host, String path, Map<String, String> headers, Map<String, String[]> querys,
                                     String body) throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (body != null && body.equals("")) {
            request.addHeader("Content-Type","application/json");
            request.setEntity(new StringEntity(body, "utf-8"));
        }
        return httpClient.execute(request);
    }


    /**
     * Delete
     *
     * @param host
     * @param path
     *
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doDelete(String host, String path, Map<String, String> headers,
                                        Map<String, String[]> querys) throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        return httpClient.execute(request);
    }


    public static HttpResponse doPatch(String host, String path, Map<String, String> headers,
                                        Map<String, String[]> querys,String body) throws Exception {
        HttpClient httpClient = wrapClient(host, path);
        HttpPatch request = new HttpPatch(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        if (body != null && body.equals("")) {
            request.addHeader("Content-Type","application/json");
            request.setEntity(new StringEntity(body, "utf-8"));
        }
        return httpClient.execute(request);
    }

    /**
     * 构建请求的 url
     *
     * @param host
     * @param path
     * @param querys
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String buildUrl(String host, String path, Map<String, String[]> querys)
            throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        if (host != null && !host.equals("")) {
            sbUrl.append(host);
        }
        if (path != null && !path.equals("")) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String[]> query : querys.entrySet()) {
                String[] params = query.getValue();
                if (params != null){
                    for (String param : params) {
                        if (0 < sbQuery.length()) {
                            sbQuery.append("&");
                        }
                        sbQuery.append(query.getKey()).append("=").append(URLEncoder.encode(param, "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }
        return sbUrl.toString();
    }




    /**
     * 获取 HttpClient
     *
     * @param host
     * @param path
     * @return
     */
    private static HttpClient wrapClient(String host, String path) {
       {
            if(null == httpClientBuilder) {
                synchronized (HttpUtil.class) {
                    if(null == httpClientBuilder) {
                        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000)// 连接超时时间
                                .setSocketTimeout(5000)// 请求获取数据的超时时间
                                .build();
                        httpClientBuilder = HttpClientBuilder.create().setMaxConnTotal(5) // 连接池中最大连接数
                                .setDefaultRequestConfig(requestConfig);
                    }
                }
            }
            return httpClientBuilder.build();
        }
    }



    /**
     * 将结果转换成JSONO字符
     *
     * @param httpResponse
     * @return
     * @throws IOException
     */
    public static String getJson(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        if (null != entity){

            return EntityUtils.toString(entity, "UTF-8");
        }
        return "";
    }
}