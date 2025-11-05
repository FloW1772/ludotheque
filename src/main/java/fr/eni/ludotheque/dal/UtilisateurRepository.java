package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    Utilisateur findByLogin(String login);
}
