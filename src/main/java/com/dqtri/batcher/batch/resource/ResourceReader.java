package com.dqtri.batcher.batch.resource;


import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import com.dqtri.batcher.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@StepScope
@Component
public class ResourceReader extends RepositoryItemReader<Resource> {
    private final ResourceRepository resourceRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        setRepository(resourceRepository);
        setMethodName("findByStatus");
        setPageSize(3); // default 10
//        setArguments(List.of(Status.ACTIVE));
        setSort(Map.of("pk", Sort.Direction.ASC));
        super.afterPropertiesSet();
    }

    @Override
    protected Resource doRead() throws Exception {
        log.info("[ResourceReader] doRead");
        return super.doRead();
    }

    @Override
    protected void jumpToItem(int itemLastIndex) throws Exception {
        log.info("[ResourceReader] jumpToItem");
        super.jumpToItem(itemLastIndex);
    }

    @Override
    protected List<Resource> doPageRead() throws Exception {
        log.info("[ResourceReader] doPageRead");
        return super.doPageRead();
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        log.info("[ResourceReader] beforeJob");
        JobParameters jobParameters = stepExecution.getJobExecution().getJobParameters();
        String status = jobParameters.getString("status", "ACTIVE");
        if (Arrays.stream(Status.values()).map(Enum::toString).toList().contains(status)){
            setArguments(List.of(Status.valueOf(status)));
        } else {
            setArguments(List.of(Status.ACTIVE));
        }
    }

    @BeforeRead
    public void beforeRead() {
        log.info("[ResourceReader] beforeRead");
    }

    @AfterRead
    public void afterRead(Resource item) {
        log.info("[ResourceReader] afterRead");
    }

    @OnReadError
    public void onReadError(Exception ex) {
        log.error("[ResourceProcessor] onReadError", ex);
    }

}
