package fr.eni.ludotheque.bo;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor

public class Adresse {
    @EqualsAndHashCode.Exclude
    private Integer noAdresse;

    @NonNull private String rue;

    @NonNull private String codePostal;

    @NonNull private String ville;
}
