package tennis.score.board.web.dto;

import java.util.List;

public record MatchesPage (
        List<MatchDTO> matches,
        long totalMatches,
        int currentPage,
        int totalPages,
        int pageSize,
        boolean hasPrevious,
        boolean hasNext,
        String filterByPlayerName
) {
}
