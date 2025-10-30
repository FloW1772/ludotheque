package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.*;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.LocationRepository;
import fr.eni.ludotheque.dto.LocationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationsServiceImplTest {

    private LocationRepository locationRepository;
    private ClientRepository clientRepository;
    private ExemplaireRepository exemplaireRepository;
    private LocationsServiceImpl locationsService;

    @BeforeEach
    void setUp() {
        locationRepository = mock(LocationRepository.class);
        clientRepository = mock(ClientRepository.class);
        exemplaireRepository = mock(ExemplaireRepository.class);

        locationsService = new LocationsServiceImpl(
                locationRepository,
                clientRepository,
                exemplaireRepository
        );
    }

    @Test
    void testCreerLocation_Succes() {
        // --- Données ---
        int idClient = 1;
        int idExemplaire = 2;

        Adresse adresse = new Adresse();
        adresse.setRue("10 rue des Jeux");
        adresse.setCodePostal("44000");
        adresse.setVille("Nantes");

        Client client = new Client("Durand", "Paul", "paul.durand@example.com", adresse);
        client.setNoClient(idClient);

        Jeu jeu = new Jeu("Catan", "REF001", 4.5f);
        jeu.setAgeMin(10);        Exemplaire exemplaire = new Exemplaire("1234567890123", jeu);
        exemplaire.setNoExemplaire(idExemplaire);
        exemplaire.setLouable(true);

        when(clientRepository.findById(idClient)).thenReturn(Optional.of(client));
        when(exemplaireRepository.findById(idExemplaire)).thenReturn(Optional.of(exemplaire));
        when(locationRepository.save(any(Location.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LocationDto dto = new LocationDto(idClient, idExemplaire, LocalDate.now(), LocalDate.now().plusDays(7));

        // --- Action ---
        Location result = locationsService.creerLocation(dto);

        // --- Vérifications ---
        assertNotNull(result);
        assertEquals(client, result.getClient());
        assertEquals(exemplaire, result.getExemplaire());
        assertEquals(dto.dateDebut(), result.getDateDebut());
        assertEquals(dto.dateRetourPrevue(), result.getDateRetourPrevue());
        assertFalse(result.isRetourEffectue());
        assertFalse(exemplaire.isLouable(), "L'exemplaire doit être marqué comme non louable");

        verify(locationRepository, times(1)).save(any(Location.class));
        verify(exemplaireRepository, times(1)).save(exemplaire);
    }

    @Test
    void testCreerLocation_ClientInexistant() {
        int idClient = 99;
        int idExemplaire = 2;

        when(clientRepository.findById(idClient)).thenReturn(Optional.empty());

        LocationDto dto = new LocationDto(idClient, idExemplaire, LocalDate.now(), LocalDate.now().plusDays(7));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> locationsService.creerLocation(dto));

        assertEquals("Client non trouvé avec l'id : 99", exception.getMessage());
        verify(locationRepository, never()).save(any(Location.class));
    }

    @Test
    void testCreerLocation_ExemplaireDejaLoue() {
        int idClient = 1;
        int idExemplaire = 2;

        Adresse adresse = new Adresse();
        adresse.setRue("5 rue des Loueurs");
        adresse.setCodePostal("35000");
        adresse.setVille("Rennes");

        Client client = new Client("Martin", "Claire", "claire.martin@example.com", adresse);
        client.setNoClient(idClient);

        Jeu jeu = new Jeu("Catan", "REF001", 4.5f);
        jeu.setAgeMin(10);        Exemplaire exemplaire = new Exemplaire("9876543210987", jeu);
        exemplaire.setNoExemplaire(idExemplaire);
        exemplaire.setLouable(false); // déjà loué

        when(clientRepository.findById(idClient)).thenReturn(Optional.of(client));
        when(exemplaireRepository.findById(idExemplaire)).thenReturn(Optional.of(exemplaire));

        LocationDto dto = new LocationDto(idClient, idExemplaire, LocalDate.now(), LocalDate.now().plusDays(5));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> locationsService.creerLocation(dto));

        assertEquals("Exemplaire déjà loué !", exception.getMessage());
        verify(locationRepository, never()).save(any(Location.class));
    }
}
