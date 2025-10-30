package fr.eni.ludotheque.dto;

import java.util.List;

public record JeuDto(
        String titre,
        String reference,
        int ageMin,
        String description,
        int duree,
        Float tarifJour,
        List<Integer> idsGenres
) {}
