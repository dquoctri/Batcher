package com.dqtri.batcher.batch.controller;

import com.dqtri.batcher.annotation.DemoTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Interface Driven Controllers in Spring
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/executions")
public class ExecutionController implements ExecutionOperations {

    private final JobOperator jobOperator;

    @DemoTag(value = "Demo 005", description = "Job Operator CRUD and restart a job")
    @Override
    public void restartExecution(Long executionId) throws Exception {
        try {
            log.info("Restart " + jobOperator.getSummary(executionId));
            jobOperator.restart(executionId);
        } catch (NoSuchJobException ex) {
            log.error("not found!");
        }
    }
}
