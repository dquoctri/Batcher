package com.dqtri.batcher.batch.resource.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("[ResourceJobExecutionListener] beforeJob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("[ResourceJobExecutionListener] afterJob");
    }
}
