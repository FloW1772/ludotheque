package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.ClientDto;

public interface ClientsService {

    Client ajouterClient(ClientDto c);

}
