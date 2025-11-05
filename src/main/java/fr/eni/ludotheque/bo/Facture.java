package fr.eni.ludotheque.bo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "factures")
public class Facture {
    @Id
    private String id;

    private LocalDateTime datePaiement;
    private float prix;

    private List<Location> locations = new ArrayList<>();

    public void addLocation(Location location) {
        this.locations.add(location);
    }
}
