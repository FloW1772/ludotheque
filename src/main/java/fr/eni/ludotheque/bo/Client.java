package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "CLIENTS")
public class Client {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue
    private Integer noClient;

    @Column(nullable = false, length = 100)
    @NonNull
    private String nom;

    @Column(nullable = false, length = 100)
    @NonNull
    private String prenom;

    @Column(nullable = false, length = 100, unique = true)
    @NonNull
    private String email;

    @Column(length = 15)
    private String noTelephone;

   // @NonNull
    //private String adresse;
}
