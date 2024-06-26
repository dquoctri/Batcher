package com.dqtri.batcher.batch.resource;

import com.dqtri.batcher.client.EmailDto;
import com.dqtri.batcher.client.EmailSender;
import com.dqtri.batcher.client.ResourceClient;
import com.dqtri.batcher.client.ResourceDto;
import com.dqtri.batcher.model.enums.Status;
import com.dqtri.batcher.repository.ResourceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@StepScope
@Component
public class ResourceTasklet implements Tasklet, StepExecutionListener {

    private final ResourceRepository resourceRepository;
    private final ResourceClient resourceClient;
    private final EmailSender emailSender;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try(Response response = resourceClient.fetchResources(Status.ACTIVE, "Bearer Hello")){
            ObjectMapper objectMapper = new ObjectMapper();
            List<ResourceDto> resourceDtos = objectMapper.readValue(response.body().asInputStream(),
                    new TypeReference<List<ResourceDto>>() {});
            int size = resourceDtos.size();
            long l = resourceRepository.countByStatus(Status.ACTIVE);
            EmailDto emailDto = createEmailDto(l);
            emailSender.send(emailDto);
        } catch (Exception e){
            log.error("Fetch data error", e);
        }
        return RepeatStatus.FINISHED;
    }

    private EmailDto createEmailDto(long size) {
        EmailDto emailDto = new EmailDto();
        emailDto.setFrom("no-reply@dqtri.com");
        emailDto.setCc("vy@dqtri.com");
        emailDto.setTo("xoay@dqtri.com");
        emailDto.setBody("Number of resources are " + size);
        emailDto.setSubject("Notify: " + size + " of resources found");
        return emailDto;
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
