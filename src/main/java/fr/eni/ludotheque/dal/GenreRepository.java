package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByLibelleContainingIgnoreCase(String libelle);
}
