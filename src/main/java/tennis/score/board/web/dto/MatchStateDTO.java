package tennis.score.board.web.dto;

public record MatchStateDTO(
        Long player1Id,
        String player1Name,
        String player1Points,
        int player1Games,
        int player1Sets,

        Long player2Id,
        String player2Name,
        String player2Points,
        int player2Games,
        int player2Sets,

        boolean tieBreak
) {
}
