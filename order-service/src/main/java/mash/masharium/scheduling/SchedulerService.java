package mash.masharium.scheduling;

import lombok.RequiredArgsConstructor;
import mash.masharium.entity.Order;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.quartz.TriggerBuilder.newTrigger;

@Component
@RequiredArgsConstructor
public class SchedulerService {

    @Autowired
    private final Scheduler scheduler;


    public void scheduleJob(Order order, Class<? extends Job> jobClass) throws SchedulerException {
        LocalDateTime appointment = order.getLastModified().plusMinutes(30);

        String triggerName = "trigger for job_" + order.getId();

        Trigger trigger = createOneTimeTrigger(appointment, triggerName);
        JobDetail jobDetail = createJobDetail(jobClass, "job_" + order.getId());
        jobDetail.getJobDataMap().put("orderId", order.getId());

        scheduleJob(trigger, jobDetail);
    }

    public void unscheduleJob(Order order) throws SchedulerException {
        scheduler.unscheduleJob(new TriggerKey("trigger for job_" + order.getId()));
    }


    private JobDetail createJobDetail(Class<? extends Job> jobClass, String name) {
        return JobBuilder.newJob().ofType(jobClass)
                .requestRecovery()
                .withDescription(String.format("job: %s", jobClass.toString()))
                .withIdentity(name)
                .build();
    }

    private Trigger createOneTimeTrigger(LocalDateTime appointment, String triggerName) {
        return newTrigger()
                .withIdentity(triggerName)
                .startAt(DateBuilder.dateOf(
                        appointment.getHour(),
                        appointment.getMinute(),
                        0,
                        appointment.getDayOfMonth(),
                        appointment.getMonth().getValue(),
                        appointment.getYear()))
                .build();
    }

    private void scheduleJob(Trigger trigger, JobDetail job) throws SchedulerException {
        if(scheduler.checkExists(job.getKey())){
            scheduler.deleteJob(job.getKey());
        }
            scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }

}
