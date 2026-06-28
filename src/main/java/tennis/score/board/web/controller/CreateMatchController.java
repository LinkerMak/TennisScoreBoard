package tennis.score.board.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tennis.score.board.exception.BadRequestException;
import tennis.score.board.model.entity.Player;
import tennis.score.board.service.OngoingMatchService;
import tennis.score.board.service.PlayerService;


import java.util.UUID;

@Controller
public class CreateMatchController {

    private final PlayerService playerService;
    private final OngoingMatchService ongoingMatchService;

    @Autowired
    public CreateMatchController(PlayerService playerService, OngoingMatchService ongoingMatchService) {
        this.playerService = playerService;
        this.ongoingMatchService = ongoingMatchService;
    }

    @GetMapping("/new")
    public String newMatch() {
        return "new-match";
    }

    @PostMapping("/new-match")
    public String createMatch(@RequestParam("playerOne") String namePlayer1,
                              @RequestParam("playerTwo") String namePlayer2) {
        validateName(namePlayer1);
        validateName(namePlayer2);

        Player player1 = playerService.findOrCreate(namePlayer1);
        Player player2 = playerService.findOrCreate(namePlayer2);

        UUID uuid = ongoingMatchService.createMatch(player1, player2);

        return "redirect:/match-score?uuid=" + uuid.toString();
    }

    private static void validateName(String str) {
        if(str == null || str.isBlank()) {
            throw new BadRequestException("Поле name обязательно");
        }
    }
}
