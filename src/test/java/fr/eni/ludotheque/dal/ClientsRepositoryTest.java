package fr.eni.ludotheque.dal;


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
    private  ClientsRepository clientsRepository;


    @Test
    @DisplayName("Test d'ajout d'un client en BD - cas droit")
    @Transactional
    void ajoutClient() {
        //AAA
        //Arrange = préparation du test
        Client client = new Client("bob", "dupont", "bob.dupont@eni.fr");
        client.setNoTelephone("01010101");

        //Act : appel de la méthode à tester
        Client newClient = clientsRepository.save(client);

        //Assert : Vérifier le résultat fourni par la méthode à tester
        assertNotNull(newClient);
        assertNotNull(newClient.getNoClient());
        assertEquals(client.getNom(), newClient.getNom() );

        clientsRepository.flush();
        Optional<Client> searchClientOpt = clientsRepository.findById(newClient.getNoClient());
        assertTrue(searchClientOpt.isPresent());
        assertEquals(client, searchClientOpt.get());
    }
}
