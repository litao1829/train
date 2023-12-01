package com.litao.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.litao.resp.PageResp;
import com.litao.util.SnowUtil;
import com.litao.train.business.domain.App;
import com.litao.train.business.domain.AppExample;
import com.litao.train.business.mapper.AppMapper;
import com.litao.train.business.req.AppQueryReq;
import com.litao.train.business.req.AppSaveReq;
import com.litao.train.business.resp.AppQueryResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {

    private static final Logger LOG = LoggerFactory.getLogger(AppService.class);

    @Resource
    private AppMapper appMapper;

    public void save(AppSaveReq req) {
        DateTime now = DateTime.now();
        App app = BeanUtil.copyProperties(req, App.class);
        if (ObjectUtil.isNull(app.getId())) {
            app.setId(SnowUtil.getSnowflakeNextId());
            app.setCreateTime(now);
            app.setUpdateTime(now);
            appMapper.insert(app);
        } else {
            app.setUpdateTime(now);
            appMapper.updateByPrimaryKey(app);
        }
    }

    public PageResp<AppQueryResp> queryList(AppQueryReq req) {
        AppExample appExample = new AppExample();
        appExample.setOrderByClause("id desc");
        AppExample.Criteria criteria = appExample.createCriteria();

        if(StrUtil.isNotBlank(req.getVersion())){
            criteria.andVersionEqualTo(req.getVersion());
        }

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<App> appList = appMapper.selectByExample(appExample);

        PageInfo<App> pageInfo = new PageInfo<>(appList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<AppQueryResp> list = BeanUtil.copyToList(appList, AppQueryResp.class);

        PageResp<AppQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        appMapper.deleteByPrimaryKey(id);
    }

}