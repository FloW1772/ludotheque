package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.GenreRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.dto.JeuDisponibleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JeuxServiceImpl_DisponibiliteTest {

    private JeuRepository jeuRepository;
    private GenreRepository GenreRepository;
    private ExemplaireRepository exemplaireRepository;
    private JeuxServiceImpl jeuxService;

    @BeforeEach
    void setUp() {
        jeuRepository = mock(JeuRepository.class);
        GenreRepository = mock(GenreRepository.class);
        exemplaireRepository = mock(ExemplaireRepository.class);

        jeuxService = new JeuxServiceImpl(jeuRepository,GenreRepository, exemplaireRepository);
    }

    @Test
    void testConsulterJeuxDisponibles_Succes() {
        // --- Création des genres ---
        Genre strategie = new Genre();
        strategie.setNoGenre(1);
        strategie.setLibelle("Stratégie");

        // --- Création des jeux ---
        Jeu jeu1 = new Jeu("Catan", "REF001", 4.5f);
        jeu1.setNoJeu(1);
        jeu1.getGenres().add(strategie);

        Jeu jeu2 = new Jeu("Carcassonne", "REF002", 3.5f);
        jeu2.setNoJeu(2);
        jeu2.getGenres().add(strategie);

        // --- Création des exemplaires ---
        Exemplaire ex1 = new Exemplaire("123", jeu1);
        ex1.setLouable(true);
        Exemplaire ex2 = new Exemplaire("456", jeu1);
        ex2.setLouable(false);
        Exemplaire ex3 = new Exemplaire("789", jeu2);
        ex3.setLouable(true);

        // --- Mock DAL ---
        when(jeuRepository.findAll()).thenReturn(List.of(jeu1, jeu2));
        when(exemplaireRepository.findByJeu(jeu1)).thenReturn(List.of(ex1, ex2));
        when(exemplaireRepository.findByJeu(jeu2)).thenReturn(List.of(ex3));

        // --- Action ---
        List<JeuDisponibleDto> result = jeuxService.consulterJeuxDisponibles(null, null);

        // --- Vérifications ---
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).nbExemplairesDispo());
        assertEquals(1, result.get(1).nbExemplairesDispo());
    }

    @Test
    void testConsulterJeuxDisponibles_FiltreTitre() {
        Jeu jeu = new Jeu("Catan Junior", "REF010", 2.5f);
        jeu.setNoJeu(1);
        Exemplaire ex = new Exemplaire("111", jeu);
        ex.setLouable(true);

        when(jeuRepository.findAll()).thenReturn(List.of(jeu));
        when(exemplaireRepository.findByJeu(jeu)).thenReturn(List.of(ex));

        List<JeuDisponibleDto> result = jeuxService.consulterJeuxDisponibles("junior", null);

        assertEquals(1, result.size());
        assertEquals("Catan Junior", result.get(0).titre());
    }

    @Test
    void testConsulterJeuxDisponibles_PasDeDispo() {
        Jeu jeu = new Jeu("Azul", "REF100", 3.0f);
        jeu.setNoJeu(1);
        Exemplaire ex = new Exemplaire("999", jeu);
        ex.setLouable(false);

        when(jeuRepository.findAll()).thenReturn(List.of(jeu));
        when(exemplaireRepository.findByJeu(jeu)).thenReturn(List.of(ex));

        List<JeuDisponibleDto> result = jeuxService.consulterJeuxDisponibles(null, null);

        assertTrue(result.isEmpty(), "Aucun jeu ne doit apparaître si aucun exemplaire n’est louable");
    }
}
