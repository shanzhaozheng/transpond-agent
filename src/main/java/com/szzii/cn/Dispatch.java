package com.szzii.cn;


import com.szzii.cn.entity.RequestEntity;
import com.szzii.cn.stereotype.RequestType;
import com.szzii.cn.util.ThreadUtil;
import org.apache.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author szz
 */
public class Dispatch {

    public static final List<Integer> status = new ArrayList<>();

    static {
        status.add(200);
        status.add(400);
        status.add(409);
        status.add(502);
    }


    public static void doDispatch(RequestEntity requestEntity) {
        ThreadUtil.pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestType requestType = RequestType.valueOf(requestEntity.getMethod());
                    HttpResponse httpResponse = requestType.doRequest(requestEntity);
                    // 是否发送钉钉报警
                    if (!filterStatus(httpResponse.getStatusLine().getStatusCode())){
                        DingTalkClient.sendDingDing(requestEntity, httpResponse);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static boolean filterStatus(int statusCode){
        for (Integer integer : status) {
            if (statusCode == integer) {
                 return false;
            }
        }
        return true;
    }

}
