package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressesRepository extends JpaRepository<Adresse, Integer> {
}
