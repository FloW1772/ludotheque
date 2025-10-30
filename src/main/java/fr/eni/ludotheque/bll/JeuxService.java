package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dto.JeuDisponibleDto;
import fr.eni.ludotheque.dto.JeuDto;

import java.util.List;

public interface JeuxService {
    Jeu ajouterJeu(JeuDto jeuDto);
    List<JeuDisponibleDto> consulterJeuxDisponibles(String filtreTitre, List<Integer> idsGenres);
}
