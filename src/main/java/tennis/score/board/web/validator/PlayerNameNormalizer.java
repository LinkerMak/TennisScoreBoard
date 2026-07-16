package tennis.score.board.web.validator;

import lombok.experimental.UtilityClass;
import tennis.score.board.exception.BadRequestException;

@UtilityClass
public class PlayerNameNormalizer {

    public static String normalize(String rawName) {
        if (rawName == null) {
            throw new BadRequestException("Имя игрока обязательно");
        }

        return rawName.strip().replaceAll("\\s+", " ");
    }

}
