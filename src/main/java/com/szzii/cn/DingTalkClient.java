package com.szzii.cn;

import com.alibaba.fastjson.JSONObject;
import com.szzii.cn.entity.DingTalkDto;
import com.szzii.cn.entity.RequestEntity;
import com.szzii.cn.stereotype.Constant;
import com.szzii.cn.stereotype.RequestType;
import com.szzii.cn.util.HttpUtil;
import org.apache.http.HttpResponse;

import java.util.ArrayList;
import java.util.HashMap;
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
                for (String param : parameterMap.keySet()) {
                    reqParam.append(param + "=" + parameterMap.get(param));
                }
            }
            int resStatusCode = httpResponse.getStatusLine().getStatusCode();
            String resBody = HttpUtil.getJson(httpResponse);
            Map<String, String[]> queryMap = new HashMap<>();
            queryMap.put("access_token",new String[]{Constant.dingtalk_access_token});
            DingTalkDto dingTalkDto = new DingTalkDto();
            message = new StringBuilder(Constant.keywords)
                    .append("\n@15943951022")
                    .append("\n内网接口异常：" + reqUrl)
                    .append("\n请求方式：" + requestType)
                    .append("\n请求参数：" + reqParam)
                    .append("\n请求body：" + reqBody)
                    .append("\n返回状态码：" + resStatusCode)
                    .append("\n返回值差异对比：" + "待处理")
                    .append("\n返回信息：" + resBody).toString();
            dingTalkDto.setText(new DingTalkDto.TextDTO(message));
            dingTalkDto.setMsgtype("text");
            ArrayList<String> strings = new ArrayList<String>();
            strings.add("15943951022");
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
        Map<String, String[]> queryMap = new HashMap<>();
        queryMap.put("access_token",new String[]{Constant.dingtalk_access_token});
        DingTalkDto dingTalkDto = new DingTalkDto();
        dingTalkDto.setText(new DingTalkDto.TextDTO(Constant.keywords + "\n@15943951022"
                +"\n内网接口异常：" + "url"
                +"\n请求参数：" +"k:v"
                +"\n请求body：" +"k:v"
                +"\n返回状态码："+ "statusCode"
                +"\n返回值差异对比："+ "body"
                +"\n返回信息：" + "body"));
        dingTalkDto.setMsgtype("text");
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("15943951022");
        dingTalkDto.setAt(new DingTalkDto.AtDTO(false,strings));
        System.out.println(JSONObject.toJSONString(dingTalkDto));
        //HttpUtil.doPost("https://oapi.dingtalk.com", "/robot/send", new HashMap<>(), queryMap, JSONObject.toJSONString(dingTalkDto));
    }
}
