package com.dqtri.batcher.batch.config;

import com.dqtri.batcher.annotation.DemoTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import java.util.Properties;

import static java.lang.Thread.MIN_PRIORITY;

@Slf4j
@Configuration
public class BatchJobConfiguration {

    @DemoTag(value = "Demo 002", description = "demo job luncher")
    @Bean
    public TaskExecutorJobLauncher taskExecutorJobLauncher(JobRepository jobRepository, SimpleAsyncTaskExecutor simpleAsyncTaskExecutor) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(simpleAsyncTaskExecutor);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor("async-task-");
        simpleAsyncTaskExecutor.setTaskTerminationTimeout(144);
        simpleAsyncTaskExecutor.setConcurrencyLimit(5);
        simpleAsyncTaskExecutor.setThreadPriority(MIN_PRIORITY);
        return simpleAsyncTaskExecutor;
    }

    @Bean
    public TransactionProxyFactoryBean baseProxy(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        TransactionProxyFactoryBean transactionProxyFactoryBean = new TransactionProxyFactoryBean();
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED"); //Support a current transaction, create a new one if none exists.
        transactionProxyFactoryBean.setTransactionAttributes(transactionAttributes);
        transactionProxyFactoryBean.setTarget(jobRepository);
        transactionProxyFactoryBean.setTransactionManager(transactionManager);
        return transactionProxyFactoryBean;
    }

    /**
     * All injected dependencies for this bean are provided by the @EnableBatchProcessing
     * infrastructure out of the box.
     */
    @DemoTag(value = "Demo 005", description = "custom an simpleJobOperator")
    @Bean
    public SimpleJobOperator simpleJobOperator(JobExplorer jobExplorer,
                                         JobRepository jobRepository,
                                         JobRegistry jobRegistry,
                                         JobLauncher jobLauncher) {

        SimpleJobOperator jobOperator = new SimpleJobOperator();
        jobOperator.setJobExplorer(jobExplorer);
        jobOperator.setJobRepository(jobRepository);
        jobOperator.setJobRegistry(jobRegistry);
        jobOperator.setJobLauncher(jobLauncher);

        return jobOperator;
    }
}
