package com.litao.train.batch.job;


import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class JobTest implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("TestJob TEST 开始");
        //try {
        //    Thread.sleep(3000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        System.out.println("TestJob TEST 结束");
    }
}
