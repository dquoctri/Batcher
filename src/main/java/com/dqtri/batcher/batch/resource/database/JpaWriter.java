package com.dqtri.batcher.batch.resource.database;

import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.repository.ResourceRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;

public class JpaWriter {
    @Bean
    public JpaItemWriter<Resource> jpaItemWriter1(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<Resource>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .clearPersistenceContext(true)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Resource> jpaItemWriter2(ResourceRepository repository) {
        return new RepositoryItemWriterBuilder<Resource>()
                .repository(repository)
                .methodName("saveAll")
                .build();
    }
}
