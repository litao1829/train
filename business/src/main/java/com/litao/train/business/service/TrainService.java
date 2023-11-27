package com.litao.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.litao.exception.BusinessException;
import com.litao.exception.BusinessExceptionEnum;
import com.litao.resp.PageResp;
import com.litao.util.SnowUtil;
import com.litao.train.business.domain.Train;
import com.litao.train.business.domain.TrainExample;
import com.litao.train.business.mapper.TrainMapper;
import com.litao.train.business.req.TrainQueryReq;
import com.litao.train.business.req.TrainSaveReq;
import com.litao.train.business.resp.TrainQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainService {

    private static final Logger LOG = LoggerFactory.getLogger(TrainService.class);

    @Resource
    private TrainMapper trainMapper;

    public void save(TrainSaveReq req) {
        DateTime now = DateTime.now();
        Train train = BeanUtil.copyProperties(req, Train.class);
        if (ObjectUtil.isNull(train.getId())) {
            //保存之前，先校验一遍是否存在
            Train train1 = selectByUnique(req.getCode());
            if(ObjectUtil.isNotEmpty(train1)){
                throw  new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_CODE_UNIQUE_ERROR);
            }
            train.setId(SnowUtil.getSnowflakeNextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            trainMapper.insert(train);
        } else {
            train.setUpdateTime(now);
            trainMapper.updateByPrimaryKey(train);
        }
    }

    public PageResp<TrainQueryResp> queryList(TrainQueryReq req) {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("id desc");
        TrainExample.Criteria criteria = trainExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Train> trainList = trainMapper.selectByExample(trainExample);

        PageInfo<Train> pageInfo = new PageInfo<>(trainList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<TrainQueryResp> list = BeanUtil.copyToList(trainList, TrainQueryResp.class);

        PageResp<TrainQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        trainMapper.deleteByPrimaryKey(id);
    }


//    public List<TrainQueryResp> queryAll(){
//        TrainExample trainExample=new TrainExample();
//        trainExample.setOrderByClause("code desc");
//        List<Train> trains = trainMapper.selectByExample(trainExample);
//        return BeanUtil.copyToList(trains,TrainQueryResp.class);
//    }

    @Transactional
    public List<TrainQueryResp> queryAll(){
        List<Train> trains = selectAll();
        trains=selectAll();
        return BeanUtil.copyToList(trains, TrainQueryResp.class);
    }

    private Train selectByUnique(String code){
        TrainExample trainExample=new TrainExample();
        trainExample.createCriteria().andCodeEqualTo(code);
        List<Train> trains = trainMapper.selectByExample(trainExample);
        if(CollUtil.isNotEmpty(trains)){
            return trains.get(0);
        }else {
            return null;
        }
    }

    public List<Train> selectAll(){
        TrainExample trainExample=new TrainExample();
        trainExample.setOrderByClause("code asc");
        return trainMapper.selectByExample(trainExample);
    }
}