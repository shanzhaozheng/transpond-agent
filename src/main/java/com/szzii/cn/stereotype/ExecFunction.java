package com.szzii.cn.stereotype;

import com.szzii.cn.entity.RequestEntity;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;


public interface ExecFunction {

    HttpResponse doRequest(RequestEntity requestEntity) throws Exception;


    default Map<String, String> buildHeader(){
        HashMap<String, String> headerMap = new HashMap();
        headerMap.put("Accept","*/*");
        headerMap.put("Connection","keep-alive");
        headerMap.put("User-Agent","transpond-agent");
        return headerMap;
    }

    default Map<String, String> buildHeader(String opUserId){
        HashMap<String, String> headerMap = new HashMap();
        headerMap.put("x-curruserid",opUserId);
        headerMap.put("Accept","*/*");
        headerMap.put("Connection","keep-alive");
        headerMap.put("User-Agent","transpond-agent");
        return headerMap;
    }
}
