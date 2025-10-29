package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExemplairesRepositoryTest {

    @Autowired
    private ExemplairesRepository exemplairesRepository;

    @Autowired
    private JeuxRepository jeuxRepository;

    @Test
    @DisplayName("Test d'ajout d'un exemplaire en base - cas nominal")
    @Transactional
    void ajoutExemplaire() {
        // Arrange
        Jeu jeu = new Jeu("Catan", "Kosmos", "Stratégie");
        jeuxRepository.save(jeu);

        Exemplaire exemplaire = new Exemplaire("Neuf", jeu);
        exemplaire.setDateAchat(LocalDate.of(2024, 10, 15));

        // Act
        Exemplaire newExemplaire = exemplairesRepository.save(exemplaire);

        // Assert
        assertNotNull(newExemplaire);
        assertNotNull(newExemplaire.getNoExemplaire());
        assertEquals("Neuf", newExemplaire.getEtat());
        assertEquals("Catan", newExemplaire.getJeu().getNom());

        // Vérification en base
        exemplairesRepository.flush();
        Optional<Exemplaire> exOpt = exemplairesRepository.findById(newExemplaire.getNoExemplaire());
        assertTrue(exOpt.isPresent());
        assertEquals("Kosmos", exOpt.get().getJeu().getEditeur());
    }
}
