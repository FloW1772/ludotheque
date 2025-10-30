package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientsServiceImplTest {

    private ClientRepository clientRepository;
    private ClientsServiceImpl clientsService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientsService = new ClientsServiceImpl(clientRepository);
    }

    @Test
    void testModifierAdresseClient() {
        // Client existant en base
        Client client = new Client();
        Adresse ancienneAdresse = new Adresse();
        ancienneAdresse.setRue("Rue de l'Ancienne Adresse");
        ancienneAdresse.setCodePostal("00000");
        ancienneAdresse.setVille("Ancienne Ville");
        client.setAdresse(ancienneAdresse);

        // Simuler la récupération du client depuis la DAL
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // DTO avec la nouvelle adresse
        ClientDto dto = new ClientDto(
                null, // nom
                null, // prenom
                null, // email
                null, // téléphone
                "Nouvelle Rue", // rue
                "12345",        // code postal
                "Nouvelle Ville" // ville
        );

        // Appel du service
        Client result = clientsService.modifierAdresseClient(1, dto);

        // Vérification
        assertEquals("Nouvelle Rue", result.getAdresse().getRue());
        assertEquals("12345", result.getAdresse().getCodePostal());
        assertEquals("Nouvelle Ville", result.getAdresse().getVille());

        // Vérifie qu’on n’a sauvegardé qu’une fois le client
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testModifierAdresseClient_ClientInexistant() {
        when(clientRepository.findById(999)).thenReturn(Optional.empty());

        ClientDto dto = new ClientDto(null, null, null, null, "Rue", "11111", "Ville");

        assertThrows(IllegalArgumentException.class, () ->
                clientsService.modifierAdresseClient(999, dto));
    }
}
