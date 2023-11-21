package com.litao.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.litao.context.LoginMemberContext;
import com.litao.resp.PageResp;
import com.litao.train.member.domain.Passenger;
import com.litao.train.member.domain.PassengerExample;
import com.litao.train.member.mapper.PassengerMapper;
import com.litao.train.member.req.PassengerQueryReq;
import com.litao.train.member.req.PassengerSaveReq;
import com.litao.train.member.resp.PassengerQueryResp;
import com.litao.util.SnowUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;


    public void save(PassengerSaveReq req){
        DateTime now=DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setMemberId(LoginMemberContext.getId());
        if(ObjectUtil.isNull(passenger.getId())){
            passenger.setId(SnowUtil.getSnowflakeNextId());
            passenger.setUpdateTime(now);
            passenger.setCreateTime(now);
            passengerMapper.insert(passenger);
        }
        else{
            passenger.setUpdateTime(now);
            passengerMapper.updateByPrimaryKey(passenger);
        }

    }


    public PageResp<PassengerQueryResp> queryList(PassengerQueryReq req){
        PassengerExample example=new PassengerExample();
        example.setOrderByClause("id desc");
        PassengerExample.Criteria criteria = example.createCriteria();
        if(ObjectUtil.isNotNull(req.getMemberId())){
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        log.info("查询页码：{}",req.getPage());
        log.info("每页条数：{}",req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Passenger> passengers = passengerMapper.selectByExample(example);

        PageInfo<Passenger> pageInfo=new PageInfo<>(passengers);
        log.info("总行数：{}",pageInfo.getTotal());
        log.info("总页数：{}",req.getPage());
        List<PassengerQueryResp> queryResps = BeanUtil.copyToList(passengers, PassengerQueryResp.class);
        PageResp<PassengerQueryResp> pageResp=new PageResp<>();

        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(queryResps);
        return pageResp;
    }
}
