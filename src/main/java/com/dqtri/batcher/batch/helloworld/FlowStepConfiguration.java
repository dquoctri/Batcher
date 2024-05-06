package com.dqtri.batcher.batch.helloworld;

import com.dqtri.batcher.annotation.DemoTag;
import com.dqtri.batcher.batch.config.sample.MyDecider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@DemoTag(value = "Demo 011", description = "Config Step flow")
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FlowStepConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job firstJob() {
        return new JobBuilder("first job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepA())
                .next(stepB())
                .next(stepC())
                .build();
    }

    @Bean
    public Job secondJob() {
        return new JobBuilder("second job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepA())
                .on("*").to(stepB())
                .from(stepA()).on("FAILED").to(stepC())
                .end()
                .build();
    }

    @DemoTag(value = "Demo 008", description = "Config steps")
    @Bean
    public Job thirdJob() {
        return new JobBuilder("third job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepA()).on("FAILED").end()
                .from(stepA()).on("COMPLETED WITH SKIPS").to(errorPrint1())
                .from(stepA()).on("*").to(stepB())
                .end()
                .build();
    }

    @DemoTag(value = "Demo 008", description = "Config steps")
    @Bean
    public Job fo4rJob() {
        return new JobBuilder("fo4r job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepA())
                .next(stepB())
                .on("FAILED").end()
                .from(stepB()).on("*").to(stepC())
                .end()
                .build();
    }

    @DemoTag(value = "Demo 008", description = "Config steps")
    @Bean
    public Job fiveJob() {
        return new JobBuilder("five job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepA())
                .next(stepB())
                .on("FAILED").fail()
                .from(stepB()).on("*").to(stepC())
                .end()
                .build();
    }

    @DemoTag(value = "Demo 008", description = "Config steps")
    @Bean
    public Job sixJob() {
        return new JobBuilder("six job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepA())
                .on("COMPLETED").stopAndRestart(stepB())
                .end()
                .build();
    }

    @DemoTag(value = "Demo 008", description = "Config steps")
    @Bean
    public Job sevenJob(MyDecider decider) {
        return new JobBuilder("seven job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stepA())
                .next(decider).on("FAILED").to(stepB())
                .from(decider).on("COMPLETED").to(stepC())
                .end()
                .build();
    }

    @Bean
    public SimpleFlow flow1(Step stepA, Step stepB) {
        return new FlowBuilder<SimpleFlow>("flow1")
                .start(stepA)
                .next(stepB)
                .build();
    }

    @Bean
    public SimpleFlow flow2(Step stepC) {
        return new FlowBuilder<SimpleFlow>("flow2")
                .start(stepC)
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, SimpleFlow flow1, SimpleFlow flow2, Step stepD) {
        return new JobBuilder("job", jobRepository)
                .start(flow1)
                .split(new SimpleAsyncTaskExecutor())
                .add(flow2)
                .next(stepD)
                .end()
                .build();
    }

    @Bean
    public Step stepA() {
        return new StepBuilder("Step A", jobRepository)
                .allowStartIfComplete(true)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("This is Step A");
//                    throw new RuntimeException("Something's wrong v2");
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }

    @Bean
    public Step stepB() {
        return new StepBuilder("Step B", jobRepository)
                .allowStartIfComplete(true)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("This is Step B");
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }

    @Bean
    public Step stepC() {
        return new StepBuilder("Step C", jobRepository)
                .allowStartIfComplete(true)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("This is Step C");
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }

    @Bean
    public Step stepD() {
        return new StepBuilder("Step D", jobRepository)
                .allowStartIfComplete(true)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("This is Step D");
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }

    @Bean
    public Step stepE() {
        return new StepBuilder("Step E", jobRepository)
                .allowStartIfComplete(true)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("This is Step E");
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }

    @Bean
    public Step errorPrint1() {
        return new StepBuilder("errorPrint1", jobRepository)
                .allowStartIfComplete(true)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("This is Step errorPrint1");
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }
}
