package com.litao.train.${module}.controller.admin;

import com.litao.context.LoginMemberContext;
import com.litao.resp.CommonResp;
import com.litao.resp.PageResp;
import com.litao.train.${module}.req.${Domain}QueryReq;
import com.litao.train.${module}.req.${Domain}SaveReq;
import com.litao.train.${module}.resp.${Domain}QueryResp;
import com.litao.train.${module}.service.${Domain}Service;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/${do_main}")
public class ${Domain}AdminController {

@Resource
private ${Domain}Service ${domain}Service;

@PostMapping("/save")
public CommonResp<Object> save(@Valid @RequestBody ${Domain}SaveReq req) {
    ${domain}Service.save(req);
    return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<${Domain}QueryResp>> queryList(@Valid ${Domain}QueryReq req) {
        PageResp<${Domain}QueryResp> list = ${domain}Service.queryList(req);
            return new CommonResp<>(list);
            }

            @DeleteMapping("/delete/{id}")
            public CommonResp<Object> delete(@PathVariable Long id) {
                ${domain}Service.delete(id);
                return new CommonResp<>();
                }

                }