package com.yan.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2016-12-07
 * Time: 16:51
 */
public class HelloJob implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello Quartz!!!!!!");

        // Throw exception for testing
//        throw new JobExecutionException("Testing Exception");
    }
}
