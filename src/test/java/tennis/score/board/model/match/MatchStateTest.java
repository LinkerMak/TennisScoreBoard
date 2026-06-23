package tennis.score.board.model.match;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tennis.score.board.exception.PlayerNotInMatchException;
import tennis.score.board.model.entity.Player;

import static org.junit.jupiter.api.Assertions.*;

public class MatchStateTest {

    private Player player1;
    private Player player2;
    private MatchState matchState;

    @BeforeEach
    void init() {
        long id1 = 1;
        long id2 = 2;
        player1 = newPlayer(id1);
        player2 = newPlayer(id2);
        matchState = new MatchState(player1, player2);
    }

    private static Player newPlayer(long id) {
        Player newPlayer = new Player();
        newPlayer.setId(id);
        return newPlayer;
    }

    @Test
    void shouldIncrementPlayer1PointsWhenWinnerIdBelongsToPlayer1() {
        matchState.updateScore(player1.getId());

        assertEquals(1, matchState.getScore().getPoints().getPlayer1());
        assertEquals(0, matchState.getScore().getPoints().getPlayer2());
    }

    @Test
    void shouldIncrementPlayer2PointsWhenWinnerIdBelongsToPlayer2() {
        matchState.updateScore(player2.getId());

        assertEquals(0, matchState.getScore().getPoints().getPlayer1());
        assertEquals(1, matchState.getScore().getPoints().getPlayer2());
    }

    @Test
    void shouldThrowWhenPlayerDoesNotBelongToMatch() {
        long id3 = 3;

        PlayerNotInMatchException e = assertThrows(PlayerNotInMatchException.class,
                () -> matchState.updateScore(id3)
        );

        assertTrue(e.getMessage().contains(String.valueOf(id3)));
    }

    @Test
    void shouldNotThrowWhenPlayersBelongsToMatch() {
        assertDoesNotThrow(
                () -> {
                    matchState.updateScore(player1.getId());
                    matchState.updateScore(player2.getId());
                }
        );
    }

    @Test
    void shouldReturnWinnerPlayerWhenMatchIsOver() {
        for(int sets = 0; sets < 2; sets++) {
            for(int games = 0; games < 6; games++) {
                for(int points = 0; points < 4; points++) {
                    matchState.updateScore(player1.getId());
                }
            }
        }

        assertTrue(matchState.getScore().isOver());
        assertTrue(matchState.getMatchWinner().isPresent());
        assertEquals(player1, matchState.getMatchWinner().get());
    }

    @Test
    void shouldReturnEmptyWinnerIfMatchNotOver() {
        for(int sets = 0; sets < 2; sets++) {
            for(int games = 0; games < 6; games++) {
                for(int points = 0; points < 3; points++) {
                    matchState.updateScore(player1.getId());
                }
            }
        }

        assertFalse(matchState.getScore().isOver());
        assertTrue(matchState.getMatchWinner().isEmpty());
    }
}
