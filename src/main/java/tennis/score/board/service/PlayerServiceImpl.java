package tennis.score.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tennis.score.board.model.entity.Player;
import tennis.score.board.repository.PlayerRepository;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public Player findOrCreate(String name) {
        String trimmedName = name.trim();
        try {
            return playerRepository.findByName(trimmedName)
                    .orElseGet(() -> playerRepository.saveAndFlush(new Player(trimmedName)));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Ошибка при создании игрока после поиска " + name, e);
        }
    }

}
