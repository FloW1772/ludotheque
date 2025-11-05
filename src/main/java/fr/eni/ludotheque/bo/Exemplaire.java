package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "exemplaires")
public class Exemplaire {
    @Id
    private String id;

    @NonNull private String codebarre;
    private boolean louable = true;

    @NonNull
    private Jeu jeu;
}
