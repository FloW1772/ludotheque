package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Facture;
import fr.eni.ludotheque.bo.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Integer>{

}
