package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplairesRepository extends JpaRepository<Exemplaire, Integer> {
}
