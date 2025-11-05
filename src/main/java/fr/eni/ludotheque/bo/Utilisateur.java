package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "utilisateurs")
public class Utilisateur {
    @Id
    private String id;

    @NonNull private String login;
    @NonNull private String password;
    @NonNull private String role;
}
