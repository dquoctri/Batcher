package com.dqtri.batcher.batch.resource.listener;

import com.dqtri.batcher.model.Resource;
import org.springframework.batch.core.ItemProcessListener;

public class ResourceItemProcessListener implements ItemProcessListener<Resource, Resource> {
    @Override
    public void beforeProcess(Resource item) {
        ItemProcessListener.super.beforeProcess(item);
    }

    @Override
    public void afterProcess(Resource item, Resource result) {
        ItemProcessListener.super.afterProcess(item, result);
    }

    @Override
    public void onProcessError(Resource item, Exception e) {
        ItemProcessListener.super.onProcessError(item, e);
    }
}
