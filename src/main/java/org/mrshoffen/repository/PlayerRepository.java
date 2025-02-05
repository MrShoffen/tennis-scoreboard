package org.mrshoffen.repository;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mrshoffen.entity.persistence.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerRepository extends BaseRepository<Player> {

    @Inject
    protected PlayerRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public Optional<Player> findByName(String name) {
        @Cleanup Session session = sessionFactory.openSession();
        return session.createQuery("SELECT p FROM Player p WHERE p.name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

    @Override
    public List<Player> getAllWithOffsetAndLimit(Integer offset, Integer limit, String playerName) {
        @Cleanup Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Player> criteria = cb.createQuery(Player.class);

        Root<Player> players = criteria.from(Player.class);

        List<Predicate> predicates = buildFilterPredicate(playerName, cb, players);

        criteria.select(players).where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(players.get("id")));

        return session.createQuery(criteria)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();

    }

    @Override
    public Integer numberOfEntitiesContainingName(String name) {
        @Cleanup Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);

        Root<Player> matches = criteria.from(Player.class);


        List<Predicate> predicates = buildFilterPredicate(name, cb, matches);

        criteria.select(cb.count(matches)).where(predicates.toArray(new Predicate[0]));

        return session.createQuery(criteria).getSingleResult().intValue();
    }

    private List<Predicate> buildFilterPredicate(String playerName, CriteriaBuilder cb, Root<Player> player) {
        List<Predicate> predicates = new ArrayList<>();
        if (playerName != null && !playerName.isBlank()) {
            Predicate like = cb.like(cb.lower(player.get("name")), "%" + playerName.toLowerCase() + "%");
            predicates.add(like);
        }
        return predicates;
    }




}
