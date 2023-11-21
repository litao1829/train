package com.litao.train.member.req;

import com.litao.req.PageReq;

public class PassengerQueryReq extends PageReq {

    private Long memberId;

    public Long getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "PassengerQueryReq{" +
                "} " + super.toString();
    }
}