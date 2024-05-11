package com.dqtri.batcher.batch.resource.listener;

import com.dqtri.batcher.model.Resource;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;

public class ResourceItemWriteListener implements ItemWriteListener<Resource> {
    @Override
    public void beforeWrite(Chunk<? extends Resource> items) {
        ItemWriteListener.super.beforeWrite(items);
    }

    @Override
    public void afterWrite(Chunk<? extends Resource> items) {
        ItemWriteListener.super.afterWrite(items);
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends Resource> items) {
        ItemWriteListener.super.onWriteError(exception, items);
    }
}
