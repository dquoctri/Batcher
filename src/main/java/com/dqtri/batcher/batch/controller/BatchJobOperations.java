package com.dqtri.batcher.batch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@RequestMapping("/jobs")
public interface BatchJobOperations {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void triggerBatchJob(@PathVariable String name, @RequestBody Map<String, String> params) throws Exception;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{name}/async", consumes = MediaType.APPLICATION_JSON_VALUE)
    void triggerBatchJobAsync(@PathVariable String name, @RequestBody Map<String, String> params) throws Exception;

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{name}")
    boolean interruptBatchJob(@PathVariable String name) throws Exception;
}
