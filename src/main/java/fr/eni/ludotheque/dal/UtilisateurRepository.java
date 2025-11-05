package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    Utilisateur findByLogin(String login);
}
