package com.dqtri.batcher.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class MyRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> {
    private final EntityManager entityManager;

    MyRepositoryImpl(JpaEntityInformation entityInformation,
                     EntityManager entityManager) {
        super(entityInformation, entityManager);

        // Keep the EntityManager around to used from the newly introduced methods.
        this.entityManager = entityManager;
    }

    @Transactional
    public <S extends T> S save(S entity) {
        return super.save(entity);
    }
}
