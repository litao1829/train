package com.litao.train.business.req;

import com.litao.req.PageReq;

public class AppQueryReq extends PageReq {
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
public String toString() {
return "AppQueryReq{" +
"} " + super.toString();
}
}