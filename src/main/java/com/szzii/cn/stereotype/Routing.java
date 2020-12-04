package com.szzii.cn.stereotype;

public enum  Routing {

    B2B("/b2b","b端接口"),

    B2C("/b2c","c端接口");


    private final String url;

    private final String describe;


    Routing(String url, String describe) {
        this.url = url;
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public String getDescribe() {
        return describe;
    }

    @Override
    public String toString() {
        return url;
    }

}
