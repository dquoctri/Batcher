package com.dqtri.batcher.batch.database;

import com.dqtri.batcher.model.IResource;
import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import com.dqtri.batcher.repository.ResourceRepository;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.util.Map;

/**
 * https://docs.spring.io/spring-batch/docs/4.3.5/reference/html/index-single.html#listOfReadersAndWriters
 */
public class RepositoryConfiguration {

    /**
     * RepositoryItemReader cannot save all records
     * https://github.com/spring-projects/spring-batch/issues/1227
     * @param resourceRepository
     * @return
     */
    @Bean
    public RepositoryItemReader<Resource> jpaRepositoryItemReader(ResourceRepository resourceRepository) {
        return new RepositoryItemReaderBuilder<Resource>()
                .name("jpaRepositoryItemReader")
                .repository(resourceRepository)
                .methodName("findByStatus")
                .arguments(Status.ACTIVE)
                .pageSize(3)
                .sorts(Map.of("pk", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public RepositoryItemReader<IResource> jpaRepositoryNativeItemReader(ResourceRepository resourceRepository) {
        return new RepositoryItemReaderBuilder<IResource>()
                .name("jpaRepositoryNativeItemReader")
                .repository(resourceRepository)
                .methodName("findResourcesByStatus")
                .arguments(Status.ACTIVE)
                .sorts(Map.of("pk", Sort.Direction.ASC))
                .build();
    }
}
