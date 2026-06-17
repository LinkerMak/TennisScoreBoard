package tennis.score.board.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(
        name = "players",
        uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Player {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
