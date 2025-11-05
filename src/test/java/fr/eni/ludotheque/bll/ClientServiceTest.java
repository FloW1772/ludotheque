package fr.eni.ludotheque.bll;

//import static org.mockito.Mockito.doAnswer;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dto.ClientDTO;
import fr.eni.ludotheque.exceptions.DataNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceTest {
	
	@Autowired
	private ClientService clientService;
	
	@MockitoBean
	private ClientRepository clientRepository;
	
	@Test
	@DisplayName("Ajout d'un client cas positif")
	public void testAjouterClientCasPositif() {
		//Arrange
		ClientDTO clientDto = new ClientDTO("n1", "p1", "e1", "tel1", "rue des Cormorans", "44860", "Saint Aignan Grand Lieu");
		Adresse adresse = new Adresse("rue des Cormorans", "44860", "Saint Aignan Grand Lieu");
		Client client = new Client("n1", "p1", "e1", adresse);
		client.setNoTelephone("tel1");
		client.setNoClient(999);

		/*
		org.mockito.Mockito.doAnswer((invocation) -> {
			Client cli = invocation.getArgument(0);
			cli.setNoClient(999);
			return cli;
						}).when(clientRepository).save(client);
		 */
		when(clientRepository.save(any(Client.class))).thenReturn(client);

		//Act
		Client clientActual = clientService.ajouterClient(clientDto);

		//Assert
		assertThat(clientActual).isNotNull();
		assertThat(clientActual.getNoClient()).isNotNull();
		assertThat(clientActual.getNoClient()).isEqualTo(999);
		
	}

	@Test
	@DisplayName("Trouver un client par id cas id est connu")
	public void testTrouverClientParIdCasIdConnu() {
		//Arrange
		Integer idClientRecherche = 99;
		Adresse adresse = new Adresse("rue des Cormorans", "44860", "Saint Aignan Grand Lieu");
		Client clientATrouver = new Client("n1", "p1", "e1",  adresse);
		clientATrouver.setNoTelephone("tel1");
		when(clientRepository.findById(idClientRecherche)).thenReturn(Optional.of(clientATrouver));

		//Act
		Client client = clientService.trouverClientParId(idClientRecherche);

		//Assert
		assertThat(client).isEqualTo(clientATrouver);

	}

	@Test
	@DisplayName("Trouver un client par id cas id est inconnu-doit renvoyer une exception ")
	public void testTrouverClientParIdCasIdIncconnu() {
		//Arrange
		Integer idClientRecherche = 99;
		when(clientRepository.findById(idClientRecherche)).thenReturn(Optional.empty());

		//Act + Assert
		assertThrows(DataNotFound.class, ()->clientService.trouverClientParId(idClientRecherche) );
/*
		try{
			clientRepository.findById(idClientRecherche);
			fail();
		}catch(DataNotFound ex){
*/


	}

	@Test
	@DisplayName("Trouver les clients dont le nom commence par")
	public void testTrouverClientsDontLeNomCommencePar() {
		//Arrange
		String nom = "DUP";
		Adresse adresse = new Adresse("rue des Cormorans", "44860", "Saint Aignan Grand Lieu");
		Client clientATrouver = new Client("DUPIEUX", "Quentin", "e1",  adresse);
		clientATrouver.setNoTelephone("tel1");
		Adresse adresse2 = new Adresse("rue 2", "44860", "Saint Aignan Grand Lieu");
		Client clientATrouver2 = new Client("DUPONT", "Jacques", "e2", adresse2);
		clientATrouver2.setNoTelephone("tel2");

		List<Client> listeClients = new ArrayList<>();
		listeClients.add(clientATrouver);
		listeClients.add(clientATrouver2);
		when(clientRepository.findByNomStartsWith(nom)).thenReturn(listeClients);
		
		//Act
		List<Client> clients = clientService.trouverClientsParNom(nom);
		
		//Assert
		assertThat(clients).hasSize(2);
		
	}



	/*
	@Test
	@DisplayName("Test modification complète client")
	//@Transactional
	public void testModifierClient() {
		//Arrange 
		Adresse adresse = new Adresse("rue des Cormorans", "44860", "Saint Aignan Grand Lieu");
		Client client = new Client("nX", "pX", "eX", "telX", adresse);
		org.mockito.Mockito.doAnswer((invocation) -> {
			Client cli = invocation.getArgument(0);
			cli.setNoClient(999);
			return cli;
						}).when(clientRepository).save(client);		
		clientService.ajouterClient(client);

		client.setEmail("bob@free.fr");
		//Act
		clientService.modifierClient(client);
		
		//Assert
		Client client2 = clientService.trouverClientParId(client.getNoClient());
		assertThat(client2.getEmail()).isEqualTo(client.getEmail());
	}
	*/
	

}



