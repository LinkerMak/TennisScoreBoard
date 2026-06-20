package tennis.score.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tennis.score.board.exception.EntityNotFoundException;
import tennis.score.board.model.entity.Match;
import tennis.score.board.model.entity.Player;
import tennis.score.board.model.match.MatchState;
import tennis.score.board.web.dto.MatchStateDTO;
import tennis.score.board.web.mapper.MatchStateMapper;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OngoingMatchService {

    private final Map<UUID, MatchState> matches = new ConcurrentHashMap<>();

    private final MatchStateMapper matchStateMapper;

    private final MatchService matchService;

    @Autowired
    public OngoingMatchService(MatchStateMapper matchStateMapper, MatchService matchService) {
        this.matchStateMapper = matchStateMapper;
        this.matchService = matchService;
    }

    public UUID createMatch(Player player1, Player player2) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, new MatchState(player1, player2));
        return uuid;
    }

    public MatchStateDTO getMatchByUUID(UUID uuid) {
        validateMatchExistence(uuid);
        return matchStateMapper.toMatchStateDTO(matches.get(uuid));
    }

    public MatchStateDTO updateMatch(UUID uuid, Long winnerId) {
        validateMatchExistence(uuid);
        MatchState match = matches.get(uuid);
        match.validatePlayerBelongsToMatch(winnerId);

        match.updateScore(winnerId);

        if(match.getScore().isOver()) {
            Optional<Long> id = match.getWinnerId();

            if(id.isEmpty()) {
                throw new IllegalStateException("Id победителя не было получено");
            }

            matches.remove(uuid);

            matchService.save(new Match(

            ));
        }


    }

    public void validateMatchExistence(UUID uuid) {
        if(!matches.containsKey(uuid)) {
            throw new EntityNotFoundException("Сущность матча по id = " + uuid + " не найдена");
        }
    }

}
