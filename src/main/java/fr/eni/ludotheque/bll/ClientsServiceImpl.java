package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dto.ClientDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientsServiceImpl implements ClientsService {
    private ClientRepository clientRepository ;

    public ClientsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client ajouterClient( ClientDto clientDto) {
        //
        Client client = new Client();

        BeanUtils.copyProperties(clientDto, client);
        Adresse adresse = new Adresse();
        BeanUtils.copyProperties(clientDto, adresse);
        client.setAdresse(adresse);

        Client newClient = clientRepository.save(client);
        return newClient;
    }
}
