package fr.eni.ludotheque.dto;

import fr.eni.ludotheque.bo.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class JeuDTO {
    private Integer noJeu;

    private String titre;

    private String reference;

    private int ageMin;

    private String description;

    private int duree;

    private Float tarifJour;

    private int nbExemplairesDisponibles;

    public JeuDTO(Integer noJeu){
        this.noJeu = noJeu;
    }

    public JeuDTO(Integer noJeu,String titre,String reference,Integer ageMin, String description,
                  Integer duree,    Float tarifJour,    Integer nbExemplairesDisponibles){
        this.noJeu = noJeu;
        this.titre=titre;
        this.reference=reference;
        this.ageMin = ageMin;
        this.description = description;
        this.duree = duree;
        this.tarifJour = tarifJour;
        this.nbExemplairesDisponibles = nbExemplairesDisponibles;
    }



}
