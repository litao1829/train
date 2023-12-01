package com.litao.train.business.controller.admin;

import com.litao.context.LoginMemberContext;
import com.litao.resp.CommonResp;
import com.litao.resp.PageResp;
import com.litao.train.business.req.AppQueryReq;
import com.litao.train.business.req.AppSaveReq;
import com.litao.train.business.resp.AppQueryResp;
import com.litao.train.business.service.AppService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/app")
public class AppAdminController {

    @Resource
    private AppService appService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody AppSaveReq req) {
        appService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<AppQueryResp>> queryList(@Valid AppQueryReq req) {
        PageResp<AppQueryResp> list = appService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        appService.delete(id);
        return new CommonResp<>();
    }

}