package tennis.score.board.service;

import org.springframework.stereotype.Service;
import tennis.score.board.model.entity.Player;
import tennis.score.board.model.match.MatchState;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OngoingMatchService {

    private final Map<UUID, MatchState> matches = new ConcurrentHashMap<>();

    public UUID createMatch(Player player1, Player player2) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, new MatchState(player1.getId(), player2.getId()));
        return uuid;
    }
}
