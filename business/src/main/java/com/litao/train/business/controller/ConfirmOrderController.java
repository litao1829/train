package com.litao.train.business.controller;

import com.litao.resp.CommonResp;
import com.litao.train.business.domain.ConfirmOrder;
import com.litao.train.business.req.ConfirmOrderDoReq;
import com.litao.train.business.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/do")
    public CommonResp<Object> doConfirm(@Valid @RequestBody ConfirmOrderDoReq req){
        confirmOrderService.doConfirm(req);
        return new CommonResp<>();
    }
}
