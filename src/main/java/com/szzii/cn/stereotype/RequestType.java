package com.szzii.cn.stereotype;

import com.szzii.cn.entity.RequestEntity;
import com.szzii.cn.util.HttpUtil;
import org.apache.http.HttpResponse;

public enum  RequestType implements ExecFunction {

    GET(){
        @Override
        public HttpResponse doRequest(RequestEntity requestEntity) throws Exception {
            return HttpUtil.doGet(Constant.URL, requestEntity.getUrl(), super.buildHeader(), requestEntity.getParameterMap());
        }
    },

    POST(){
        @Override
        public HttpResponse doRequest(RequestEntity requestEntity) throws Exception {
            String userId = requestEntity.getHeaderMap().get("x-curruserid");
            return HttpUtil.doPost(Constant.URL, requestEntity.getUrl(), super.buildHeader(userId), requestEntity.getParameterMap(), requestEntity.getBody());
        }
    },

    PUT(){
        @Override
        public HttpResponse doRequest(RequestEntity requestEntity) throws Exception {
            String userId = requestEntity.getHeaderMap().get("x-curruserid");
            return HttpUtil.doPut(Constant.URL, requestEntity.getUrl(), super.buildHeader(userId), requestEntity.getParameterMap(), requestEntity.getBody());
        }
    },

    DELETE(){
        @Override
        public HttpResponse doRequest(RequestEntity requestEntity) throws Exception {
            String userId = requestEntity.getHeaderMap().get("x-curruserid");
            return HttpUtil.doDelete(Constant.URL, requestEntity.getUrl(), super.buildHeader(userId), requestEntity.getParameterMap());
        }
    },

    PATCH(){
        @Override
        public HttpResponse doRequest(RequestEntity requestEntity) throws Exception {
            String userId = requestEntity.getHeaderMap().get("x-curruserid");
            return HttpUtil.doPatch(Constant.URL, requestEntity.getUrl(), super.buildHeader(userId), requestEntity.getParameterMap(), requestEntity.getBody());
        }
    };


}
