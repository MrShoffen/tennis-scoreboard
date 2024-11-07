package org.mrshoffen.repository;

import jakarta.inject.Inject;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;


public abstract class BaseRepository<E> {

    protected final SessionFactory sessionFactory;

    @Inject
    protected BaseRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public E save(E entity) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        return entity;
    }

    public abstract Optional<E> findById(Integer id);

    public abstract List<E> getAllWithOffsetAndLimit(Integer offset, Integer limit, String playerName);

    public abstract Integer numberOfEntitiesWithName(String name);

}
