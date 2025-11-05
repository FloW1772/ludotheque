package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "adresses")
public class Adresse {
    private String rue;
    private String codePostal;
    private String ville;
}
