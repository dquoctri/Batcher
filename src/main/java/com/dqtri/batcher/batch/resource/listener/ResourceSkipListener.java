package com.dqtri.batcher.batch.resource.listener;

import com.dqtri.batcher.model.Resource;
import org.springframework.batch.core.SkipListener;

public class ResourceSkipListener implements SkipListener<Resource, Resource> {
    @Override
    public void onSkipInRead(Throwable t) {
        SkipListener.super.onSkipInRead(t);
    }

    @Override
    public void onSkipInWrite(Resource item, Throwable t) {
        SkipListener.super.onSkipInWrite(item, t);
    }

    @Override
    public void onSkipInProcess(Resource item, Throwable t) {
        SkipListener.super.onSkipInProcess(item, t);
    }
}
