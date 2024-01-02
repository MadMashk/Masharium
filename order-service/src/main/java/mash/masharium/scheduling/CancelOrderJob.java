package mash.masharium.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mash.masharium.service.OrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CancelOrderJob implements Job {

    private final OrderService orderService;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        UUID orderId = (UUID) context.getMergedJobDataMap().get("orderId");
        orderService.closeOrder(orderId);
    }
}
