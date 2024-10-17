package org.mrshoffen.repository;

import jakarta.inject.Inject;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mrshoffen.entity.Match;

import java.util.List;
import java.util.Optional;

public class MatchRepository extends BaseRepository<Integer, Match> {

    @Inject
    protected MatchRepository(SessionFactory sessionFactory) {
        super(Match.class, sessionFactory);
    }

    @Override
    public Optional<Match> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Match> findWithPagination(int page, int pageSize) {
        @Cleanup Session session = sessionFactory.openSession();

        return session.createQuery("SELECT m from Match m " +
                        " JOIN FETCH m.firstPlayer f " +
                        " JOIN FETCH m.secondPlayer s " +
                        " JOIN FETCH m.winner w ", Match.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();


    }

    @Override
    public List<Match> findAll() {
        @Cleanup Session session = sessionFactory.openSession();

        return session.createQuery("select m from Match m" +
                        " JOIN FETCH m.firstPlayer f " +
                        " JOIN FETCH m.secondPlayer s " +
                        " JOIN FETCH m.winner w ", Match.class)
                .list();
    }
}
