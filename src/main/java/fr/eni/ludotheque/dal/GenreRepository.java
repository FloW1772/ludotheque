package fr.eni.ludotheque.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eni.ludotheque.bo.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer>{

    @Query(nativeQuery = true, value = "select g.* from genres g inner join jeux_genres jg on g.no_genre = jg.no_genre where jg.no_jeu=:noJeu")
    List<Genre> findGenresByNoJeu(@Param("noJeu") Integer noJeu);

}
