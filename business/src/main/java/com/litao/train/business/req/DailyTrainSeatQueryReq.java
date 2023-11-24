package com.litao.train.business.req;

import com.litao.req.PageReq;

public class DailyTrainSeatQueryReq extends PageReq {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DailyTrainSeatQueryReq{" +
                "code='" + code + '\'' +
                '}';
    }
}