package org.mrshoffen.repository;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.*;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mrshoffen.entity.Match;
import org.mrshoffen.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerRepository extends BaseRepository<Integer, Player> {


    @Inject
    protected PlayerRepository(SessionFactory sessionFactory) {
        super(Player.class, sessionFactory);
    }

    @Override
    public List<Player> findAll() {
        @Cleanup Session session = sessionFactory.openSession();

        return session.createQuery("SELECT p FROM Player p", Player.class).list();
    }

    @Override
    public Optional<Player> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Player> findWithPaginationFilteredByName(int pageNumber, int pageSize, String playerName) {
        @Cleanup Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Player> criteria = cb.createQuery(Player.class);

        Root<Player> matches = criteria.from(Player.class);


        List<Predicate> predicates = calculateNameFilterPredicate(playerName, cb, matches);

        criteria.select(matches).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(matches.get("id")));

        return session.createQuery(criteria)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();

    }

    @Override
    public Long numberOfEntitiesWithName(String name) {
        @Cleanup Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);

        Root<Player> matches = criteria.from(Player.class);


        List<Predicate> predicates = calculateNameFilterPredicate(name, cb, matches);

        criteria.select(cb.count(matches)).where(predicates.toArray(new Predicate[0]));

        return session.createQuery(criteria).getSingleResult();
    }



    private List<Predicate> calculateNameFilterPredicate(String playerName, CriteriaBuilder cb, Root<Player> player) {
        List<Predicate> predicates = new ArrayList<>();
        if (playerName != null && !playerName.isBlank()) {
            Predicate like = cb.like(cb.lower(player.get("name")), "%" + playerName.toLowerCase() + "%");
            predicates.add(like);
        }
        return predicates;
    }

}
