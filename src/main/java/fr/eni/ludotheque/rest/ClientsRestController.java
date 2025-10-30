package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.ClientsService;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.ClientDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientsRestController {

    private final ClientsService clientsService;

    public ClientsRestController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    // --- Ajouter un client ---
    @PostMapping("/clients")
    public ResponseEntity<Client> ajouterClient(@RequestBody ClientDto clientDto) {
        Client client = clientsService.ajouterClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    // --- Supprimer un client ---
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> supprimerClient(@PathVariable("id") int idClient) {
        try {
            clientsService.supprimerClient(idClient); // à implémenter dans la BLL
            return ResponseEntity.noContent().build(); // HTTP 204
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 si client non trouvé
        }
    }
}
