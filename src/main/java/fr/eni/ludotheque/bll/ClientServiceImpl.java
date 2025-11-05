package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.AdresseRepository;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dto.AdresseDTO;
import fr.eni.ludotheque.dto.ClientDTO;
import fr.eni.ludotheque.exceptions.DataNotFound;
import fr.eni.ludotheque.exceptions.EmailClientAlreadyExistException;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService{
	@NonNull
	private ClientRepository clientRepository;
	@NonNull
	private AdresseRepository adresseRepository;
	
	@Override
	public Client ajouterClient( ClientDTO clientDto)  {

		Client client = new Client();
		Adresse adresse = new Adresse();
		BeanUtils.copyProperties(clientDto, client);
		BeanUtils.copyProperties(clientDto, adresse);
		client.setAdresse(adresse);
		Client newClient = null;
		try {
			newClient = clientRepository.save(client);
		}catch(DataIntegrityViolationException ex) {
			throw new EmailClientAlreadyExistException();
		}

		return newClient;
	}

	@Override
	public List<Client> trouverClientsParNom(String nom) {

		return clientRepository.findByNomStartsWith(nom);
	}

	@Override
	/* S2010 - Modification complète d'un client */
	public Client modifierClient(Integer noClient, ClientDTO clientDto) {
		//Client client = clientRepository.findById(noClient).orElseThrow(()->new DataNotFound("Client", noClient));
		Client client = new Client();
		client.setNoClient(noClient);
		client.setAdresse(new Adresse());
		BeanUtils.copyProperties(clientDto, client);
		BeanUtils.copyProperties(clientDto, client.getAdresse());
		Client clientBD = null;
		try {
			clientBD = clientRepository.save(client);
		} catch (OptimisticLockingFailureException e) {//thrown if entity is assumed to be present but does not exists in db
			//e.printStackTrace();
			throw new DataNotFound("Client", noClient);
		}

		return clientBD;
	}

	@Override
	public Client trouverClientParId(Integer id)  {

		Optional<Client> optClient = clientRepository.findById(id);
		if(optClient.isEmpty()) {
			throw new DataNotFound("Client", id);
		}
		return optClient.get();
	}

	@Override
	public Client modifierAdresse(Integer noClient, AdresseDTO adresseDto) {
		Client client = clientRepository.findById(noClient).orElseThrow(()->new DataNotFound("Client", noClient));

		BeanUtils.copyProperties(adresseDto, client.getAdresse());

		adresseRepository.save(client.getAdresse());

		return client;

	}

	@Override
	public void supprimerClient(int noClient) {

		try {
			clientRepository.deleteById(noClient);
		}catch (OptimisticLockingFailureException exc){
			throw new DataNotFound("Client", noClient);
		}
	}

}
