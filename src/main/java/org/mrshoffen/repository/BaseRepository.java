package org.mrshoffen.repository;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mrshoffen.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;


public abstract class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> entityClass;

    protected final SessionFactory sessionFactory;

    @Inject
    protected BaseRepository(Class<E> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }




    @Override
    public long size() {

        @Cleanup Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);

        Root<E> from = criteria.from(entityClass);
        criteria.select(criteriaBuilder.count(from));

        return session.createQuery(criteria).getSingleResult();

    }
}