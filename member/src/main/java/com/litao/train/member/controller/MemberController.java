package com.litao.train.member.controller;

import com.litao.resp.CommonResp;
import com.litao.train.member.req.MemberLoginReq;
import com.litao.train.member.req.MemberRegisterReq;
import com.litao.train.member.req.MemberSendCodeReq;
import com.litao.train.member.resp.MemberLoginResp;
import com.litao.train.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @GetMapping("/count")
    public Integer count(){
        return memberService.count();
    }

    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq req){
        long register = memberService.register(req);
        return new CommonResp<>(register);
    }

    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid MemberSendCodeReq req){
        memberService.sendCode(req);
        return new CommonResp<>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid MemberLoginReq req){
        MemberLoginResp resp=memberService.login(req);
        return new CommonResp<>(resp);
    }
}
