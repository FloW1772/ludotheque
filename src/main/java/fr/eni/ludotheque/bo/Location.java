package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noLocation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "no_client")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "no_exemplaire")
    private Exemplaire exemplaire;

    private LocalDate dateDebut;
    private LocalDate dateRetourPrevue;
    private boolean retourEffectue = false;
}
