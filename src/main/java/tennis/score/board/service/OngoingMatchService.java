package tennis.score.board.service;

import tennis.score.board.service.updateresult.UpdateMatchResult;
import tennis.score.board.web.dto.MatchStateDTO;

import java.util.UUID;

public interface OngoingMatchService {

    UUID createMatch(String normalizeName1, String normalizeName2);

    MatchStateDTO getMatchByUUID(UUID uuid);

    UpdateMatchResult updateMatch(UUID uuid, Long winnerId);

}
