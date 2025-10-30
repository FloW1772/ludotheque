package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dto.ClientDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsServiceImpl implements ClientsService {

    private final ClientRepository clientRepository;

    public ClientsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client ajouterClient(ClientDto clientDto) {
        Client client = new Client();
        Adresse adresse = new Adresse();

        client.setNom(clientDto.nom());
        client.setPrenom(clientDto.prenom());
        client.setEmail(clientDto.email());
        client.setNoTelephone(clientDto.noTelephone());

        adresse.setRue(clientDto.rue());
        adresse.setCodePostal(clientDto.codePostal());
        adresse.setVille(clientDto.ville());

        client.setAdresse(adresse);

        return clientRepository.save(client);
    }

    @Override
    public List<Client> rechercherClientsParNomCommencePar(String prefixe) {
        return clientRepository.findByNomStartingWithIgnoreCase(prefixe);
    }

    @Override
    public Client modifierClient(int idClient, ClientDto clientDto) {
        Optional<Client> optClient = clientRepository.findById(idClient);
        if (optClient.isEmpty()) {
            throw new IllegalArgumentException("Client non trouvé avec l'id : " + idClient);
        }

        Client client = optClient.get();

        // Mise à jour des champs du client
        client.setNom(clientDto.nom());
        client.setPrenom(clientDto.prenom());
        client.setEmail(clientDto.email());
        client.setNoTelephone(clientDto.noTelephone());

        // Mise à jour ou création de l’adresse
        Adresse adresse = client.getAdresse();
        if (adresse == null) {
            adresse = new Adresse();
        }

        adresse.setRue(clientDto.rue());
        adresse.setCodePostal(clientDto.codePostal());
        adresse.setVille(clientDto.ville());

        client.setAdresse(adresse);

        return clientRepository.save(client);
    }

    @Override
    public Client modifierAdresseClient(int idClient, ClientDto clientDto) {
        Optional<Client> optClient = clientRepository.findById(idClient);
        if (optClient.isEmpty()) {
            throw new IllegalArgumentException("Client non trouvé avec l'id : " + idClient);
        }

        Client client = optClient.get();

        Adresse adresse = client.getAdresse();
        if (adresse == null) {
            adresse = new Adresse();
        }

        // On ne modifie que les champs d’adresse
        adresse.setRue(clientDto.rue());
        adresse.setCodePostal(clientDto.codePostal());
        adresse.setVille(clientDto.ville());

        client.setAdresse(adresse);

        return clientRepository.save(client);
    }
}
