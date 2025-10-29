package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dto.ClientDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientsServiceTest {

    @Autowired
    private ClientsService clientsService;

    @MockitoBean
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Test de l'ajoute d'un client cas positif")
    public void testAjouterClientEtAdresseCasPositif() {
        //Arrange
        ClientDto clientDto = new ClientDto("nom1", "p1", "p1.nom1@eni.fr",
                "010101011",
                "rue1",
                "44400",
                "REZE");
        Client fauxClient = new Client();
        BeanUtils.copyProperties(clientDto, fauxClient);
        fauxClient.setAdresse(new Adresse());
        BeanUtils.copyProperties(clientDto, fauxClient.getAdresse());
        fauxClient.setNoClient(123);
        fauxClient.getAdresse().setNoAdresse(456);
        when(clientRepository.save(any(Client.class))).thenReturn(fauxClient);

        //Act
        Client newClient = clientsService.ajouterClient(clientDto);

        //Assert
        assertNotNull(newClient);
        assertNotNull(newClient.getNoClient());
        assertNotNull(newClient.getAdresse().getNoAdresse());
        assertEquals(456, newClient.getAdresse().getNoAdresse());

    }

}
