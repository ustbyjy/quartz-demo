package com.yan.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Created with IntelliJ IDEA.
 * Description: job相关的事件包括：job即将执行的通知以及job执行完毕的通知。
 * User: Administrator
 * Date: 2016-12-07
 * Time: 17:28
 */
public class MyJobListener implements JobListener {
    public static final String LISTENER_NAME = "MyJobListener";

    public String getName() {
        return LISTENER_NAME;
    }

    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        String jobName = jobExecutionContext.getJobDetail().getKey().toString();
        System.out.println("jobToBeExecuted");
        System.out.println("Job : " + jobName + " is going to start...");
    }

    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        System.out.println("jobExecutionVetoed");
    }

    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        System.out.println("jobWasExecuted");

        String jobName = jobExecutionContext.getJobDetail().getKey().toString();
        System.out.println("Job : " + jobName + " is finished...");

        if (e != null && !"".equals(e.getMessage())) {
            System.out.println("Exception thrown by: " + jobName
                    + " Exception: " + e.getMessage());
        }
    }
}
