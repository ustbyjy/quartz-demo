package com.yan;

import com.yan.job.HelloJob;
import com.yan.listener.HelloJobListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2016-12-07
 * Time: 17:31
 */
public class QuartzListenerTest {
    public static void main(String[] args) throws Exception {

        JobKey jobKey = new JobKey("dummyJobName", "group1");
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity(jobKey).build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dummyTriggerName", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        // Listener attached to jobKey
        scheduler.getListenerManager().addJobListener(
                new HelloJobListener(), KeyMatcher.keyEquals(jobKey)
        );

        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
