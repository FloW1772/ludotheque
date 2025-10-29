package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClientsRepositoryTest {

    @Autowired
    private ClientsRepository clientsRepository;

    @Test
    @DisplayName("Test d'ajout d'un client et de son adresse en BD - cas nominal")
    @Transactional
    void ajoutClientAvecAdresse() {
        // Arrange
        Adresse adresse = new Adresse("12 rue des Lilas", "44000", "Nantes");
        Client client = new Client("Bob", "Dupont", "bob.dupont@eni.fr");
        client.setNoTelephone("01010101");
        client.setAdresse(adresse);

        // Act
        Client newClient = clientsRepository.save(client);

        // Assert
        assertNotNull(newClient);
        assertNotNull(newClient.getNoClient());
        assertNotNull(newClient.getAdresse());
        assertNotNull(newClient.getAdresse().getNoAdresse());

        assertEquals(client.getNom(), newClient.getNom());
        assertEquals("Nantes", newClient.getAdresse().getVille());

        // Vérification en base
        clientsRepository.flush();
        Optional<Client> searchClientOpt = clientsRepository.findById(newClient.getNoClient());
        assertTrue(searchClientOpt.isPresent());

        Client clientInDb = searchClientOpt.get();
        assertEquals("12 rue des Lilas", clientInDb.getAdresse().getRue());
    }
}
