package tennis.score.board.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tennis.score.board.model.entity.Player;
import tennis.score.board.service.OngoingMatchService;
import tennis.score.board.service.PlayerService;
import tennis.score.board.web.validator.PlayerNameValidator;


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

        String normalizeName1 = PlayerNameValidator.normalizeAndValidate(namePlayer1);
        String normalizeName2 = PlayerNameValidator.normalizeAndValidate(namePlayer2);
        PlayerNameValidator.validateDifferentPlayers(normalizeName1, normalizeName2);

        Player player1 = playerService.findOrCreate(normalizeName1);
        Player player2 = playerService.findOrCreate(normalizeName2);

        UUID uuid = ongoingMatchService.createMatch(player1, player2);

        return "redirect:/match-score?uuid=" + uuid.toString();
    }

}
