package com.yan.listener;

import org.quartz.*;

/**
 * Created with IntelliJ IDEA.
 * Description: scheduler相关的消息包括：job/trigger的增加、job/trigger的删除、scheduler内部发生的严重错误以及scheduler关闭的消息等
 * User: Administrator
 * Date: 2017/4/11
 * Time: 18:32
 */
public class MyScheduleListener implements SchedulerListener {

    public void jobScheduled(Trigger trigger) {
        System.out.println("jobScheduled");
    }

    public void jobUnscheduled(TriggerKey triggerKey) {
        System.out.println("jobUnscheduled");
    }

    public void triggerFinalized(Trigger trigger) {
        System.out.println("triggerFinalized");
    }

    public void triggerPaused(TriggerKey triggerKey) {
        System.out.println("triggerPaused");
    }

    public void triggersPaused(String triggerGroup) {
        System.out.println("triggersPaused");
    }

    public void triggerResumed(TriggerKey triggerKey) {
        System.out.println("triggerResumed");
    }

    public void triggersResumed(String triggerGroup) {
        System.out.println("triggersResumed");
    }

    public void jobAdded(JobDetail jobDetail) {
        System.out.println("jobAdded");
    }

    public void jobDeleted(JobKey jobKey) {
        System.out.println("jobDeleted");
    }

    public void jobPaused(JobKey jobKey) {
        System.out.println("jobPaused");
    }

    public void jobsPaused(String jobGroup) {
        System.out.println("jobsPaused");
    }

    public void jobResumed(JobKey jobKey) {
        System.out.println("jobResumed");
    }

    public void jobsResumed(String jobGroup) {
        System.out.println("jobsResumed");
    }

    public void schedulerError(String msg, SchedulerException cause) {
        System.out.println("schedulerError");
    }

    public void schedulerInStandbyMode() {
        System.out.println("schedulerInStandbyMode");
    }

    public void schedulerStarted() {
        System.out.println("schedulerStarted");
    }

    public void schedulerStarting() {
        System.out.println("schedulerStarting");
    }

    public void schedulerShutdown() {
        System.out.println("schedulerShutdown");
    }

    public void schedulerShuttingdown() {
        System.out.println("schedulerShuttingdown");
    }

    public void schedulingDataCleared() {
        System.out.println("schedulingDataCleared");
    }
}
