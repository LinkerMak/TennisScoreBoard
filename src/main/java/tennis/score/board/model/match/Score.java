package tennis.score.board.model.match;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Score {

    private final static int SETS_TO_WIN = 2;

    private static final int TIE_BREAK_MIN_POINTS_TO_WIN = 7;
    private static final int REQUIRED_LEAD = 2;

    private final SideScore points;
    private final SideScore games;
    private final SideScore sets;

    boolean tieBreak;

    public Score() {
        this.points = new SideScore();
        this.games = new SideScore();
        this.sets = new SideScore();
    }

    public void update(WinnerSide winnerSide) {
        updatePoints(winnerSide);
        updateGames();
        updateSets();
    }

    private void updatePoints(WinnerSide winnerSide) {
        switch(winnerSide) {
            case PLAYER_1 -> points.incrementPlayer1();
            case PLAYER_2 -> points.incrementPlayer2();
        }
    }

    private void updateGames() {
        if(tieBreak) {
            updateTieBreak();
            return;
        }

        if(isDeuceLikeScore()) {
            tryFinishGameAfterDeuce();
            return;
        }

        tryFinishRegularGame();
    }

    private boolean isDeuceLikeScore() {
        return points.getPlayer1() > 2
                && points.getPlayer2() > 2;
    }

    private void tryFinishGameAfterDeuce() {
        if(pointsLeadOfPlayer1() == REQUIRED_LEAD) {
            games.incrementPlayer1();
            points.reset();
        }
        else if(pointsLeadOfPlayer2() == REQUIRED_LEAD) {
            games.incrementPlayer2();
            points.reset();
        }
    }

    private void tryFinishRegularGame() {
        if(points.getPlayer1() == 4 && pointsLeadOfPlayer1() >= REQUIRED_LEAD) {
            games.incrementPlayer1();
            points.reset();
        }
        else if(points.getPlayer2() == 4 && pointsLeadOfPlayer2() >= REQUIRED_LEAD) {
            games.incrementPlayer2();
            points.reset();
        }
    }

    private void updateSets() {
        if(bothPlayersHaveMoreThanFourGames()) {
            tryFinishExtendedSetOrStartTieBreak();
        }

        tryFinishRegularSet();
    }

    private boolean bothPlayersHaveMoreThanFourGames() {
        return games.getPlayer1() > 4 && games.getPlayer2() > 4;
    }

    private void tryFinishExtendedSetOrStartTieBreak() {
        if(games.getPlayer1() == 7 && games.getPlayer2() == 5) {
            sets.incrementPlayer1();
            games.reset();
        }
        else if(games.getPlayer1() == 5 && games.getPlayer2() == 7) {
            sets.incrementPlayer2();
            games.reset();
        }
        else if(shouldStartTieBreak()) {
            tieBreak = true;
        }
    }

    private void tryFinishRegularSet() {
        if(games.getPlayer1() == 6 && gamesLeadOfPlayer1() >= REQUIRED_LEAD) {
            sets.incrementPlayer1();
            games.reset();
        }
        else if(games.getPlayer2() == 6 && gamesLeadOfPlayer2() >= REQUIRED_LEAD) {
            sets.incrementPlayer2();
            games.reset();
        }
    }

    private void updateTieBreak() {
        if (!hasTieBreakReachedWinningZone()) {
            return;
        }

        if (pointsLeadOfPlayer1() >= REQUIRED_LEAD) {
            sets.incrementPlayer1();
            resetAfterTieBreak();
        } else if (pointsLeadOfPlayer2() >= REQUIRED_LEAD) {
            sets.incrementPlayer2();
            resetAfterTieBreak();
        }
    }

    private boolean shouldStartTieBreak() {
        return games.getPlayer1() == 6 && games.getPlayer2() == 6;
    }

    private void resetAfterTieBreak() {
        points.reset();
        games.reset();
        tieBreak = false;
    }

    private boolean hasTieBreakReachedWinningZone() {
        return points.getPlayer1() >= TIE_BREAK_MIN_POINTS_TO_WIN
                || points.getPlayer2() >= TIE_BREAK_MIN_POINTS_TO_WIN;
    }

    private int pointsLeadOfPlayer1() {
        return points.getPlayer1() - points.getPlayer2();
    }

    private int pointsLeadOfPlayer2() {
        return points.getPlayer2() - points.getPlayer1();
    }

    private int gamesLeadOfPlayer1() {
        return games.getPlayer1() - games.getPlayer2();
    }

    private int gamesLeadOfPlayer2() {
        return games.getPlayer2() - games.getPlayer1();
    }

    public boolean isOver() {
        return sets.getPlayer1() == SETS_TO_WIN || sets.getPlayer2() == SETS_TO_WIN;
    }

    public Optional<WinnerSide> getWinnerSide() {
        if(!isOver()) {
            return Optional.empty();
        }

        return Optional.of((sets.getPlayer1() == SETS_TO_WIN)
                ? WinnerSide.PLAYER_1
                : WinnerSide.PLAYER_2);
    }

}
