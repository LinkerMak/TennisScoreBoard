package tennis.score.board.service;

import tennis.score.board.model.entity.Player;

public interface PlayerService {

    Player findOrCreate(String name);

}
