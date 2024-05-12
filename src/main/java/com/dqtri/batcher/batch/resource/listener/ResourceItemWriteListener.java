package com.dqtri.batcher.batch.resource.listener;

import com.dqtri.batcher.model.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceItemWriteListener implements ItemWriteListener<Resource> {
    @Override
    public void beforeWrite(Chunk<? extends Resource> items) {
        log.info("[ResourceItemWriteListener] beforeWrite");
    }

    @Override
    public void afterWrite(Chunk<? extends Resource> items) {
        log.info("[ResourceItemWriteListener] afterWrite");
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends Resource> items) {
        log.error("[ResourceItemWriteListener] onWriteError", exception);
    }
}
