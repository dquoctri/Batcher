package com.dqtri.batcher.batch.resource.listener;

import com.dqtri.batcher.model.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResourceSkipListener implements SkipListener<Resource, Resource> {
    @Override
    public void onSkipInRead(Throwable t) {
        log.info("[ResourceSkipListener] onSkipInRead");
    }

    @Override
    public void onSkipInWrite(Resource item, Throwable t) {
        log.info("[ResourceSkipListener] onSkipInWrite");
    }

    @Override
    public void onSkipInProcess(Resource item, Throwable t) {
        log.info("[ResourceSkipListener] onSkipInProcess");
    }
}
