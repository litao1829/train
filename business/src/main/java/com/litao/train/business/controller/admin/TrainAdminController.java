package com.litao.train.business.controller.admin;

import com.litao.context.LoginMemberContext;
import com.litao.resp.CommonResp;
import com.litao.resp.PageResp;
import com.litao.train.business.req.TrainQueryReq;
import com.litao.train.business.req.TrainSaveReq;
import com.litao.train.business.resp.TrainQueryResp;
import com.litao.train.business.service.TrainSeatService;
import com.litao.train.business.service.TrainService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train")
public class TrainAdminController {

    @Resource
    private TrainService trainService;

    @Resource
    private TrainSeatService trainSeatService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody TrainSaveReq req) {
        trainService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<TrainQueryResp>> queryList(@Valid TrainQueryReq req) {
        PageResp<TrainQueryResp> list = trainService.queryList(req);
        return new CommonResp<>(list);
    }

    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryList(){
        List<TrainQueryResp> resps = trainService.queryAll();
        return new CommonResp<List<TrainQueryResp>>(resps);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        trainService.delete(id);
        return new CommonResp<>();
    }

    @GetMapping("/gen-seat/{trainCode}")
    public CommonResp<Object> genSeat(@PathVariable String trainCode){
        trainSeatService.genTrainSeat(trainCode);
        return new CommonResp<>();
    }

}