package com.litao.train.member.req;


import com.litao.req.PageReq;
import lombok.Data;

@Data
public class PassengerQueryReq  extends PageReq {
    private Long memberId;
}
