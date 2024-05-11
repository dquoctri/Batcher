package com.dqtri.batcher.batch.resource.database;

import com.dqtri.batcher.annotation.DemoTag;
import com.dqtri.batcher.batch.resource.xml.TradeItemWriteListener;
import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Types;

@Slf4j
@Configuration
public class JdbcCursorConfiguration {

    @DemoTag(value = "Demo 111", description = "Demo config database")
    @Bean
    public Job fdbcCursorJob(JobRepository jobRepository, Step fdbcCursorChunkStep) {
        return new JobBuilder("fdbc Cursor Job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fdbcCursorChunkStep)
                .build();
    }

    @DemoTag(value = "Demo 111", description = "Demo config database")
    @Bean
    public Step fdbcCursorChunkStep(JobRepository jobRepository,
                             PlatformTransactionManager transactionManager,
                                    JdbcPagingItemReader<Resource> dataSourceItemReader,
                              JdbcBatchItemWriter<Resource> jdbcBatchItemWriter,
                             TradeItemWriteListener listener) {
        return new StepBuilder("fdbcCursorChunkStep", jobRepository)
                .<Resource, Resource>chunk(2, transactionManager) //With a commit-interval of 5
                .reader(dataSourceItemReader)
                .writer(jdbcBatchItemWriter)
                .processor(resourceProcessor())
                .listener(listener)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Resource> fdbcCursorItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Resource>()
                .dataSource(dataSource)
                .name("fdbcCursorItemReader")
                .sql("select PK, CONTENT, STATUS from RESOURCE")
                .rowMapper(new ResourceRowMapper())
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Resource> jdbcBatchItemWriter(DataSource dataSource) {
        String sql = "UPDATE RESOURCE set content = :content, status = :status where pk = :pk";
        return new JdbcBatchItemWriterBuilder<Resource>()
                .dataSource(dataSource)
                .sql(sql)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>() {
                    @Override
                    public SqlParameterSource createSqlParameterSource(Resource item) {
                        return new BeanPropertySqlParameterSource(item) {
                            @Override
                            public int getSqlType(String paramName) {
                                if (paramName.equalsIgnoreCase("status")) {
                                    return Types.VARCHAR;
                                }
                                return super.getSqlType(paramName);
                            }
                        };
                    }
                })
                .assertUpdates(true)
                .build();
    }

    public ItemProcessor<Resource, Resource> resourceProcessor() {
        return item -> {
            log.info("Processor item: {}", item);
            if (Status.ACTIVE.equals(item.getStatus())){
                item.setStatus(Status.LOCKED);
            } else if (Status.LOCKED.equals(item.getStatus())){
                item.setStatus(Status.ACTIVE);
            }
            return item;
        };
    }

}
