package tennis.score.board.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tennis.score.board.model.entity.Player;
import tennis.score.board.repository.PlayerRepository;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public Player findOrCreate(String name) {
        System.out.println("biba");
        return playerRepository.findByName(name)
                .orElseGet(() -> {
                    Player newPlayer = new Player(name);
                    playerRepository.save(newPlayer);
                    return newPlayer;
                });
    }
}
