package com.yan.job;

import com.yan.util.DateUtil;
import org.quartz.*;
import org.quartz.impl.triggers.SimpleTriggerImpl;
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
@DisallowConcurrentExecution
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
        try {
            Scheduler scheduler = jobExecutionContext.getScheduler();
            SimpleTriggerImpl trigger = (SimpleTriggerImpl) jobExecutionContext.getTrigger();
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            Date startDate = (Date) trigger.getJobDataMap().get("startDate");
            Date endDate = (Date) trigger.getJobDataMap().get("endDate");
            Integer type = (Integer) trigger.getJobDataMap().get("type");

            System.out.println("任务开始 " + DateUtil.date2Str(new Date(), DateUtil.FORMAT_DEFAULT_DATE_TIME) + " Hello Quartz!!!!!! " + jobExecutionContext.getNextFireTime());
            System.out.println("开始时间 " + DateUtil.date2Str(startDate, DateUtil.FORMAT_DEFAULT_DATE_TIME));
            System.out.println("结束时间 " + DateUtil.date2Str(endDate, DateUtil.FORMAT_DEFAULT_DATE_TIME));

            Thread.sleep(3000);

            java.util.Calendar startCalendar = java.util.Calendar.getInstance();
            java.util.Calendar endCalendar = java.util.Calendar.getInstance();
            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);
            int addDayNum = 0;
            switch (type) {
                case 1:
                    addDayNum = 1;
                    break;
                case 2:
                    addDayNum = DateUtil.getDayOfWeek(startDate) == 5 ? 3 : 1;
                    break;
                case 3:
                    addDayNum = DateUtil.getDayOfWeek(startDate) == 7 ? 6 : 1;
                    break;
            }
            startCalendar.add(java.util.Calendar.DAY_OF_YEAR, addDayNum);
            endCalendar.add(java.util.Calendar.DAY_OF_YEAR, addDayNum);
            trigger.setEndTime(endCalendar.getTime());
            trigger.setStartTime(startCalendar.getTime());
            trigger.getJobDataMap().put("startDate", startCalendar.getTime());
            trigger.getJobDataMap().put("endDate", endCalendar.getTime());
            jobExecutionContext.getScheduler().rescheduleJob(trigger.getKey(), trigger);

//            long now = System.currentTimeMillis();
//            if (now < trigger.getEndTime().getTime()) {
//                trigger.setStartTime(new Date(now));
//                jobExecutionContext.getScheduler().rescheduleJob(trigger.getKey(), trigger);
//            }

            System.out.println("重新规划成功...");
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Throw exception for testing
//        throw new JobExecutionException("Testing Exception");

    }
}
