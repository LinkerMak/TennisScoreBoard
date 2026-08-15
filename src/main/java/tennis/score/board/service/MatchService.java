package tennis.score.board.service;

import tennis.score.board.model.entity.Match;
import tennis.score.board.web.dto.MatchesPage;

public interface MatchService {

    void saveMatch(Match match);

    MatchesPage getFinishedMatches(Integer pageNumber, String name);
}
