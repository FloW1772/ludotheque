package fr.eni.ludotheque.dto;

public record ClientDto(String nom,
                        String prenom,
                        String email,
                        String noTelephone,
                        String rue,
                        String codePostal,
                        String ville) {
}
