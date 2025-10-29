package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "GENRES")
public class Genre {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noGenre;

    @NonNull
    @Column(nullable = false, length = 50, unique = true)
    private String nom;
}
