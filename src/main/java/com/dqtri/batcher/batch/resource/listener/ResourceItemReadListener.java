package com.dqtri.batcher.batch.resource.listener;

import com.dqtri.batcher.model.Resource;
import org.springframework.batch.core.ItemReadListener;

public class ResourceItemReadListener implements ItemReadListener<Resource> {
    @Override
    public void beforeRead() {
        ItemReadListener.super.beforeRead();
    }

    @Override
    public void afterRead(Resource item) {
        ItemReadListener.super.afterRead(item);
    }

    @Override
    public void onReadError(Exception ex) {
        ItemReadListener.super.onReadError(ex);
    }
}
