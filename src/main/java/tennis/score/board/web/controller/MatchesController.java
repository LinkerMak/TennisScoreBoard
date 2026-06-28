package tennis.score.board.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tennis.score.board.service.MatchService;
import tennis.score.board.web.dto.MatchesPage;

@Controller
@RequestMapping("/matches")
public class MatchesController {

    private final MatchService matchService;

    @Autowired
    public MatchesController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping()
    public String getFinishedMatches(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
                                     @RequestParam(value = "filter_by_player_name", required = false) String name,
                                     Model model) {
        MatchesPage matchesPage = matchService.getFinishedMatches(pageNumber, name);
        model.addAttribute("matchesPage", matchesPage);

        return "matches";
    }

}

