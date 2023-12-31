package com.litao.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.litao.resp.PageResp;
import com.litao.util.SnowUtil;
import com.litao.train.business.domain.Progammer;
import com.litao.train.business.domain.ProgammerExample;
import com.litao.train.business.mapper.ProgammerMapper;
import com.litao.train.business.req.ProgammerQueryReq;
import com.litao.train.business.req.ProgammerSaveReq;
import com.litao.train.business.resp.ProgammerQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgammerService {

    private static final Logger LOG = LoggerFactory.getLogger(ProgammerService.class);

    @Resource
    private ProgammerMapper progammerMapper;

    public void save(ProgammerSaveReq req) {
        DateTime now = DateTime.now();
        Progammer progammer = BeanUtil.copyProperties(req, Progammer.class);
        if (ObjectUtil.isNull(progammer.getId())) {
            progammer.setId(SnowUtil.getSnowflakeNextId());
            progammer.setCreateTime(now);
            progammer.setUpdateTime(now);
            progammerMapper.insert(progammer);
        } else {
            progammer.setUpdateTime(now);
            progammerMapper.updateByPrimaryKey(progammer);
        }
    }

    public PageResp<ProgammerQueryResp> queryList(ProgammerQueryReq req) {
        ProgammerExample progammerExample = new ProgammerExample();
        progammerExample.setOrderByClause("id desc");
        ProgammerExample.Criteria criteria = progammerExample.createCriteria();
        if(StrUtil.isNotEmpty(req.getNickname())){
           criteria.andNicknameLike(req.getNickname());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Progammer> progammerList = progammerMapper.selectByExample(progammerExample);

        PageInfo<Progammer> pageInfo = new PageInfo<>(progammerList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<ProgammerQueryResp> list = BeanUtil.copyToList(progammerList, ProgammerQueryResp.class);

        PageResp<ProgammerQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        progammerMapper.deleteByPrimaryKey(id);
    }

}