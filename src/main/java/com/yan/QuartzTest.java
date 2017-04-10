package com.yan;

import com.yan.job.DumbJob;
import com.yan.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 1、同一个分组下的Job或Trigger的名称必须唯一，即一个Job或Trigger的key由名称（name）和分组（group）组成。
 * User: Administrator
 * Date: 2016-12-07
 * Time: 16:17
 */
public class QuartzTest {
    private static Scheduler scheduler;

    public static void main(String[] args) {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

//            testSimpleTrigger();
//            testJobDataMap();
            testCalendar();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private static void testSimpleTrigger() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("helloJob", "group1").build();
        Trigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleTrigger", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                .build();
        scheduler.scheduleJob(job, simpleTrigger);
    }

    private static void testCronTrigger() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("helloJob", "group1").build();
        Trigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();
        scheduler.scheduleJob(job, simpleTrigger);
    }

    private static void testJobDataMap() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(DumbJob.class)
                .withIdentity("myJob", "group1") // name "myJob", group "group1"
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();
        Trigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleTrigger", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                .build();
        scheduler.scheduleJob(job, simpleTrigger);
    }

    private static void testCalendar() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myJob")
                .build();

        Date today = new Date();
        HolidayCalendar calendar = new HolidayCalendar();
        calendar.addExcludedDate(today);

        scheduler.addCalendar("myHolidays", calendar, false, false);

        Trigger t1 = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger")
                .forJob("myJob")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                .modifiedByCalendar("myHolidays") // but not on holidays
                .build();

        Trigger t2 = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger2")
                .forJob("myJob2")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(11, 30)) // execute job daily at 11:30
                .modifiedByCalendar("myHolidays") // but not on holidays
                .build();

        scheduler.scheduleJob(job, t1);
    }


}
