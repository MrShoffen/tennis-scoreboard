package org.mrshoffen.repository;

import jakarta.inject.Inject;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mrshoffen.entity.Match;
import org.mrshoffen.entity.Player;

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
    public List<Player> findWithPagination(int page, int pageSize) {
        @Cleanup Session session = sessionFactory.openSession();

        return session.createQuery("SELECT p from Player p ORDER BY p.id", Player.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
    }


}
