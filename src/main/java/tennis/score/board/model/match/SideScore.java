package tennis.score.board.model.match;

import lombok.Getter;

@Getter
public class SideScore {
    private int player1;
    private int player2;

    public SideScore() {
        this.player1 = 0;
        this.player2 = 0;
    }

    public void incrementPlayer1() {
        this.player1++;
    }

    public void incrementPlayer2() {
        this.player2++;
    }

    public void reset() {
        this.player1 = 0;
        this.player2 = 0;
    }
}
