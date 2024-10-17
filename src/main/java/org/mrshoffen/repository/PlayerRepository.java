package org.mrshoffen.repository;

import jakarta.inject.Inject;
import org.hibernate.SessionFactory;
import org.mrshoffen.entity.Player;

import java.util.Optional;

public class PlayerRepository extends BaseRepository<Integer, Player> {


    @Inject
    protected PlayerRepository(SessionFactory sessionFactory) {
        super(Player.class, sessionFactory);
    }

    @Override
    public Optional<Player> findById(Integer id) {
        return Optional.empty();
    }


}
