package org.mrshoffen.repository;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.JpaCriteriaQuery;
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
    public List<E> findAll() {

        @Cleanup
        Session session = sessionFactory.openSession();


        CriteriaQuery<E> criteria = session.getCriteriaBuilder().createQuery(entityClass);

        criteria.from(entityClass);

        return session.createQuery(criteria).getResultList();

    }
}
