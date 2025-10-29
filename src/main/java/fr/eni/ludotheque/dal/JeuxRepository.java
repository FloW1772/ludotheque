package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Jeu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JeuxRepository extends JpaRepository<Jeu, Integer> {
}
