package fr.eni.ludotheque.dto;

public record JeuDisponibleDto(
        Integer idJeu,
        String titre,
        long nbExemplairesDispo
) {}
