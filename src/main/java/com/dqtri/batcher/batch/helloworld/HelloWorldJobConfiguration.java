package com.dqtri.batcher.batch.helloworld;

import com.dqtri.batcher.annotation.DemoTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.dqtri.batcher.annotation.utils.ISO8601DateSerializer.ISO8601_DATE_PATTERN;

@Slf4j
@Configuration
public class HelloWorldJobConfiguration {

    public static final String HELLO_WORLD_JOB_NAME = "helloWorldJob";

    @DemoTag(value = "Demo 002")
    @Bean
    public Job helloWorldJob(JobRepository jobRepository,
                             JobExecutionListener jobExecutionListener,
                             Step helloWorldStep,
                             Step helloDeadlineStep) {
        return new JobBuilder(HELLO_WORLD_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
//                .incrementer(new SampleIncrementer())
                .start(helloWorldStep)
                .next(helloDeadlineStep)
                .listener(jobExecutionListener)
                .preventRestart() //Demo 005
                .build();
    }

    @Bean
    public Step helloWorldStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Hello world step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
            log.info("Hello world!");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @DemoTag(value = "Demo 006", description = "Config basic step")
    @Bean
    public Step helloDeadlineStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Hello Deadline step", jobRepository)
                .tasklet(this::helloDeadlineTasklet, transactionManager)
                .build();
    }

    private RepeatStatus helloDeadlineTasklet(StepContribution contribution, ChunkContext chunkContext) throws InterruptedException {
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        Date executedDate = jobParameters.getDate("executed_date");
        SimpleDateFormat formatter = new SimpleDateFormat(ISO8601_DATE_PATTERN, Locale.ENGLISH);
        log.info("Hello Deadline! " + formatter.format(executedDate));
//        Thread.sleep(14400); //TODO: just for fake a processing
//        throw new  RuntimeException("Something's wrong!");
        log.info("Bye Deadline! " + formatter.format(new Date()));
        return RepeatStatus.FINISHED;
    }
}
