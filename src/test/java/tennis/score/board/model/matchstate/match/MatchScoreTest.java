package tennis.score.board.model.matchstate.match;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tennis.score.board.model.matchstate.WinnerSide;

import static org.junit.jupiter.api.Assertions.*;
import static tennis.score.board.model.matchstate.WinnerSide.PLAYER_1;
import static tennis.score.board.model.matchstate.WinnerSide.PLAYER_2;

class MatchScoreTest {

    private MatchScore matchScore;

    @BeforeEach
    void setUp() {
        matchScore = new MatchScore();
    }

    @Test
    void shouldNotBeOverAtStart() {
        assertFalse(matchScore.isOver());
    }

    @Test
    void shouldWinMatchWhenPlayer1Wins2Sets() {
        winSet(PLAYER_1);
        winSet(PLAYER_1);

        assertTrue(matchScore.isOver());
        assertEquals(PLAYER_1, matchScore.getWinnerSide());
    }

    @Test
    void shouldWinMatchWhenPlayer2Wins2Sets() {
        winSet(PLAYER_2);
        winSet(PLAYER_2);

        assertTrue(matchScore.isOver());
        assertEquals(PLAYER_2, matchScore.getWinnerSide());
    }

    @Test
    void shouldWinMatchWhenPlayer1Wins2SetsAndPlayer2Wins1Set() {
        winSet(PLAYER_1);
        winSet(PLAYER_2);
        winSet(PLAYER_1);

        assertTrue(matchScore.isOver());
        assertEquals(PLAYER_1, matchScore.getWinnerSide());
    }

    @Test
    void shouldWinMatchWhenPlayer2Wins2SetsAndPlayer1Wins1Set() {
        winSet(PLAYER_2);
        winSet(PLAYER_1);
        winSet(PLAYER_2);

        assertTrue(matchScore.isOver());
        assertEquals(PLAYER_2, matchScore.getWinnerSide());
    }

    @Test
    void shouldThrowWhenGettingWinnerBeforeMatchIsOver() {
        IllegalStateException e = assertThrows(
                IllegalStateException.class,
                () -> matchScore.getWinnerSide()
        );

        assertTrue(e.getMessage().contains("матч еще не закончен"));
    }

    @Test
    void snapshotShouldReflectCurrentSetsAndGames() {
        winGame(PLAYER_1, 4);
        winGame(PLAYER_2, 2);
        winGame(PLAYER_1, 1);
        winGame(PLAYER_2, 1);
        winGame(PLAYER_1, 3);
        winGame(PLAYER_2, 3);

        MatchScoreSnapshot snapshot = matchScore.snapshot();

        assertEquals(1, snapshot.player1Sets());
        assertEquals(0, snapshot.player2Sets());
        assertEquals(2, snapshot.player1Games());
        assertEquals(3, snapshot.player2Games());
    }

    private void winSet(WinnerSide winnerSide) {
        winGame(winnerSide, 6);
    }

    private void winGame(WinnerSide winnerSide, int gamesCount) {
        for (int i = 0; i < gamesCount; i++) {
            winSingleGame(winnerSide);
        }
    }

    private void winSingleGame(WinnerSide winnerSide) {
        for (int i = 0; i < 4; i++) {
            matchScore.pointWonBy(winnerSide);
        }
    }
}
