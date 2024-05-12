package com.dqtri.batcher.client;

import com.dqtri.batcher.model.enums.Status;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "Resource", url = "${services.resource.api.url}", configuration = ResourceClientConfig.class)
public interface ResourceClient {

    @GetMapping("/resources")
    Response fetchResources(@RequestParam(value = "status") Status status,
                              @RequestHeader(value = "Authorization") String token);


    @PutMapping("/resources/{resourceId}")
    Response updateResource(@PathVariable(value = "resourceId") Long resourceId,
                            @RequestBody ResourceDto resource,
                            @RequestHeader(value = "Authorization") String token);

}
