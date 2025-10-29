package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "ADRESSES")
public class Adresse {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue
    private Integer noAdresse;

    @NonNull
    @Column(nullable = false, length = 255)
    private String rue;

    @NonNull
    @Column(nullable = false, length = 10)
    private String codePostal;

    @NonNull
    @Column(nullable = false, length = 100)
    private String ville;
}
