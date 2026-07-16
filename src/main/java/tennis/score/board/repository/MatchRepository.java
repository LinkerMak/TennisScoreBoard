package tennis.score.board.repository;

import tennis.score.board.model.entity.Match;

import java.util.List;

public interface MatchRepository {

    void save(Match match);

    long countAll(String playerName);

    long countAll();

    List<Match> findAll(int offset, int pageSize);

    List<Match> findAll(String playerName, int offset, int pageSize);

}
