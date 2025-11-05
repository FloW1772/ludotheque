package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "jeux")
public class Jeu {
    @Id
    private String id;

    @EqualsAndHashCode.Include
    @NonNull private String reference;

    @NonNull private String titre;
    private int ageMin;
    private String description;
    private int duree;
    @NonNull private Float tarifJour;

    private List<Genre> genres = new ArrayList<>();
    private Integer nbExemplairesDisponibles;
}
