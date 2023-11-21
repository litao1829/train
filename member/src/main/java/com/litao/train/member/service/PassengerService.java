package com.litao.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.litao.context.LoginMemberContext;
import com.litao.train.member.domain.Passenger;
import com.litao.train.member.domain.PassengerExample;
import com.litao.train.member.mapper.PassengerMapper;
import com.litao.train.member.req.PassengerQueryReq;
import com.litao.train.member.req.PassengerSaveReq;
import com.litao.train.member.resp.PassengerQueryResp;
import com.litao.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;


    public void save(PassengerSaveReq req){
        DateTime now=DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setMemberId(LoginMemberContext.getId());
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setUpdateTime(now);
        passenger.setCreateTime(now);
        passengerMapper.insert(passenger);
    }


    public List<PassengerQueryResp> queryList(PassengerQueryReq req){
        PassengerExample example=new PassengerExample();
        PassengerExample.Criteria criteria = example.createCriteria();
        if(ObjectUtil.isNotNull(req.getMemberId())){
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        List<Passenger> passengers = passengerMapper.selectByExample(example);
        return BeanUtil.copyToList(passengers, PassengerQueryResp.class);
    }
}
