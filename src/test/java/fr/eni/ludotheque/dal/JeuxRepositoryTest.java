package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JeuxRepositoryTest {

    @Autowired
    private JeuxRepository jeuxRepository;

    @Autowired
    private GenresRepository genresRepository;

    @Test
    @DisplayName("Test d'ajout d'un jeu avec plusieurs genres")
    @Transactional
    void ajoutJeuAvecGenres() {
        // Arrange
        Genre strategie = new Genre("Stratégie");
        Genre famille = new Genre("Famille");

        Set<Genre> genres = new HashSet<>();
        genres.add(strategie);
        genres.add(famille);

        Jeu jeu = new Jeu("Catan", "Kosmos", "Stratégie");
        jeu.setAgeMinimum(10);
        jeu.setDureePartie(60);
        jeu.setGenres(genres);

        // Act
        Jeu newJeu = jeuxRepository.save(jeu);

        // Assert
        assertNotNull(newJeu);
        assertNotNull(newJeu.getNoJeu());
        assertEquals(2, newJeu.getGenres().size());

        // Vérification en base
        jeuxRepository.flush();
        Optional<Jeu> jeuOpt = jeuxRepository.findById(newJeu.getNoJeu());
        assertTrue(jeuOpt.isPresent());
        assertEquals(2, jeuOpt.get().getGenres().size());
    }
}
