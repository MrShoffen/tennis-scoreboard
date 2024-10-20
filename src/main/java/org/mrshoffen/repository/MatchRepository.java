package org.mrshoffen.repository;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.*;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mrshoffen.entity.Match;

import java.util.ArrayList;
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
    public List<Match> findWithPagination(int pageNumber, int pageSize, String playerName) {
        @Cleanup Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Match> criteria = cb.createQuery(Match.class);

        Root<Match> matches = criteria.from(Match.class);
        matches.fetch("firstPlayer", JoinType.INNER);
        matches.fetch("secondPlayer", JoinType.INNER);
        matches.fetch("winner", JoinType.INNER);

        List<Predicate> predicates = calculateNameFilterPredicate(playerName, cb, matches);

        criteria.select(matches).where(predicates.toArray(new Predicate[0]));

        return session.createQuery(criteria)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();

    }

    @Override
    public long sizeFilteredByPlayerName(String name) {
        @Cleanup Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);

        Root<Match> matches = criteria.from(Match.class);


        List<Predicate> predicates = calculateNameFilterPredicate(name, cb, matches);

        criteria.select(cb.count(matches)).where(predicates.toArray(new Predicate[0]));

        return session.createQuery(criteria).getSingleResult();
    }

    private List<Predicate> calculateNameFilterPredicate(String playerName, CriteriaBuilder cb, Root<Match> matches) {
        List<Predicate> predicates = new ArrayList<>();
        if (playerName != null && !playerName.isBlank()) {
            Predicate firstLike = cb.like(cb.lower(matches.get("firstPlayer").get("name")), "%" + playerName.toLowerCase() + "%");
            Predicate secondLike = cb.like(cb.lower(matches.get("secondPlayer").get("name")), "%" + playerName.toLowerCase() + "%");


            predicates.add(cb.or(firstLike, secondLike));
        }
        return predicates;
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
