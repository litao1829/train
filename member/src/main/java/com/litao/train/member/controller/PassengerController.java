package com.litao.train.member.controller;

import com.litao.context.LoginMemberContext;
import com.litao.resp.CommonResp;
import com.litao.train.member.req.PassengerQueryReq;
import com.litao.train.member.req.PassengerSaveReq;
import com.litao.train.member.resp.PassengerQueryResp;
import com.litao.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginContext;
import java.util.List;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req){
        passengerService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<List<PassengerQueryResp>> querList(@Valid PassengerQueryReq req){
        req.setMemberId(LoginMemberContext.getId());
        List<PassengerQueryResp> resps = passengerService.queryList(req);
        return new CommonResp<>(resps);
    }
}
