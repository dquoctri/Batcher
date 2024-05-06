package com.dqtri.batcher.batch.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ResourceExecution {
    public static final String JOB_NAME = "notificationExecutionJob";

    @Value("${batch-job.resource.chunk-size:10}")
    private final int chunkSize = 10;

}
