package com.dqtri.batcher.repository;

import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ResourceRepositoryCustom {
    Page<Resource> findByStatus(@Param("status") Status status, Pageable pageable);
    long countByStatus(Status status);
}
