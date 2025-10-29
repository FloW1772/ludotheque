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

    @OneToOne(cascade = CascadeType.ALL) // Sauvegarde automatique de l’adresse
    @JoinColumn(name = "no_adresse")     // Clé étrangère en table CLIENTS
    private Adresse adresse;
}