package com.yan.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Administrator
 * Date: 2017/4/11
 * Time: 12:32
 */
public class MyTriggerListener implements TriggerListener {
    public static final String LISTENER_NAME = "MyTriggerListener";

    public String getName() {
        return LISTENER_NAME;
    }

    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        System.out.println("triggerFired");
    }

    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    public void triggerMisfired(Trigger trigger) {
        System.out.println("triggerMisfired");
    }

    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        System.out.println("triggerComplete");
    }
}
