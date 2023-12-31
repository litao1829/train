package com.litao.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.litao.exception.BusinessException;
import com.litao.exception.BusinessExceptionEnum;
import com.litao.resp.PageResp;
import com.litao.train.business.domain.TrainCarriage;
import com.litao.train.business.domain.TrainCarriageExample;
import com.litao.train.business.enums.SeatColEnum;
import com.litao.util.SnowUtil;
import com.litao.train.business.domain.DailyTrainCarriage;
import com.litao.train.business.domain.DailyTrainCarriageExample;
import com.litao.train.business.mapper.DailyTrainCarriageMapper;
import com.litao.train.business.req.DailyTrainCarriageQueryReq;
import com.litao.train.business.req.DailyTrainCarriageSaveReq;
import com.litao.train.business.resp.DailyTrainCarriageQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DailyTrainCarriageService {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainCarriageService.class);

    @Resource
    private DailyTrainCarriageMapper dailyTrainCarriageMapper;

    @Resource
    private TrainCarriageService trainCarriageService;

    public void save(DailyTrainCarriageSaveReq req) {
        DateTime now = DateTime.now();

        //自动计算出列数和座位总数
        List<SeatColEnum> colsByType = SeatColEnum.getColsByType(req.getSeatType());
        req.setColCount(colsByType.size());
        req.setSeatCount(req.getColCount()* req.getRowCount());

        DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(req, DailyTrainCarriage.class);
        if (ObjectUtil.isNull(dailyTrainCarriage.getId())) {

            //保存前限校验一遍是否存在
            DailyTrainCarriage trainCarriage1 = selectByUnique(req.getTrainCode(), req.getIndex());
            if(ObjectUtil.isNotEmpty(trainCarriage1)){
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_CARRIAGE_INDEX_UNIQUE_ERROR);
            }
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        } else {
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriageMapper.updateByPrimaryKey(dailyTrainCarriage);
        }
    }

    public PageResp<DailyTrainCarriageQueryResp> queryList(DailyTrainCarriageQueryReq req) {
        DailyTrainCarriageExample dailyTrainCarriageExample = new DailyTrainCarriageExample();
        dailyTrainCarriageExample.setOrderByClause("date desc,train_code asc");
        DailyTrainCarriageExample.Criteria criteria = dailyTrainCarriageExample.createCriteria();
        if(ObjectUtil.isNotNull(req.getDate())){
            criteria.andDateEqualTo(req.getDate());
        }
        if(ObjectUtil.isNotEmpty(req.getCode())){
            criteria.andTrainCodeEqualTo(req.getCode());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainCarriage> dailyTrainCarriageList = dailyTrainCarriageMapper.selectByExample(dailyTrainCarriageExample);

        PageInfo<DailyTrainCarriage> pageInfo = new PageInfo<>(dailyTrainCarriageList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainCarriageQueryResp> list = BeanUtil.copyToList(dailyTrainCarriageList, DailyTrainCarriageQueryResp.class);

        PageResp<DailyTrainCarriageQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainCarriageMapper.deleteByPrimaryKey(id);
    }


    public List<DailyTrainCarriage> selectByTrainCode(String trainCode){
        DailyTrainCarriageExample dailyTrainCarriageExample=new DailyTrainCarriageExample();
        dailyTrainCarriageExample.setOrderByClause("`index` asc");
        DailyTrainCarriageExample.Criteria criteria = dailyTrainCarriageExample.createCriteria();
        criteria.andTrainCodeEqualTo(trainCode);
        return dailyTrainCarriageMapper.selectByExample(dailyTrainCarriageExample);
    }

    private DailyTrainCarriage selectByUnique(String trainCode,Integer index){
        DailyTrainCarriageExample dailyTrainCarriageExample=new DailyTrainCarriageExample();
        dailyTrainCarriageExample.createCriteria()
                .andTrainCodeEqualTo(trainCode)
                .andIndexEqualTo(index);
        List<DailyTrainCarriage> trainCarriages = dailyTrainCarriageMapper.selectByExample(dailyTrainCarriageExample);
        if(CollUtil.isNotEmpty(trainCarriages)){
            return trainCarriages.get(0);
        }else {
            return null;
        }
    }

    public void genDaily(Date date, String trainCode){
        LOG.info("生成日期【{}】车次【{}】的车厢信息开始", DateUtil.formatDate(date),trainCode);

        //删除某日某车次的车厢信息
        DailyTrainCarriageExample dailyTrainCarriageExample=new DailyTrainCarriageExample();
        dailyTrainCarriageExample.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode);
        dailyTrainCarriageMapper.deleteByExample(dailyTrainCarriageExample);


        //查出某车次的所有的车厢信息
        List<TrainCarriage> trainCarriages = trainCarriageService.selectByTrainCode(trainCode);
        if(CollUtil.isEmpty(trainCarriages)){
            LOG.info("该车次没有车厢基础数据，生成该车次的车厢信息结束");
        }

        for(TrainCarriage trainCarriage : trainCarriages){
            DateTime now =DateTime.now();
            DailyTrainCarriage dailyTrainCarriage = BeanUtil.copyProperties(trainCarriage, DailyTrainCarriage.class);
            dailyTrainCarriage.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainCarriage.setCreateTime(now);
            dailyTrainCarriage.setUpdateTime(now);
            dailyTrainCarriage.setDate(date);
            dailyTrainCarriageMapper.insert(dailyTrainCarriage);
        }
        LOG.info("生成日期【{}】车次【{}】的车厢信息结束",DateUtil.formatDate(date),trainCode);
    }

    public List<DailyTrainCarriage> selectBySeatType (Date date, String trainCode, String seatType) {
        DailyTrainCarriageExample example = new DailyTrainCarriageExample();
        example.createCriteria()
                .andDateEqualTo(date)
                .andTrainCodeEqualTo(trainCode)
                .andSeatTypeEqualTo(seatType);
        return dailyTrainCarriageMapper.selectByExample(example);
    }
}