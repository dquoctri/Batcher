package com.dqtri.batcher.batch.controller;

import com.dqtri.batcher.annotation.DemoTag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.Set;

//Interface Driven Controllers in Spring
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/jobs")
public class BatchJobController implements BatchJobOperations {

    private final JobLauncher jobLauncher;
    private final TaskExecutorJobLauncher taskExecutorJobLauncher;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;
    private final ApplicationContext applicationContext;

    @DemoTag(value = "Demo 003", description = "Launch from an HTTP request")
    @Override
    public void triggerBatchJob(String name, Map<String, String> params) throws Exception{
        try {
            Job jobToRun = applicationContext.getBean(name, Job.class);
            jobLauncher.run(jobToRun, buildJobParameters(jobToRun, params));
        } catch (BeansException ex) {
            throw new EntityNotFoundException(String.format(NOT_FOUND_JOB_NAME_MSG, name));
        }
    }

    @DemoTag(value = "Demo 004", description = "Running Jobs asynchronously")
    @Override
    public void triggerBatchJobAsync(String name, Map<String, String> params) throws Exception {
        try {
            Job jobToRun = applicationContext.getBean(name, Job.class);
            taskExecutorJobLauncher.run(jobToRun, buildJobParameters(jobToRun, params));
        } catch (BeansException ex) {
            throw new EntityNotFoundException(String.format(NOT_FOUND_JOB_NAME_MSG, name));
        }
    }

    private JobParameters buildJobParameters(Job jobToRun, Map<String, String> paramMap) {
        //A JobExplorer is required to get next job parameters
        JobParametersBuilder builder = new JobParametersBuilder(jobExplorer);
        if (jobToRun.getJobParametersIncrementer() != null) {
            JobParametersBuilder nextJobParameters = builder.getNextJobParameters(jobToRun);
            builder.addJobParameters(nextJobParameters.toJobParameters());
        }
        paramMap.forEach(builder::addString);
        builder.addDate("executed_date", new Date()); // override if provided "executed_date"
        return builder.toJobParameters();
    }

    @DemoTag(value = "Demo 005", description = "Job Operator CRUD and stop a job")
    @Override
    public boolean interruptBatchJob(String name) throws Exception {
        log.info(String.format("Interrupt batch job [%s]", name));
        try {
            Set<String> jobNames = jobOperator.getJobNames();
            boolean contains = jobNames.contains(name);
            if (!contains) {
                return false;
            }
            Set<Long> executions = jobOperator.getRunningExecutions(name);
            if (executions.isEmpty()) {
                return false;
            }
            return jobOperator.stop(executions.iterator().next());
        } catch (NoSuchJobException ex) {
            return false; //TODO: No content
        }
    }

    private static final String NOT_FOUND_JOB_NAME_MSG = "Not found job name %s";
}
