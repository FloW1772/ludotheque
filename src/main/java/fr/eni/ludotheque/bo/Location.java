package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "locations")
public class Location {
    @Id
    private String id;

    @NonNull private LocalDateTime dateDebut;
    private LocalDateTime dateRetour;
    private float tarifJour;

    @NonNull private Client client;
    @NonNull private Exemplaire exemplaire;
}
