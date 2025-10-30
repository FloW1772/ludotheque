package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    List<Exemplaire> findByJeu(Jeu jeu);
}
