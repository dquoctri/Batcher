package com.dqtri.batcher.batch.config.sample;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SampleCompositeJobExecutionListener extends CompositeJobExecutionListener {

    @Override
    public void beforeJob(@NotNull JobExecution jobExecution) {
        JobInstance jobInstance = jobExecution.getJobInstance();
        String jobName = jobInstance.getJobName();
        Long jobExecutionId = jobExecution.getId();
        jobExecution.getExecutionContext().put("jobExecutionId", jobExecutionId);
        log.info(String.format("Starting job: [%s] with id: %s", jobName, jobExecutionId));
    }

    @Override
    public void afterJob(@NotNull JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        log.info(String.format("Finished job: [%s] with id: %s", jobName, jobExecution.getId()));
        log.info(String.format("Job [%s] status: %s", jobName, jobExecution.getExitStatus()));
        List<StepExecution> stepExecutionList = new ArrayList<>(jobExecution.getStepExecutions());
        List<String> jobNames = stepExecutionList.stream().map(StepExecution::getStepName).toList();
        log.info(String.format("List of step: %s", jobNames));
    }

}
