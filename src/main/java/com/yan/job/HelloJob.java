package com.yan.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2016-12-07
 * Time: 16:51
 */
public class HelloJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(HelloJob.class);

    /**
     * 当job的一个trigger被触发后（稍后会讲到），execute()方法会被scheduler的一个工作线程调用；
     * 传递给execute()方法的JobExecutionContext对象中保存着该job运行时的一些信息，比如：
     * 1、执行job的scheduler的引用
     * 2、触发job的trigger的引用
     * 3、JobDetail对象引用
     * 4、一些其它信息。
     *
     * @param jobExecutionContext Job执行上下文
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Scheduler scheduler = jobExecutionContext.getScheduler();
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        System.out.println(new Date() + " Hello Quartz!!!!!!");

        // Throw exception for testing
//        throw new JobExecutionException("Testing Exception");
    }
}
