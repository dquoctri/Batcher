package com.dqtri.batcher.batch.resource.listener;

import com.dqtri.batcher.model.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceItemReadListener implements ItemReadListener<Resource> {
    @Override
    public void beforeRead() {
        log.info("[ResourceItemReadListener] beforeRead");
    }

    @Override
    public void afterRead(Resource item) {
        log.info("[ResourceItemReadListener] afterRead");
    }

    @Override
    public void onReadError(Exception ex) {
        log.error("[ResourceItemReadListener] onReadError", ex);
    }
}
