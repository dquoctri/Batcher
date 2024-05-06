package com.dqtri.batcher.batch.config.sample;

import io.micrometer.common.lang.Nullable;
import lombok.NonNull;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class SampleIncrementer implements JobParametersIncrementer {

    public static final String RUN_ID_KEY = "run.id";

    @NonNull
    public JobParameters getNext(@Nullable JobParameters parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return new JobParametersBuilder().addLong(RUN_ID_KEY, 1L).toJobParameters();
        }
        @SuppressWarnings("ConstantConditions")
        long id = parameters.getLong(RUN_ID_KEY, 1L) + 100;
        return new JobParametersBuilder().addLong(RUN_ID_KEY, id).toJobParameters();
    }
}
