package com.dqtri.batcher.batch.xml;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TradeItemWriteListener implements ItemWriteListener<Trade> {
    @Override
    public void beforeWrite(Chunk<? extends Trade> items) {
        log.info("Processing ...: " + items);
        ItemWriteListener.super.beforeWrite(items);
    }

    @Override
    public void afterWrite(Chunk<? extends Trade> items) {
        log.info("Processed: " + items);
        ItemWriteListener.super.afterWrite(items);
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends Trade> items) {
        ItemWriteListener.super.onWriteError(exception, items);
    }
}
