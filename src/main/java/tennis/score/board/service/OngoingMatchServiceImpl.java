package tennis.score.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tennis.score.board.exception.EntityNotFoundException;
import tennis.score.board.exception.PlayerNotInMatchException;
import tennis.score.board.model.entity.Match;
import tennis.score.board.model.entity.Player;
import tennis.score.board.model.matchstate.MatchState;
import tennis.score.board.model.matchstate.WinnerSide;
import tennis.score.board.service.updateresult.MatchStatus;
import tennis.score.board.service.updateresult.UpdateMatchResult;
import tennis.score.board.web.dto.MatchStateDTO;
import tennis.score.board.web.mapper.MatchStateMapper;
import tennis.score.board.web.validator.PlayerNameNormalizer;
import tennis.score.board.web.validator.PlayerNameValidator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static tennis.score.board.model.matchstate.WinnerSide.PLAYER_1;
import static tennis.score.board.model.matchstate.WinnerSide.PLAYER_2;

@Service
@RequiredArgsConstructor
public class OngoingMatchServiceImpl implements OngoingMatchService {

    private final static long FINISHED_MATCH_GRACE_PERIOD_MILLIS = 10_000L;

    private final Map<UUID, MatchState> matches = new ConcurrentHashMap<>();
    private final Map<UUID, Long> removeAt = new ConcurrentHashMap<>();

    private final MatchStateMapper matchStateMapper;

    private final MatchServiceImpl matchService;
    private final PlayerServiceImpl playerService;

    @Override
    @Transactional
    public UUID createMatch(String namePlayer1, String namePlayer2) {
        String normalizeName1 = PlayerNameNormalizer.normalize(namePlayer1);
        String normalizeName2 = PlayerNameNormalizer.normalize(namePlayer2);

        validateNames(normalizeName1, normalizeName2);

        Player player1 = playerService.findOrCreate(normalizeName1);
        Player player2 = playerService.findOrCreate(normalizeName2);

        UUID uuid = UUID.randomUUID();
        matches.put(uuid, new MatchState(player1, player2));
        return uuid;
    }

    @Override
    public MatchStateDTO getMatchByUUID(UUID uuid) {
        return matchStateMapper.toMatchStateDTO(getExistingMatch(uuid).snapshot());
    }

    @Override
    public UpdateMatchResult updateMatch(UUID uuid, Long winnerId) {
        MatchState match = getExistingMatch(uuid);
        synchronized (match) {
            validatePlayerBelongsToMatch(match, winnerId);

            if (match.isOver()) {
                return buildUpdateMatchResult(match, MatchStatus.FINISHED);
            }

            WinnerSide winnerSide = (Objects.equals(winnerId, match.getPlayer1().getId()))
                    ? PLAYER_1
                    : PLAYER_2;
            match.updateScore(winnerSide);

            if (match.isOver()) {
                MatchState completeMatch = handleMatchOverAfterUpdate(match, uuid);
                return buildUpdateMatchResult(completeMatch, MatchStatus.FINISHED);
            }

            return buildUpdateMatchResult(match, MatchStatus.ONGOING);
        }
    }

    private MatchState handleMatchOverAfterUpdate(MatchState match, UUID uuid) {
        Player winner = match.getMatchWinner();

        removeAt.put(uuid, System.currentTimeMillis() + FINISHED_MATCH_GRACE_PERIOD_MILLIS);

        matchService.saveMatch(new Match(
                match.getPlayer1(),
                match.getPlayer2(),
                winner
        ));

        return match;
    }

    private UpdateMatchResult buildUpdateMatchResult(MatchState match, MatchStatus status) {
        return new UpdateMatchResult(status,
                matchStateMapper.toMatchStateDTO(match.snapshot()));
    }

    private MatchState getExistingMatch(UUID uuid) {
        MatchState matchState = matches.get(uuid);
        if (matchState == null) {
            throw new EntityNotFoundException("Сущность матча по id = " + uuid + " не найдена");
        }

        return matchState;
    }

    private void validateNames(String namePlayer1, String namePlayer2) {
        PlayerNameValidator.validate(namePlayer1);
        PlayerNameValidator.validate(namePlayer2);
        PlayerNameValidator.validateDifferentPlayers(namePlayer1, namePlayer2);
    }

    private void validatePlayerBelongsToMatch(MatchState match, Long id) {
        if (!Objects.equals(id, match.getPlayer1().getId())
                && !Objects.equals(id, match.getPlayer2().getId())) {
            throw new PlayerNotInMatchException(id);
        }
    }

    @Scheduled(fixedDelay = 1000)
    private void cleanupFinishedMatches() {
        for (Map.Entry<UUID, Long> entry : removeAt.entrySet()) {
            if (System.currentTimeMillis() >= entry.getValue()) {
                matches.remove(entry.getKey());
                removeAt.remove(entry.getKey());
            }
        }
    }
}
