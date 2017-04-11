package com.yan;

import com.yan.job.HelloJob;
import com.yan.listener.MyJobListener;
import com.yan.listener.MyScheduleListener;
import com.yan.listener.MyTriggerListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2016-12-07
 * Time: 17:31
 */
public class QuartzListenerTest {

    public static void main(String[] args) throws Exception {

        JobKey jobKey = new JobKey("helloJob", "group1");
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity(jobKey).build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        // Listener attached to jobKey
//        scheduler.getListenerManager().addJobListener(new MyJobListener(), KeyMatcher.keyEquals(jobKey));
//        scheduler.getListenerManager().addJobListener(new MyJobListener(), GroupMatcher.jobGroupEquals("group1")); // 给一个group下的所有job添加一个JobListener
//        scheduler.getListenerManager().addJobListener(new MyJobListener(), OrMatcher.or(GroupMatcher.jobGroupEquals("group1"), GroupMatcher.jobGroupEquals("group2"))); // 给两个group下的所有job添加一个JobListener
        scheduler.getListenerManager().addJobListener(new MyJobListener(), EverythingMatcher.allJobs()); // 给所有的job添加一个JobListener

        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener(), EverythingMatcher.allTriggers());

        scheduler.getListenerManager().addSchedulerListener(new MyScheduleListener());

        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
