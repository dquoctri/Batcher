package com.dqtri.batcher.batch.helloworld;

import com.dqtri.batcher.annotation.DemoTag;
import io.micrometer.core.instrument.config.validate.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepJobConfiguration {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public static final Logger logger = LoggerFactory.getLogger(StepJobConfiguration.class);

    @DemoTag(value = "Demo 008", description = "Config steps")
    @Bean
    public Job job1() {
        return new JobBuilder("job1", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .next(taskletStep())
//                .preventRestart()
                .build();
    }

    @Bean
    public Step taskletStep() {
        return new StepBuilder("tasklet step", jobRepository)
                .allowStartIfComplete(true)
                .tasklet((stepContribution, chunkContext) -> {
                    logger.info("This is first tasklet step");
                    logger.info("SEC = {}", chunkContext.getStepContext().getStepExecutionContext());
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }

    @DemoTag(value = "Demo 010", description = "Demo config chunk size")
    @Bean
    public Step chunkStep() {
        return new StepBuilder("chunk step", jobRepository)
                .<String, String>chunk(5, transactionManager) //With a commit-interval of 5
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .allowStartIfComplete(true)
                .startLimit(3)
                .faultTolerant()
                .skipLimit(2) //Configuring Skip Logic
                .skip(RuntimeException.class)
                .noSkip(Exception.class)
                .retryLimit(3) // Configuring Retry Logic
                .retry(NullPointerException.class)
                .noRollback(ValidationException.class) //Controlling Rollback
                .readerIsTransactionalQueue()//Transactional Readers
                .build();
    }

    @DemoTag(value = "Demo 009", description = "Config reader")
    @Bean
    public ItemReader<String> reader() {
        List<String> data = Arrays.asList("Byte", "Code", "Data", "Disk", "File", "Input", "Loop", "Logic", "Mode", "Node", "Port", "Query", "Ratio", "Root", "Route", "Scope", "Syntax", "Token", "Trace");
        return new ListItemReader<>(data);
    }

    @DemoTag(value = "Demo 009", description = "Config writer")
    @Bean
    public ItemWriter<String> writer() {
        return items -> {
            logger.info("Writing number of items: {}", items.size()); //5 items are processed within each transaction
            logger.info("Writing items: {}", items); //the list of aggregated items is passed to the ItemWriter
            throw new RuntimeException("Something's wrong!");
        };
    }

    @DemoTag(value = "Demo 009", description = "Config processor")
    @Bean
    public ItemProcessor<String, String> processor() {
        return item -> {
            logger.debug("Processor item: {}", item);
            return item + "ed";
        };
    }
}
