package com.dqtri.batcher.batch.resource;

import com.dqtri.batcher.audit.ActionType;
import com.dqtri.batcher.audit.AuditAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResourceScheduler {
    private final ApplicationContext applicationContext;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "${batch-job.resource.scheduler.cron:0 0 0 * * *}")
    @AuditAction(value = "resource_job_scheduled", type = ActionType.SCHEDULER)
    public void scheduled() throws Exception {
        Date now = new Date();
        log.info(String.format("### ResourceJob scheduled at: %s ###", now));
        Job jobToRun = applicationContext.getBean("resourceJob", Job.class);
        jobLauncher.run(jobToRun, getJobParameters(now));
    }

    private JobParameters getJobParameters(Date now) {
        JobParametersBuilder jobParamsBuilder = new JobParametersBuilder();
        jobParamsBuilder.addDate("executed_date", now);
        return jobParamsBuilder.toJobParameters();
    }
}
