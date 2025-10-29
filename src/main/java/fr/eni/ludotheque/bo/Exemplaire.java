package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "EXEMPLAIRES")
public class Exemplaire {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noExemplaire;

    @NonNull
    @Column(nullable = false, length = 20)
    private String etat;

    @Column
    private LocalDate dateAchat;

    // 🔹 Relation avec Jeu (plusieurs exemplaires pour un jeu)
    @ManyToOne(optional = false)
    @JoinColumn(name = "noJeu")
    @NonNull
    private Jeu jeu;
}
