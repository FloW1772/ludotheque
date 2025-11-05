package fr.eni.ludotheque.bll;

//import static org.mockito.Mockito.doAnswer;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.AdresseRepository;
import fr.eni.ludotheque.dto.AdresseDTO;
import fr.eni.ludotheque.dto.ClientDTO;
import fr.eni.ludotheque.exceptions.DataNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class ClientServiceTestIntegration {

	@Autowired
	private ClientService clientService;

	@Autowired
	private AdresseRepository adresseRepository;

	@Test
	@DisplayName("Trouver les clients dont le nom commence par")
	@Transactional
	public void testTrouverClientsDontLeNomCommencePar() {
		//Arrange
		String nom = "DUP";
		ClientDTO client= new ClientDTO("DUPIEUX", "Quentin", "e1",  "tel1","rue des Cormorans",  "44860", "Saint Aignan Grand Lieu");
		ClientDTO client2 = new ClientDTO("DUPONT", "Jacques", "e2", "tel2", "rue 2", "44860", "Saint Aignan Grand Lieu");
		clientService.ajouterClient(client);
		clientService.ajouterClient(client2);

		//Act
		List<Client> clients = clientService.trouverClientsParNom(nom);

		//Assert
		assertThat(clients).hasSize(2);

	}



	@Test
	@DisplayName("Test modification complète client")
	@Transactional
	public void testModifierClientEtAdresseCasPositif() {
		// Arrange
		ClientDTO clientDto = new ClientDTO("nX", "pX", "eX", "telX","rue des Cormorans", "44860", "Saint Aignan Grand Lieu");
		Client newClient = clientService.ajouterClient(clientDto);

		clientDto.setEmail("bob@free.fr");
		clientDto.setNom("nXX");
		clientDto.setPrenom("pXX");
		clientDto.setRue("rue Franklin");
		clientDto.setCodePostal("44800");
		clientDto.setVille("Saint Herblain");
		
		// Act
		clientService.modifierClient(newClient.getNoClient(),clientDto);

		// Assert
		Client client2 = clientService.trouverClientParId(newClient.getNoClient());
		assertThat(client2.getEmail()).isEqualTo(clientDto.getEmail());
		assertThat(client2.getNom()).isEqualTo(clientDto.getNom());
		assertThat(client2.getPrenom()).isEqualTo(clientDto.getPrenom());
		assertThat(client2.getAdresse().getRue()).isEqualTo(clientDto.getRue());
		assertThat(client2.getAdresse().getCodePostal()).isEqualTo(clientDto.getCodePostal());
		assertThat(client2.getAdresse().getVille()).isEqualTo(clientDto.getVille());

	}


	@Test
	@DisplayName("Test modification complète client cas client non trouvé")
	@Transactional
	public void testModifierClientEtAdresseCasClientNonTrouve() {
		// Arrange
		ClientDTO clientDto = new ClientDTO("nX", "pX", "eX", "telX","rue des Cormorans", "44860", "Saint Aignan Grand Lieu");


		// Act
		// Assert
		assertThrows(DataNotFound.class, ()-> clientService.modifierClient(9999,clientDto));

	}


	@Test
	@DisplayName("Test modification de l'adresse d'un client")
	@Transactional
	public void testModificationAdresseCasPositif() {
		// Arrange
		Adresse adresse = new Adresse("rue des Cormorans", "44860", "Saint Aignan Grand Lieu");
		Client client = new Client("nX", "pX", "eX", adresse);
		client.setNoTelephone("telX");
		ClientDTO clientDto = new ClientDTO();
		BeanUtils.copyProperties(client, clientDto);
		BeanUtils.copyProperties(adresse, clientDto);

		Client newClient = clientService.ajouterClient(clientDto);
		
		AdresseDTO newAdresse = new AdresseDTO();
		newAdresse.setRue("rue des mouettes");
		newAdresse.setCodePostal("79000");
		newAdresse.setVille("Niort");
		
		// Act
		Client clientBase = clientService.modifierAdresse(newClient.getNoClient(), newAdresse);

		// Assert
		Optional<Adresse> newAdresseOpt = adresseRepository.findById(newClient.getAdresse().getNoAdresse());

		if (newAdresseOpt.isEmpty()) {
			fail();
		} else {
			assertThat(newClient.getAdresse()).isEqualTo(newAdresseOpt.get());
		}
		
	}

}
