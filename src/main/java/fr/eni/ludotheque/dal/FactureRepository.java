package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Facture;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FactureRepository extends MongoRepository<Facture, String> {
}
