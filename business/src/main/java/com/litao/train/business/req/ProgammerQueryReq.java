package com.litao.train.business.req;

import com.litao.req.PageReq;

public class ProgammerQueryReq extends PageReq {

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "ProgammerQueryReq{" +
                "nickname='" + nickname + '\'' +
                '}';
    }
}