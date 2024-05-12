package com.dqtri.batcher.batch.resource;

import com.dqtri.batcher.batch.resource.listener.ResourceChunkListener;
import com.dqtri.batcher.batch.resource.listener.ResourceItemProcessListener;
import com.dqtri.batcher.batch.resource.listener.ResourceItemReadListener;
import com.dqtri.batcher.batch.resource.listener.ResourceItemWriteListener;
import com.dqtri.batcher.batch.resource.listener.ResourceJobExecutionListener;
import com.dqtri.batcher.batch.resource.listener.ResourceStepExecutionListener;
import com.dqtri.batcher.model.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class ResourceJobConfiguration {
    public static final String JOB_NAME = "resourceJob";

    @Value("${batch-job.resource.chunk-size:2}")
    private static final int chunkSize = 2;

    @Bean
    public Job resourceJob(JobRepository jobRepository,
                           Step resourceChunkStep,
                           Step resourceTaskletStep,
                           ResourceJobExecutionListener resourceJobExecutionListener) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(resourceChunkStep)
                .next(resourceTaskletStep)
                .listener(resourceJobExecutionListener)
                .preventRestart()
                .build();
    }

    @Bean
    public Step resourceChunkStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ResourceReader resourceReader,
                                    ResourceWriter resourceWriter,
                                    ResourceProcessor resourceProcessor,
                                    ResourceChunkListener resourceChunkListener,
                                    ResourceStepExecutionListener resourceStepExecutionListener,
                                    ResourceItemReadListener resourceItemReadListener,
                                    ResourceItemProcessListener resourceItemProcessListener,
                                    ResourceItemWriteListener resourceItemWriteListener) {
        return new StepBuilder("Resource Chunk Step", jobRepository)
                .<Resource, Resource>chunk(chunkSize, transactionManager)
                .reader(resourceReader)
                .writer(resourceWriter)
                .processor(resourceProcessor)
                .listener(resourceChunkListener)
                .listener(resourceStepExecutionListener)
                .listener(resourceItemReadListener)
                .listener(resourceItemProcessListener)
                .listener(resourceItemWriteListener)
                .build();
    }

    @Bean
    public Step resourceTaskletStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager,
                                    ResourceTasklet resourceTasklet,
                                    ResourceStepExecutionListener resourceStepExecutionListener) {
        return new StepBuilder("Resource Tasklet Step", jobRepository)
                .tasklet(resourceTasklet, transactionManager)
                .listener(resourceStepExecutionListener)
                .build();
    }
}
