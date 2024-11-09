package org.mrshoffen.repository;

import jakarta.inject.Inject;
import lombok.Cleanup;
import org.hibernate.HibernateException;
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

    public Optional<E> save(E entity) {
        try {
            @Cleanup Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return Optional.of(entity);
        } catch (HibernateException e) {
            return Optional.empty();
        }

    }

    public abstract List<E> getAllWithOffsetAndLimit(Integer offset, Integer limit, String playerName);

    public abstract Integer numberOfEntitiesContainingName(String name);

}
