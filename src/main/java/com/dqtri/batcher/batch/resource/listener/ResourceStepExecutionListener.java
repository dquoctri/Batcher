package com.dqtri.batcher.batch.resource.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceStepExecutionListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("[ResourceStepExecutionListener] beforeJob");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("[ResourceStepExecutionListener] afterStep");
        return stepExecution.getExitStatus();
    }
}
