package com.dqtri.batcher.batch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/executions")
public interface ExecutionOperations {
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{executionId}/restart")
    void restartExecution(@PathVariable Long executionId) throws Exception;
}
