package tennis.score.board.repository;

import tennis.score.board.model.entity.Player;

import java.util.Optional;

public interface PlayerRepository {

    Optional<Player> findByName(String name);

    Player save(Player player);

    Player saveAndFlush(Player player);

}
