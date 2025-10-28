package fr.eni.ludotheque.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor

public class Client {
    private Integer noClient;

    @NonNull
    private String nom;

    @NonNull
    private String prenom;

    @NonNull
    private String mail;

    private String noTelephone;

    @NonNull
    private String adresse;
}
