package com.dqtri.batcher.repository.impl;

import com.dqtri.batcher.client.ResourceClient;
import com.dqtri.batcher.client.ResourceDto;
import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import com.dqtri.batcher.repository.ResourceRepositoryCustom;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.ArrayList;
import java.util.List;

@Slf4j
//@NoRepositoryBean
public class ResourceRepositoryCustomImpl implements ResourceRepositoryCustom {

    private final EntityManager em;
    private final ResourceClient resourceClient;

    @Autowired
    public ResourceRepositoryCustomImpl(JpaContext context, ResourceClient resourceClient) {
        this.em = context.getEntityManagerByManagedType(Resource.class);
        this.resourceClient = resourceClient;
    }

    @Override
    public Page<Resource> findByStatus(Status status, Pageable pageable) {
        try(Response response = resourceClient.fetchResources(Status.ACTIVE, "Bearer Hello")){
            ObjectMapper objectMapper = new ObjectMapper();
            List<ResourceDto> resourceDtos = objectMapper.readValue(response.body().asInputStream(),
                    new TypeReference<List<ResourceDto>>() {});
            int size = resourceDtos.size();
            List<Resource> resources = resourceDtos.stream()
                    .map(ResourceDto::toResource).toList();
            if (pageable.getPageNumber() != 1
                    && (pageable.getPageSize() * pageable.getPageNumber()) > size) {
                resources = new ArrayList<>();
            }
            return new PageImpl<>(resources, pageable, size);
        } catch (Exception e){
            log.error("Fetch data error", e);
        }
        return new PageImpl<>(List.of(new Resource()), pageable,  8);
    }

    @Override
    public long countByStatus(Status status) {
        return 6;
    }
}
