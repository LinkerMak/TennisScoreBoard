package tennis.score.board.model.match;

public class MatchState {

    private Long player1Id;

    private Long player2Id;

    private Score score;

    public MatchState(Long player1Id, Long player2Id) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
    }
}
