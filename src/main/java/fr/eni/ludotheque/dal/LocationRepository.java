package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
