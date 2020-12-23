package com.szzii.cn;

import com.alibaba.fastjson.JSONObject;
import com.szzii.cn.entity.DingTalkDto;
import com.szzii.cn.entity.RequestEntity;
import com.szzii.cn.stereotype.Constant;
import com.szzii.cn.stereotype.RequestType;
import com.szzii.cn.util.HttpUtil;
import org.apache.http.*;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DingTalkClient {



    public static String sendDingDing(RequestEntity requestEntity, HttpResponse httpResponse){
        String message = "";
        try {
            String reqUrl = requestEntity.getUrl();
            RequestType requestType = RequestType.valueOf(requestEntity.getMethod());
            String reqBody = requestEntity.getBody();
            Map<String, String[]> parameterMap = requestEntity.getParameterMap();
            StringBuilder reqParam = new StringBuilder();
            if (parameterMap != null && !parameterMap.isEmpty()){
                for (String paramKey : parameterMap.keySet()) {
                    String[] params = parameterMap.get(paramKey);
                    if (params != null){
                        for (String param : params) {
                            if (reqParam.length() > 0){
                                reqParam.append("&");
                            }
                            reqParam.append(paramKey).append("=").append(param);
                        }
                    }
                }
            }
            int resStatusCode = httpResponse.getStatusLine().getStatusCode();
            String resBody = HttpUtil.getJson(httpResponse);
            Map<String, String[]> queryMap = new HashMap<>();
            queryMap.put("access_token",new String[]{Constant.dingtalk_access_token});
            DingTalkDto dingTalkDto = new DingTalkDto();
            message = new StringBuilder(Constant.keywords)
                    .append("\n@15943951022")
                    .append("\n@13625476697")
                    .append("\n内网接口异常：").append(reqUrl)
                    .append("\n请求方式：").append(requestType)
                    .append("\n请求参数：").append(reqParam)
                    .append("\n请求body：").append(reqBody)
                    .append("\n返回状态码：" + resStatusCode)
                    .append("\n返回值差异对比：" + "待处理")
                    .append("\n返回信息：")
                    .append(resBody).toString();
            dingTalkDto.setText(new DingTalkDto.TextDTO(message));
            dingTalkDto.setMsgtype("text");
            ArrayList<String> strings = new ArrayList<String>();
            strings.add("15943951022");
            strings.add("13625476697");
            dingTalkDto.setAt(new DingTalkDto.AtDTO(false,strings));
            System.out.println(JSONObject.toJSONString(dingTalkDto));
            HttpUtil.doPost("https://oapi.dingtalk.com", "/robot/send", new HashMap<>(), queryMap, JSONObject.toJSONString(dingTalkDto));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return message;
        }

    }


    public static String sendDingDingMessage(String messgae){
        String message = "";
        DingTalkDto dingTalkDto = new DingTalkDto();
        Map<String, String[]> queryMap = new HashMap<>();
        queryMap.put("access_token",new String[]{Constant.dingtalk_access_token});
        try {
            message = new StringBuilder(Constant.keywords)
                    .append("\n@15943951022")
                    .append("\n@13625476697")
                    .append("\n").append(messgae).toString();
            dingTalkDto.setText(new DingTalkDto.TextDTO(message));
            dingTalkDto.setMsgtype("text");
            ArrayList<String> strings = new ArrayList<String>();
            strings.add("15943951022");
            strings.add("13625476697");
            dingTalkDto.setAt(new DingTalkDto.AtDTO(false,strings));
            System.out.println(JSONObject.toJSONString(dingTalkDto));
            HttpUtil.doPost("https://oapi.dingtalk.com", "/robot/send", new HashMap<>(), queryMap, JSONObject.toJSONString(dingTalkDto));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return message;
        }

    }



    public static void main(String[] args) throws Exception {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setBody("szz");
        requestEntity.setMethod("GET");
        requestEntity.setUrl("sdaadsa");
        HashMap<String, String[]> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("id",new String[]{"11","22"});
        objectObjectHashMap.put("iszz",new String[]{"11231","223213"});
        objectObjectHashMap.put("isz2z",null);
        requestEntity.setParameterMap(objectObjectHashMap);
        sendDingDing(requestEntity, new HttpResponse() {
            @Override
            public StatusLine getStatusLine() {
                return null;
            }

            @Override
            public void setStatusLine(StatusLine statusLine) {

            }

            @Override
            public void setStatusLine(ProtocolVersion protocolVersion, int i) {

            }

            @Override
            public void setStatusLine(ProtocolVersion protocolVersion, int i, String s) {

            }

            @Override
            public void setStatusCode(int i) throws IllegalStateException {

            }

            @Override
            public void setReasonPhrase(String s) throws IllegalStateException {

            }

            @Override
            public HttpEntity getEntity() {
                return null;
            }

            @Override
            public void setEntity(HttpEntity httpEntity) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public void setLocale(Locale locale) {

            }

            @Override
            public ProtocolVersion getProtocolVersion() {
                return null;
            }

            @Override
            public boolean containsHeader(String s) {
                return false;
            }

            @Override
            public Header[] getHeaders(String s) {
                return new Header[0];
            }

            @Override
            public Header getFirstHeader(String s) {
                return null;
            }

            @Override
            public Header getLastHeader(String s) {
                return null;
            }

            @Override
            public Header[] getAllHeaders() {
                return new Header[0];
            }

            @Override
            public void addHeader(Header header) {

            }

            @Override
            public void addHeader(String s, String s1) {

            }

            @Override
            public void setHeader(Header header) {

            }

            @Override
            public void setHeader(String s, String s1) {

            }

            @Override
            public void setHeaders(Header[] headers) {

            }

            @Override
            public void removeHeader(Header header) {

            }

            @Override
            public void removeHeaders(String s) {

            }

            @Override
            public HeaderIterator headerIterator() {
                return null;
            }

            @Override
            public HeaderIterator headerIterator(String s) {
                return null;
            }

            @Override
            public HttpParams getParams() {
                return null;
            }

            @Override
            public void setParams(HttpParams httpParams) {

            }
        });
    }
}
