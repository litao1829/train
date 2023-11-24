package com.litao.train.business.controller.admin;

import com.litao.context.LoginMemberContext;
import com.litao.resp.CommonResp;
import com.litao.resp.PageResp;
import com.litao.train.business.req.ProgammerQueryReq;
import com.litao.train.business.req.ProgammerSaveReq;
import com.litao.train.business.resp.ProgammerQueryResp;
import com.litao.train.business.service.ProgammerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/progammer")
public class ProgammerAdminController {

    @Resource
    private ProgammerService progammerService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody ProgammerSaveReq req) {
        progammerService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<ProgammerQueryResp>> queryList(@Valid ProgammerQueryReq req) {
        PageResp<ProgammerQueryResp> list = progammerService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        progammerService.delete(id);
        return new CommonResp<>();
    }

}