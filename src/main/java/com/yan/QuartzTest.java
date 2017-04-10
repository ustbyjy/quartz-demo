package com.yan;

import com.yan.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2016-12-07
 * Time: 16:17
 */
public class QuartzTest {
    public static void main(String[] args) {
        Scheduler scheduler;
        JobDetail job;
        Trigger simpleTrigger;
        Trigger cronTrigger;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            job = JobBuilder.newJob(HelloJob.class).withIdentity("helloJob", "group1").build();
            // SimpleTrigger
            simpleTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("simpleTrigger", "group1")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    .build();

            // CronTrigger
            cronTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("dummyTriggerName", "group1")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .build();

            scheduler.scheduleJob(job, simpleTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
