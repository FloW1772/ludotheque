package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.ClientDto;

import java.util.List;

public interface ClientsService {

    Client ajouterClient(ClientDto clientDto);

    List<Client> rechercherClientsParNomCommencePar(String prefixe);

    Client modifierClient(int idClient, ClientDto clientDto); // méthode de modification complète
}
