package com.dqtri.batcher.batch.helloworld;

import com.dqtri.batcher.annotation.DemoTag;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class EndOfDayJobConfiguration {

    @DemoTag(value = "Demo 001", description = "Config Job example")
    @JobScope
    @Bean
    public Job endOfDay(JobRepository jobRepository, Step step1, Step step2, Step step3) {
        return new JobBuilder("endOfDay", jobRepository)
                .start(step1)
                .next(step2)
                .next(step3)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("bastep-async-");
    }

    @Bean
    @StepScope
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, TaskExecutor taskExecutor) {

        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> null, transactionManager)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .tasklet((contribution, chunkContext) -> null, transactionManager)
                .build();
    }

    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step3", jobRepository)
                .tasklet((contribution, chunkContext) -> null, transactionManager)
                .build();
    }
}
