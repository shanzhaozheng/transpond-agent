package com.szzii.cn;


import com.alibaba.fastjson.JSONObject;
import com.szzii.cn.entity.DingTalkDto;
import com.szzii.cn.entity.RequestEntity;
import com.szzii.cn.stereotype.Constant;
import com.szzii.cn.stereotype.RequestType;
import com.szzii.cn.util.HttpUtil;
import com.szzii.cn.util.ThreadUtil;
import org.apache.http.HttpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author szz
 */
public class Dispatch {

    public static void main(String[] args) throws Exception {
        Map<String, String[]> queryMap = new HashMap<>();
        queryMap.put("access_token",new String[]{Constant.dingtalk_access_token});
        DingTalkDto dingTalkDto = new DingTalkDto();
        dingTalkDto.setText(new DingTalkDto.TextDTO(Constant.keywords + "\n@15943951022\n内网接口异常：" + "url" + "\n状态码："+ "statusCode" + "\n返回信息：" + "body"));
        dingTalkDto.setMsgtype("text");
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("15943951022");
        dingTalkDto.setAt(new DingTalkDto.AtDTO(false,strings));
        System.out.println(JSONObject.toJSONString(dingTalkDto));
        HttpUtil.doPost("https://oapi.dingtalk.com", "/robot/send", new HashMap<>(), queryMap, JSONObject.toJSONString(dingTalkDto));

    }


    public static void doDispatch(RequestEntity requestEntity) {
        ThreadUtil.pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = requestEntity.getUrl();
                    RequestType requestType = RequestType.valueOf(requestEntity.getMethod());
                    HttpResponse httpResponse = requestType.doRequest(requestEntity);
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    String body = HttpUtil.getJson(httpResponse);

                    if (statusCode != 200 && statusCode != 400 && statusCode != 409){
                        Map<String, String[]> queryMap = new HashMap<>();
                        queryMap.put("access_token",new String[]{Constant.dingtalk_access_token});
                        DingTalkDto dingTalkDto = new DingTalkDto();
                        dingTalkDto.setText(new DingTalkDto.TextDTO(Constant.keywords + "\n@15943951022\n内网接口异常：" + url + "\n状态码："+ statusCode + "\n返回信息：" + body));
                        dingTalkDto.setMsgtype("text");
                        ArrayList<String> strings = new ArrayList<String>();
                        strings.add("15943951022");
                        dingTalkDto.setAt(new DingTalkDto.AtDTO(false,strings));
                        System.out.println(JSONObject.toJSONString(dingTalkDto));
                        HttpUtil.doPost("https://oapi.dingtalk.com", "/robot/send", new HashMap<>(), queryMap, JSONObject.toJSONString(dingTalkDto));
                    }
                    System.out.println("=======================转发地址"+ Constant.URL+" ======================= \n" +
                            "=======================转发数据======================= \n" +
                            requestEntity.toString() + "\n" +
                            "=======================响应数据======================= \n" +
                            body + "\n" +
                            "=======================结束=======================");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
