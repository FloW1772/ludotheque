package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.dto.ClientDto;
import fr.eni.ludotheque.dal.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Utilise la vraie base
@Transactional // Chaque test est rollbacké automatiquement
class ClientsServiceIntegrationTest {

    @Autowired
    private ClientsServiceImpl clientsService;

    @Autowired
    private ClientRepository clientRepository;

    private Client clientExistant;

    @BeforeEach
    void setUp() {
        // Création d’un client existant en base avant chaque test
        clientExistant = new Client();
        clientExistant.setNom("Dupont");
        clientExistant.setPrenom("Jean");
        clientExistant.setEmail("jean.dupont@mail.com");

        // selon ton entité :
        clientExistant.setNoTelephone("0600000000");

        Adresse adresse = new Adresse();
        adresse.setRue("1 rue de Paris");
        adresse.setCodePostal("75000");
        adresse.setVille("Paris");
        clientExistant.setAdresse(adresse);

        clientRepository.save(clientExistant);
    }

    @Test
    void testModifierClient_CasPositif() {
        // Préparation du DTO avec nouvelles informations
        ClientDto dto = new ClientDto(
                "Durand",
                "Paul",
                "paul.durand@mail.com",
                "0700000000",
                "10 rue des Fleurs",
                "44000",
                "Nantes"
        );

        // Appel de la méthode à tester
        Client modifie = clientsService.modifierClient(clientExistant.getNoClient(), dto);

        // Vérifications
        assertThat(modifie.getNom()).isEqualTo("Durand");
        assertThat(modifie.getPrenom()).isEqualTo("Paul");
        assertThat(modifie.getEmail()).isEqualTo("paul.durand@mail.com");

        assertThat(modifie.getNoTelephone()).isEqualTo("0700000000");

        assertThat(modifie.getAdresse()).isNotNull();
        assertThat(modifie.getAdresse().getRue()).isEqualTo("10 rue des Fleurs");
        assertThat(modifie.getAdresse().getVille()).isEqualTo("Nantes");

        // Vérifie que la modification est bien persistée en base
        Client verifie = clientRepository.findById(clientExistant.getNoClient()).orElseThrow();
        assertThat(verifie.getNom()).isEqualTo("Durand");
        assertThat(verifie.getAdresse().getVille()).isEqualTo("Nantes");
    }

    @Test
    void testModifierClient_ClientNonTrouve() {
        ClientDto dto = new ClientDto(
                "Inconnu",
                "Test",
                "inconnu@mail.com",
                "0800000000",
                "rue fantôme",
                "99999",
                "Nowhere"
        );

        // Cas négatif : id inexistant
        assertThrows(IllegalArgumentException.class, () ->
                clientsService.modifierClient(9999, dto)
        );
    }
}
