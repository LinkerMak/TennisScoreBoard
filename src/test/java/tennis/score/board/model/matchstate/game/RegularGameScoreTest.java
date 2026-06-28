package tennis.score.board.model.matchstate.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tennis.score.board.model.matchstate.WinnerSide;
import tennis.score.board.model.matchstate.game.regulargame.RegularGameScore;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static tennis.score.board.model.matchstate.WinnerSide.PLAYER_1;
import static tennis.score.board.model.matchstate.WinnerSide.PLAYER_2;

class RegularGameScoreTest {

    private RegularGameScore gameScore;

    @BeforeEach
    void setUp() {
        gameScore = new RegularGameScore();
    }

    @Test
    void shouldIncrementPointsWhenPlayer1WinsPoint() {
        GameResult result = gameScore.pointWonBy(PLAYER_1);

        GameScoreSnapshot snapshot = gameScore.snapshot();

        assertEquals("15", snapshot.player1Points());
        assertEquals("0", snapshot.player2points());
        assertEquals(GameResult.CONTINUES, result);
    }

    @Test
    void shouldIncrementPointsWhenPlayer2WinsPoint() {
        GameResult result = gameScore.pointWonBy(PLAYER_2);

        GameScoreSnapshot snapshot = gameScore.snapshot();

        assertEquals("0", snapshot.player1Points());
        assertEquals("15", snapshot.player2points());
        assertEquals(GameResult.CONTINUES, result);
    }

    @Test
    void shouldWinGameForPlayer1At40_0() {
        winPoints(PLAYER_1, 3); // 40:0

        GameResult result = gameScore.pointWonBy(PLAYER_1);

        assertEquals(GameResult.FINISHED, result);
        Optional<WinnerSide> winner = gameScore.getWinner();
        assertTrue(winner.isPresent());
        assertEquals(PLAYER_1, winner.get());
    }

    @Test
    void shouldWinGameForPlayer2At0_40() {
        winPoints(PLAYER_2, 3); // 0:40

        GameResult result = gameScore.pointWonBy(PLAYER_2);

        assertEquals(GameResult.FINISHED, result);
        Optional<WinnerSide> winner = gameScore.getWinner();
        assertTrue(winner.isPresent());
        assertEquals(PLAYER_2, winner.get());
    }

    @Test
    void shouldTransitionToDeuceAt40_40() {
        winPoints(PLAYER_1, 2);
        winPoints(PLAYER_2, 3);

        GameResult result = gameScore.pointWonBy(PLAYER_1); // 50:40 по внутреннему enum

        assertEquals(GameResult.TRANSITION_TO_DEUCE, result);
        assertTrue(gameScore.isDeuce());
    }


    private void winPoints(WinnerSide winnerSide, int count) {
        for (int i = 0; i < count; i++) {
            gameScore.pointWonBy(winnerSide);
        }
    }
}
