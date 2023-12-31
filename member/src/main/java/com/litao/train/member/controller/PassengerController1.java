//package com.litao.train.member.controller;
//
//import com.litao.context.LoginMemberContext;
//import com.litao.resp.CommonResp;
//import com.litao.resp.PageResp;
//import com.litao.train.member.req.PassengerQueryReq;
//import com.litao.train.member.req.PassengerSaveReq;
//import com.litao.train.member.resp.PassengerQueryResp;
//import com.litao.train.member.service.PassengerService;
//import jakarta.annotation.Resource;
//import jakarta.validation.Valid;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/passenger")
//public class PassengerController1 {
//
//    @Resource
//    private PassengerService passengerService;
//
//    @PostMapping("/save")
//    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req){
//        passengerService.save(req);
//        return new CommonResp<>();
//    }
//
//    @GetMapping("/query-list")
//    public CommonResp<PageResp<PassengerQueryResp>> querList(@Valid PassengerQueryReq req){
//        req.setMemberId(LoginMemberContext.getId());
//        PageResp<PassengerQueryResp> resps = passengerService.queryList(req);
//        return new CommonResp<>(resps);
//    }
//
//    @DeleteMapping("/deleted/{id}")
//    public CommonResp<Object> delete(@PathVariable Long id){
//        passengerService.delete(id);
//        return new CommonResp<>();
//    }
//}
