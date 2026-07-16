package tennis.score.board.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import tennis.score.board.model.entity.Match;

import java.util.List;

@Repository
public class JpaMatchRepository implements MatchRepository {

    private static final String COUNT_ALL_JPQL = """
            select count(m) 
            from Match m
            """;

    private static final String COUNT_ALL_WITH_NAME_FILTER_JPQL = """
            select count(m) 
            from Match m
            where lower(m.player1.name) like :playerName
            or lower(m.player2.name) like :playerName
            """;

    private static final String FIND_ALL_JPQL = """
            select m
            from Match m
            order by m.id desc
            """;

    private static final String FIND_ALL_WITH_NAME_FILTER_JPQL = """
            select m
            from Match m
            where lower(m.player1.name) like :playerName
            or lower(m.player2.name) like :playerName
            order by m.id desc
            """;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Match match) {
        entityManager.persist(match);
    }

    @Override
    public long countAll(String playerName) {
        return entityManager.createQuery(COUNT_ALL_WITH_NAME_FILTER_JPQL, Long.class)
                .setParameter("playerName", normalizedNameFilter(playerName))
                .getSingleResult();
    }

    @Override
    public long countAll() {
        return entityManager.createQuery(COUNT_ALL_JPQL, Long.class)
                .getSingleResult();
    }

    @Override
    public List<Match> findAll(int offset, int pageSize) {
        return entityManager.createQuery(FIND_ALL_JPQL, Match.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public List<Match> findAll(String playerName, int offset, int pageSize) {
        return entityManager.createQuery(FIND_ALL_WITH_NAME_FILTER_JPQL, Match.class)
                .setParameter("playerName", normalizedNameFilter(playerName))
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private static String normalizedNameFilter(String nameFilter) {
        return "%" + nameFilter.trim().toLowerCase() + "%";
    }
}
