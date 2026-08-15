package tennis.score.board.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import tennis.score.board.model.entity.Player;

import java.util.Optional;

@Repository
public class JpaPlayerRepository implements PlayerRepository {

    private static final String FIND_BY_NAME_JPQL = "select p from Player p where p.name = :name";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Player> findByName(String name) {
        return entityManager.createQuery(FIND_BY_NAME_JPQL, Player.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Player save(Player player) {
        entityManager.persist(player);
        return player;
    }

    @Override
    public Player saveAndFlush(Player player) {
        save(player);
        entityManager.flush();
        return player;
    }
}
