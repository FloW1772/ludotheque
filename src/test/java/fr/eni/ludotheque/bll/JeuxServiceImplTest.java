package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.GenreRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.dto.JeuDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JeuxServiceImplTest {

    private JeuRepository jeuRepository;
    private GenreRepository genreRepository;
    private ExemplaireRepository exemplaireRepository;
    private JeuxServiceImpl jeuxService;

    @BeforeEach
    void setUp() {
        jeuRepository = mock(JeuRepository.class);
        genreRepository = mock(GenreRepository.class);
        exemplaireRepository = mock(ExemplaireRepository.class); // ajouté pour le constructeur
        jeuxService = new JeuxServiceImpl(jeuRepository, genreRepository, exemplaireRepository);
    }

    @Test
    void testAjouterJeu_AvecGenres() {
        // Données d’entrée
        JeuDto dto = new JeuDto(
                "Catan",
                "REF001",
                10,
                "Jeu de stratégie classique",
                60,
                4.5f,
                List.of(1, 2)
        );

        Genre g1 = new Genre();
        g1.setNoGenre(1);
        g1.setLibelle("Stratégie");

        Genre g2 = new Genre();
        g2.setNoGenre(2);
        g2.setLibelle("Famille");

        when(genreRepository.findAllById(List.of(1, 2))).thenReturn(List.of(g1, g2));
        when(jeuRepository.save(any(Jeu.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Appel de la méthode
        Jeu result = jeuxService.ajouterJeu(dto);

        // Vérifications
        assertEquals("Catan", result.getTitre());
        assertEquals("REF001", result.getReference());
        assertEquals(2, result.getGenres().size());
        assertTrue(result.getGenres().contains(g1));
        assertTrue(result.getGenres().contains(g2));

        verify(genreRepository, times(1)).findAllById(List.of(1, 2));
        verify(jeuRepository, times(1)).save(result);
    }

    @Test
    void testAjouterJeu_SansGenres() {
        JeuDto dto = new JeuDto("Solo", "REF002", 8, "Jeu solo", 30, 2.0f, List.of());

        when(jeuRepository.save(any(Jeu.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Jeu result = jeuxService.ajouterJeu(dto);

        assertEquals("Solo", result.getTitre());
        assertTrue(result.getGenres().isEmpty());

        verify(jeuRepository, times(1)).save(result);
        verify(genreRepository, never()).findAllById(anyList());
    }
}
