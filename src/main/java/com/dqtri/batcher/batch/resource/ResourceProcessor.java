package com.dqtri.batcher.batch.resource;

import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@StepScope
@Component
public class ResourceProcessor implements ItemProcessor<Resource, Resource> {
    @Override
    public Resource process(@NonNull Resource item) {
        log.info("[ResourceProcessor] process item {}", item.getPk());
        if (Status.ACTIVE.equals(item.getStatus())){
            item.setStatus(Status.LOCKED);
        } else if (Status.LOCKED.equals(item.getStatus())){
            item.setStatus(Status.ACTIVE);
        }
        return item;
    }

    @BeforeProcess
    public void beforeProcess(Resource item) {
        log.info("[ResourceProcessor] beforeProcess");
    }

    @AfterProcess
    public void afterProcess(Resource item, Resource result) {
        log.info("[ResourceProcessor] afterProcess");
    }

    @OnProcessError
    public void onProcessError(Resource item, Exception e) {
        log.error("[ResourceProcessor] onProcessError", e);
    }
}
