package com.dqtri.batcher.batch.resource.listener;

import com.dqtri.batcher.model.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceItemProcessListener implements ItemProcessListener<Resource, Resource> {
    @Override
    public void beforeProcess(Resource item) {
        log.info("[ResourceItemProcessListener] beforeProcess");
    }

    @Override
    public void afterProcess(Resource item, Resource result) {
        log.info("[ResourceItemProcessListener] afterProcess");
    }

    @Override
    public void onProcessError(Resource item, Exception e) {
        log.error("[ResourceItemProcessListener] onProcessError", e);
    }
}
