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

            testSimpleTrigger();
//            testJobDataMap();
//            testCalendar();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private static void testSimpleTrigger() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("helloJob", "group1").build();
        Trigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleTrigger", "group1")
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()) // 每隔5秒执行一次，永久重复
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(2)) // 每隔3秒执行一次，重复两次，即总共执行3次
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).endAt(DateBuilder.dateOf(10, 43, 0)) // 立即触发，每5秒执行一次，直到10:43
//                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.SECOND)) // 5秒后出发，仅执行一次
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(2).repeatForever()).startAt(DateBuilder.evenHourDate(null)) // 在下一小时整点触发，每个2小时执行一次，一直重复
                .build();
        scheduler.scheduleJob(job, simpleTrigger);
    }

    /**
     * Cron表达式用于配置CronTrigger的实例，它由7个字段组成，字段之间由空格分开，它们表示的含义如下：
     * 1.秒 (Seconds) 2.分钟 (Minutes) 3.小时 (Hours) 4.日(一个月的一天) (Day-of-Month) 5.月份 (Month) 6.周(一周的一天) (Day-of-Week) 7.年份(可选的) (Year)
     * <p>
     * 0 0/5 * * * ?                    ---             每隔5分钟执行一次
     * 10 0/5 * * * ?                   ---             每隔5分钟，且在该分钟的第10秒执行（如10:00:10 am, 10:05:10 am等）
     * 0 30 10-13 ? * WED,FRI           ---             每周三和周五的10:30, 11:30, 12:30和13:30执行
     * 0 0/30 8-9 5,20 * ?              ---             在每个月的第5天和第20天，上午8点到上午10点之间每隔半小时执行。注意：该trigger不会在上午10点执行，只在上午8:00, 8:30, 9:00和9:30执行
     * </p>
     *
     * @throws SchedulerException
     */
    private static void testCronTrigger() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("helloJob", "group1").build();
        Trigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("cronTrigger", "group1")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")) // 每5秒执行一次
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?")) // 每天的上午8点到下午5点之间每隔2分钟执行一次
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 42).withMisfireHandlingInstructionFireAndProceed()) // 每天上午10:42执行，设置错发策略为立即触发
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
