package tennis.score.board.model.match;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest {

    private Score score;

    @BeforeEach
    void initScore() {
        score = new Score();
    }

    @Test
    void shouldIncrementPlayer1PointsWhenPlayer1WinsPoint() {
        winPointsPlayer1(1);

        assertEquals(1, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldIncrementPlayer2PointsWhenPlayer2WinsPoint() {
        winPointsPlayer2(1);

        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(1, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinGameForPlayer1At40_0() {
        winPointsPlayer1(3);

        winPointsPlayer1(1);

        assertEquals(1, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinGameForPlayer2At_40() {
        winPointsPlayer2(3);

        winPointsPlayer2(1);

        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(1, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldNotFinishGameWhenPlayer1WinsPointAtDeuce() {
        reachDeuce();

        winPointsPlayer1(1);

        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(4, score.getPoints().getPlayer1());
        assertEquals(3, score.getPoints().getPlayer2());
    }

    @Test
    void shouldNotFinishGameWhenPlayer2WinsPointAtDeuce() {
        reachDeuce();

        winPointsPlayer2(1);

        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(3, score.getPoints().getPlayer1());
        assertEquals(4, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinGameForPlayer1FromAdvantage() {
        reachDeuce();

        winPointsPlayer1(2);

        assertEquals(1, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinGameForPlayer2FromAdvantage() {
        reachDeuce();

        winPointsPlayer2(2);

        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(1, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldReturnToDeuceWhenBothPlayersWinAfterAdvantage() {
        reachDeuce();

        winPointsPlayer1(1);
        winPointsPlayer2(1);

        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(4, score.getPoints().getPlayer1());
        assertEquals(4, score.getPoints().getPlayer2());
    }

    @Test
    void shouldReturnToDeuceWhenBothPlayersWinAfterTwoAdvantages() {
        reachDeuce();

        winPointsPlayer1(1);
        winPointsPlayer2(1);

        winPointsPlayer1(1);
        winPointsPlayer2(1);

        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(5, score.getPoints().getPlayer1());
        assertEquals(5, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinSetForPlayer1At6_0() {
        winGamesPlayer1(6);

        assertEquals(1, score.getSets().getPlayer1());
        assertEquals(0, score.getSets().getPlayer2());
        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinSetForPlayer2At0_6() {
        winGamesPlayer2(6);

        assertEquals(0, score.getSets().getPlayer1());
        assertEquals(1, score.getSets().getPlayer2());
        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinSetForPlayer1At6_4() {
        winGamesPlayer2(4);
        winGamesPlayer1(6);

        assertEquals(1, score.getSets().getPlayer1());
        assertEquals(0, score.getSets().getPlayer2());
        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinSetForPlayer2At4_6() {
        winGamesPlayer1(4);
        winGamesPlayer2(6);

        assertEquals(0, score.getSets().getPlayer1());
        assertEquals(1, score.getSets().getPlayer2());
        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldNotWinSetAt6_5() {
        winGamesPlayer2(5);
        winGamesPlayer1(6);

        assertEquals(0, score.getSets().getPlayer1());
        assertEquals(0, score.getSets().getPlayer2());
        assertEquals(6, score.getGames().getPlayer1());
        assertEquals(5, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldNotWinSetAt5_6() {
        winGamesPlayer1(5);
        winGamesPlayer2(6);

        assertEquals(0, score.getSets().getPlayer1());
        assertEquals(0, score.getSets().getPlayer2());
        assertEquals(5, score.getGames().getPlayer1());
        assertEquals(6, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldStartTieBreakAt6_6() {
        tieBreak();

        assertTrue(score.isTieBreak());
        assertEquals(0, score.getSets().getPlayer1());
        assertEquals(0, score.getSets().getPlayer2());
        assertEquals(6, score.getGames().getPlayer1());
        assertEquals(6, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldNotFinishTieBreakAt7_6() {
        tieBreak();

        winPointsPlayer1(6);
        winPointsPlayer2(6);
        winPointsPlayer1(1);

        assertTrue(score.isTieBreak());
        assertEquals(0, score.getSets().getPlayer1());
        assertEquals(0, score.getSets().getPlayer2());
        assertEquals(6, score.getGames().getPlayer1());
        assertEquals(6, score.getGames().getPlayer2());
        assertEquals(7, score.getPoints().getPlayer1());
        assertEquals(6, score.getPoints().getPlayer2());
    }

    @Test
    void shouldNotFinishTieBreakAt6_7() {
        tieBreak();

        winPointsPlayer1(6);
        winPointsPlayer2(6);
        winPointsPlayer2(1);

        assertTrue(score.isTieBreak());
        assertEquals(0, score.getSets().getPlayer1());
        assertEquals(0, score.getSets().getPlayer2());
        assertEquals(6, score.getGames().getPlayer1());
        assertEquals(6, score.getGames().getPlayer2());
        assertEquals(6, score.getPoints().getPlayer1());
        assertEquals(7, score.getPoints().getPlayer2());
    }


    @Test
    void shouldWinTieBreakSetForPlayer1At8_6() {
        tieBreak();

        winPointsPlayer1(6);
        winPointsPlayer2(6);
        winPointsPlayer1(2);

        assertFalse(score.isTieBreak());
        assertEquals(1, score.getSets().getPlayer1());
        assertEquals(0, score.getSets().getPlayer2());
        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinTieBreakSetForPlayer2At6_8() {
        tieBreak();

        winPointsPlayer1(6);
        winPointsPlayer2(6);
        winPointsPlayer2(2);

        assertFalse(score.isTieBreak());
        assertEquals(0, score.getSets().getPlayer1());
        assertEquals(1, score.getSets().getPlayer2());
        assertEquals(0, score.getGames().getPlayer1());
        assertEquals(0, score.getGames().getPlayer2());
        assertEquals(0, score.getPoints().getPlayer1());
        assertEquals(0, score.getPoints().getPlayer2());
    }

    @Test
    void shouldWinMatchWhenPlayer1Win2Sets() {
        winGamesPlayer1(6);
        winGamesPlayer1(6);

        assertTrue(score.isOver());
        assertTrue(score.getWinnerSide().isPresent());
        assertEquals(WinnerSide.PLAYER_1,score.getWinnerSide().get());
    }

    @Test
    void shouldWinMatchWhenPlayer2Win1Sets() {
        winGamesPlayer2(6);
        winGamesPlayer2(6);

        assertTrue(score.isOver());
        assertTrue(score.getWinnerSide().isPresent());
        assertEquals(WinnerSide.PLAYER_2,score.getWinnerSide().get());
    }

    @Test
    void shouldWinMatchWhenPlayer1Win2SetsAndPlayer2Win1Set() {
        winGamesPlayer1(6);
        winGamesPlayer2(6);
        winGamesPlayer1(6);

        assertTrue(score.isOver());
        assertTrue(score.getWinnerSide().isPresent());
        assertEquals(WinnerSide.PLAYER_1,score.getWinnerSide().get());
    }

    @Test
    void shouldWinMatchWhenPlayer2Win2SetsAndPlayer1Win1Set() {
        winGamesPlayer2(6);
        winGamesPlayer1(6);
        winGamesPlayer2(6);

        assertTrue(score.isOver());
        assertTrue(score.getWinnerSide().isPresent());
        assertEquals(WinnerSide.PLAYER_2,score.getWinnerSide().get());
    }

    @Test
    void shouldReturnEmptyWinnerWhenMatchIsNotOver() {
        winGamesPlayer1(6);
        winGamesPlayer2(6);

        assertFalse(score.isOver());
        assertTrue(score.getWinnerSide().isEmpty());
    }

    private void winPointsPlayer1(int count) {
        for (int i = 0; i < count; i++) {
            score.update(WinnerSide.PLAYER_1);
        }
    }

    private void winPointsPlayer2(int count) {
        for (int i = 0; i < count; i++) {
            score.update(WinnerSide.PLAYER_2);
        }
    }

    private void reachDeuce() {
        winPointsPlayer1(3);
        winPointsPlayer2(3);
    }

    private void winGamesPlayer1(int count) {
        for (int i = 0; i < count; i++) {
            winPointsPlayer1(4);
        }
    }

    private void winGamesPlayer2(int count) {
        for (int i = 0; i < count; i++) {
            winPointsPlayer2(4);
        }
    }

    private void tieBreak() {
        winGamesPlayer1(5);
        winGamesPlayer2(5);
        winGamesPlayer1(1);
        winGamesPlayer2(1);
    }

}
