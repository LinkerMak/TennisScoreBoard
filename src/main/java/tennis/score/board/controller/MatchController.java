package tennis.score.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tennis.score.board.model.entity.Player;
import tennis.score.board.service.OngoingMatchService;
import tennis.score.board.service.PlayerService;

import java.util.UUID;

@Controller()
@RequestMapping("/matches")
public class MatchController {

    private final PlayerService playerService;
    private final OngoingMatchService ongoingMatchService;

    @Autowired
    public MatchController(PlayerService playerService, OngoingMatchService ongoingMatchService) {
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
        Player player1 = playerService.findOrCreate(namePlayer1);
        Player player2 = playerService.findOrCreate(namePlayer2);

        UUID uuid = ongoingMatchService.createMatch(player1, player2);

        return "redirect:/match-score?uuid=" + uuid.toString();
    }
}
