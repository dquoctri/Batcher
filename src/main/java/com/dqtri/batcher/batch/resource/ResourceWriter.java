package com.dqtri.batcher.batch.resource;

import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@StepScope
@Component
public class ResourceWriter extends RepositoryItemWriter<Resource> {
    private final ResourceRepository resourceRepository;

    @Override
    public void write(Chunk<? extends Resource> chunk) throws Exception {
        log.info("[ResourceWriter] write {}", chunk.size());
        resourceRepository.saveAll(chunk);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRepository(resourceRepository);
        setMethodName("saveAll");
        super.afterPropertiesSet();
    }

    @BeforeWrite
    public void beforeWrite(Chunk<? extends Resource> items) {
        log.info("[ResourceWriter] beforeWrite");
    }

    @AfterWrite
    public void afterWrite(Chunk<? extends Resource> items) {
        log.info("[ResourceWriter] afterWrite");
    }

    @OnWriteError
    public void onWriteError(Exception exception, Chunk<? extends Resource> items) {
        log.error("[ResourceWriter] onWriteError", exception);
    }

    @AfterStep
    public ExitStatus ResourceWriter(StepExecution stepExecution) {
        log.info("[ResourceWriter] afterStep");
        return stepExecution.getExitStatus();
    }
}
