package tennis.score.board.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tennis.score.board.model.match.MatchState;
import tennis.score.board.model.match.Score;
import tennis.score.board.web.dto.MatchStateDTO;

@Mapper(componentModel = "spring")
public interface MatchStateMapper {

    @Mapping(source = "player1.id", target = "player1Id")
    @Mapping(source = "player1.name", target = "player1Name")
    @Mapping(source = "score", target = "player1Points", qualifiedByName = "mapPlayer1Points")
    @Mapping(source = "score.games.player1", target = "player1Games")
    @Mapping(source = "score.sets.player1", target = "player1Sets")

    @Mapping(source = "player2.id", target = "player2Id")
    @Mapping(source = "player2.name", target = "player2Name")
    @Mapping(source = "score" , target = "player2Points", qualifiedByName = "mapPlayer2Points" )
    @Mapping(source = "score.games.player2", target = "player2Games")
    @Mapping(source = "score.sets.player2", target = "player2Sets")

    @Mapping(source = "score.tieBreak", target = "tieBreak")
    MatchStateDTO toMatchStateDTO(MatchState matchState);

    @Named("mapPlayer1Points")
    default String mapPlayer1Points(Score score) {
        return toDisplayPoints(
                score.getPoints().getPlayer1(),
                score.getPoints().getPlayer2(),
                score.isTieBreak());
    }

    @Named("mapPlayer2Points")
    default String mapPlayer2Points(Score score) {
        return toDisplayPoints(
                score.getPoints().getPlayer2(),
                score.getPoints().getPlayer1(),
                score.isTieBreak());
    }

    default String toDisplayPoints(int playerPoints, int opponentPoints, boolean tieBreak) {
        if(tieBreak) {
            return String.valueOf(playerPoints);
        }

        if(playerPoints >= 3 && opponentPoints >= 3 && playerPoints - opponentPoints == 1) {
            return "AD";
        }

        if(playerPoints > 3) {
            return "40";
        }

        return String.valueOf(switch(playerPoints){
            case 0 -> 0;
            case 1 -> 15;
            case 2 -> 30;
            case 3 -> 40;
            default -> throw new IllegalStateException("Не ожидаемое значение очков игрока равное " + playerPoints);
        });
    }


}
