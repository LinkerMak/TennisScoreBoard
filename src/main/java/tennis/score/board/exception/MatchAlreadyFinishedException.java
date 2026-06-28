package tennis.score.board.exception;

public class MatchAlreadyFinishedException extends RuntimeException {
    public MatchAlreadyFinishedException(String message) {
        super(message);
    }
}
