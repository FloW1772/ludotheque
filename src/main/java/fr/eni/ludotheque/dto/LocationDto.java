package fr.eni.ludotheque.dto;

import java.time.LocalDate;

public record LocationDto(
        Integer idClient,
        Integer idExemplaire,
        LocalDate dateDebut,
        LocalDate dateRetourPrevue
) {}
