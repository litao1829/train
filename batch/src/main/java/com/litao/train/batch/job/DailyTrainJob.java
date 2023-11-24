package com.litao.train.batch.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.litao.resp.CommonResp;
import com.litao.train.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.PipedReader;
import java.util.Date;

@DisallowConcurrentExecution
public class DailyTrainJob implements Job {
    public static final Logger LOG= LoggerFactory.getLogger(DailyTrainJob.class);

    @Resource
    private BusinessFeign businessFeign;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        MDC.put("LOG_ID",System.currentTimeMillis()+ RandomUtil.randomString(3));
        LOG.info("生成15天后的车次定时任务开始");
        Date date=new Date();
        DateTime offset = DateUtil.offsetDay(date, 15);
        Date offsetDay=offset.toJdkDate();
        CommonResp<Object> objectCommonResp = businessFeign.genDaily(offsetDay);
        LOG.info("commonRest:{}",objectCommonResp);
        LOG.info("生成每日车次定时任务结束");
    }
}
