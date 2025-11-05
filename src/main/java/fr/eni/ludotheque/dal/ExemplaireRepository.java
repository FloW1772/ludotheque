package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Exemplaire;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExemplaireRepository extends MongoRepository<Exemplaire, String> {
    Exemplaire findByCodebarre(String codebarre);
}
