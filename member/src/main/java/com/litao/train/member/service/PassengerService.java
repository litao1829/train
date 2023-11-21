package com.litao.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.litao.train.member.domain.Passenger;
import com.litao.train.member.mapper.PassengerMapper;
import com.litao.train.member.req.PassengerSaveReq;
import com.litao.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;


    public void save(PassengerSaveReq req){
        DateTime now=DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setUpdateTime(now);
        passenger.setCreateTime(now);
        passengerMapper.insert(passenger);
    }
}
