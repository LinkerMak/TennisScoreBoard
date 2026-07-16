package tennis.score.board.web.validator;

import tennis.score.board.exception.BadRequestException;

import java.util.regex.Pattern;

public final class PlayerNameValidator {

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[\\p{L}]+(?:[ '-][\\p{L}]+)*$");

    private PlayerNameValidator() {
    }

    public static void validate(String name) {
        if (name.isBlank()) {
            throw new BadRequestException("Имя игрока обязательно");
        }

        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new BadRequestException(
                    "Имя игрока может содержать только буквы, пробел, дефис и апостроф"
            );
        }
    }

    public static void validateDifferentPlayers(String player1, String player2) {
        if (player1.equalsIgnoreCase(player2)) {
            throw new BadRequestException("Игроки должны быть разными");
        }
    }

}