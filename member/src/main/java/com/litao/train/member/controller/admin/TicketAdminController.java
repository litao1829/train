package com.litao.train.member.controller.admin;

import com.litao.resp.CommonResp;
import com.litao.resp.PageResp;
import com.litao.train.member.req.TicketQueryReq;
import com.litao.train.member.req.TicketSaveReq;
import com.litao.train.member.resp.TicketQueryResp;
import com.litao.train.member.service.TicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

    @Resource
    private TicketService ticketService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<TicketQueryResp>> queryList(@Valid TicketQueryReq req) {
        PageResp<TicketQueryResp> list = ticketService.queryList(req);
        return new CommonResp<>(list);
    }

}