package com.dqtri.batcher.batch.resource;

import com.dqtri.batcher.client.EmailDto;
import com.dqtri.batcher.client.EmailSender;
import com.dqtri.batcher.model.enums.Status;
import com.dqtri.batcher.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@StepScope
@Component
public class ResourceTasklet implements Tasklet, StepExecutionListener {

    private final ResourceRepository resourceRepository;
    private final EmailSender emailSender;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        long count = resourceRepository.countByStatus(Status.ACTIVE);
        EmailDto emailDto = new EmailDto();
        emailDto.setFrom("no-reply@dqtri.com");
        emailDto.setCc("vy@dqtri.com");
        emailDto.setTo("xoay@dqtri.com");
        emailDto.setBody("Number of active resources are " + count);
        emailDto.setSubject("Notify: " + count + " of resources found");
        emailSender.send(emailDto);
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("[ResourceTasklet] beforeStep " + stepExecution.getId());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("[ResourceTasklet] afterStep");
        return stepExecution.getExitStatus();
    }
}
