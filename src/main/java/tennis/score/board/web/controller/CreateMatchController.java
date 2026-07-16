package tennis.score.board.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tennis.score.board.service.OngoingMatchService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CreateMatchController {

    private final OngoingMatchService ongoingMatchService;

    @GetMapping("/new")
    public String newMatch() {
        return "new-match";
    }

    @PostMapping("/new-match")
    public String createMatch(@RequestParam("playerOne") String namePlayer1,
                              @RequestParam("playerTwo") String namePlayer2) {
        UUID uuid = ongoingMatchService.createMatch(namePlayer1, namePlayer2);

        return "redirect:/match-score?uuid=" + uuid.toString();
    }

}
