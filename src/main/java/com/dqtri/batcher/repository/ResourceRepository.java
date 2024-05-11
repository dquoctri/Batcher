/*
 * Copyright (c) 2023 Mango Family
 * All rights reserved or may not! :)
 */

package com.dqtri.batcher.repository;

import com.dqtri.batcher.model.IResource;
import com.dqtri.batcher.model.Resource;
import com.dqtri.batcher.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("SELECT s FROM Resource s " +
            "WHERE :status is null or s.status = :status")
    Page<Resource> findByStatus(@Param("status") Status status, Pageable pageable);

    @Query(value = "SELECT s.pk, s.content, s.status FROM resource s " +
            "WHERE :status is null or s.status = :status", nativeQuery = true)
    Page<IResource> findResourcesByStatus(@Param("status") Status status, Pageable pageable);
}
