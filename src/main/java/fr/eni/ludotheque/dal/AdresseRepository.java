package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Adresse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdresseRepository extends MongoRepository<Adresse, String> {
}
