package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dto.AdresseDTO;
import fr.eni.ludotheque.dto.ClientDTO;
import fr.eni.ludotheque.exceptions.DataNotFound;
import fr.eni.ludotheque.exceptions.EmailClientAlreadyExistException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    @NonNull
    private final ClientRepository clientRepository;

    @Override
    public Client ajouterClient(ClientDTO clientDto) {
        Client client = new Client();
        Adresse adresse = new Adresse();
        BeanUtils.copyProperties(clientDto, client);
        BeanUtils.copyProperties(clientDto, adresse);
        client.setAdresse(adresse);

        try {
            return clientRepository.save(client);
        } catch (DuplicateKeyException ex) {
            throw new EmailClientAlreadyExistException();
        }
    }

    @Override
    public List<Client> trouverClientsParNom(String nom) {
        return clientRepository.findByNomStartsWith(nom);
    }

    @Override
    public Client modifierClient(String clientId, ClientDTO clientDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new DataNotFound("Client", clientId));

        BeanUtils.copyProperties(clientDto, client);
        BeanUtils.copyProperties(clientDto, client.getAdresse());

        return clientRepository.save(client);
    }

    @Override
    public Client trouverClientParId(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new DataNotFound("Client", clientId));
    }

    @Override
    public Client modifierAdresse(String clientId, AdresseDTO adresseDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new DataNotFound("Client", clientId));

        BeanUtils.copyProperties(adresseDto, client.getAdresse());
        return clientRepository.save(client);
    }

    @Override
    public void supprimerClient(String clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new DataNotFound("Client", clientId);
        }
        clientRepository.deleteById(clientId);
    }
}
