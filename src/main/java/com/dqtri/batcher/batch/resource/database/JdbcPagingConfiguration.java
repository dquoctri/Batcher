package com.dqtri.batcher.batch.resource.database;

import com.dqtri.batcher.model.Resource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JdbcPagingConfiguration {
    @Bean
    public JdbcPagingItemReader<Resource> dataSourceItemReader(DataSource dataSource, PagingQueryProvider queryProvider) {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("status", "ACTIVE");
        return new JdbcPagingItemReaderBuilder<Resource>()
                .name("queryProviderReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider)
                .parameterValues(parameterValues)
                .rowMapper(new ResourceRowMapper())
                .pageSize(3)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Resource> managerFactorItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Resource>()
                .name("managerFactoryReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select r from Resource r")
                .pageSize(3)
                .build();
    }

    @Bean
    public PagingQueryProvider queryProvider(DataSource dataSource) throws Exception {
        SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
        provider.setDataSource(dataSource);
        provider.setSelectClause("select pk, content, status");
        provider.setFromClause("from resource");
        provider.setWhereClause("where status=:status");
        provider.setSortKey("pk");
        return provider.getObject();
    }
}
