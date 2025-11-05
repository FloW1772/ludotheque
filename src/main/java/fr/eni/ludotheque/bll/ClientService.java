package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.AdresseDTO;
import fr.eni.ludotheque.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    Client ajouterClient(ClientDTO clientDto);

    List<Client> trouverClientsParNom(String nom);

    Client modifierClient(String clientId, ClientDTO clientDto);

    Client trouverClientParId(String clientId);

    Client modifierAdresse(String clientId, AdresseDTO adresseDto);

    void supprimerClient(String clientId);
}
