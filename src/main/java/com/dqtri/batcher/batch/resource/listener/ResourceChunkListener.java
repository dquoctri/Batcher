package com.dqtri.batcher.batch.resource.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ResourceChunkListener implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext context) {
        ChunkListener.super.beforeChunk(context);
    }

    @Override
    public void afterChunk(ChunkContext context) {
        ChunkListener.super.afterChunk(context);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        ChunkListener.super.afterChunkError(context);
    }
}
