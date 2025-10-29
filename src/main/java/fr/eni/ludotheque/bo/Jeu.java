package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "JEUX")
public class Jeu {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noJeu;

    @NonNull
    @Column(nullable = false, length = 100)
    private String nom;

    @NonNull
    @Column(nullable = false)
    private String editeur;

    @NonNull
    @Column(nullable = false)
    private String categorie;

    @Column
    private Integer ageMinimum;

    @Column
    private Integer dureePartie; // en minutes

    // 🔹 Relation ManyToMany avec Genre
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "JEUX_GENRES",
            joinColumns = @JoinColumn(name = "noJeu"),
            inverseJoinColumns = @JoinColumn(name = "noGenre")
    )
    private Set<Genre> genres = new HashSet<>();
}
