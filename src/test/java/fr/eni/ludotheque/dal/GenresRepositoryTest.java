package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Genre;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GenresRepositoryTest {

    @Autowired
    private GenresRepository genresRepository;

    @Test
    @DisplayName("Test d'ajout d'un genre en base - cas nominal")
    @Transactional
    void ajoutGenre() {
        // Arrange
        Genre genre = new Genre("Stratégie");

        // Act
        Genre newGenre = genresRepository.save(genre);

        // Assert
        assertNotNull(newGenre);
        assertNotNull(newGenre.getNoGenre());
        assertEquals("Stratégie", newGenre.getNom());

        // Vérification en base
        genresRepository.flush();
        Optional<Genre> genreOpt = genresRepository.findById(newGenre.getNoGenre());
        assertTrue(genreOpt.isPresent());
        assertEquals("Stratégie", genreOpt.get().getNom());
    }
}
