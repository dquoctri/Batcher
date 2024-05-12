package com.dqtri.batcher.batch.database;

import com.dqtri.batcher.model.Resource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.orm.JpaNamedQueryProvider;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaCursorConfiguration {

    @Bean
    public JpaCursorItemReader<Resource> jpaCursorQueryStringItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaCursorItemReaderBuilder<Resource>()
                .name("jpaCursorQueryStringItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT r FROM Resource r")
                .build();
    }

    @Bean
    public JpaCursorItemReader<Resource> jpaCursorQueryProviderItemReader(EntityManagerFactory entityManagerFactory) {
        JpaNativeQueryProvider<Resource> jpaQueryProvider = new JpaNativeQueryProvider<>();
        String sqlQuery = "SELECT * FROM dqtri.resource r";
        jpaQueryProvider.setSqlQuery(sqlQuery);
        jpaQueryProvider.setEntityClass(Resource.class);

        JpaNamedQueryProvider<Resource> jpaNamedQueryProvider = new JpaNamedQueryProvider<>();
        jpaNamedQueryProvider.setNamedQuery("Resource.findAllResources");

        JpaNamedQueryProvider<Resource> jpaNamedSqlQueryProvider = new JpaNamedQueryProvider<>();
        jpaNamedQueryProvider.setNamedQuery("Resource.countAllResourcesWithSQL");

        return new JpaCursorItemReaderBuilder<Resource>()
                .name("fdbcCursor3Reader")
                .entityManagerFactory(entityManagerFactory)
                .queryProvider(jpaQueryProvider)
                .build();
    }
}
