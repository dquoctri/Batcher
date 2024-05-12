package com.dqtri.batcher.batch.resource.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceChunkListener implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("[ResourceChunkListener] beforeChunk");
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("[ResourceChunkListener] afterChunk");
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("[ResourceChunkListener] afterChunkError");
    }
}
