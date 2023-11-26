package com.litao.train.business.controller;

import com.litao.resp.CommonResp;
import com.litao.train.business.resp.StationQueryResp;
import com.litao.train.business.service.StationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController {

    @Resource
    private StationService stationService;

    @GetMapping("/query-all")
    public CommonResp<List<StationQueryResp>> querList(){
        List<StationQueryResp> stationQueryResps = stationService.queryAll();
        return new CommonResp<>(stationQueryResps);
    }
}
