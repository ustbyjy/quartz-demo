package com.yan.job;

import org.quartz.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2017-04-10
 * Time: 22:54
 */
public class DumbJob implements Job {

    private String jobSays;
    private float myFloatValue;

    public DumbJob() {

    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        // 使用JobDataMap获取
        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");

        // 使用JobFactory实现数据的自动“注入”
        String jobSays2 = getJobSays();
        float myFloatValue2 = getMyFloatValue();

        System.err.println("1-Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
        System.err.println("2-Instance " + key + " of DumbJob says: " + jobSays2 + ", and val is: " + myFloatValue2);
    }

    public String getJobSays() {
        return jobSays;
    }

    public void setJobSays(String jobSays) {
        this.jobSays = jobSays;
    }

    public float getMyFloatValue() {
        return myFloatValue;
    }

    public void setMyFloatValue(float myFloatValue) {
        this.myFloatValue = myFloatValue;
    }
}
