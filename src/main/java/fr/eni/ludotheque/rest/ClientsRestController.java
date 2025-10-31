package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.ClientsService;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.ClientDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientsRestController {

    private final ClientsService clientsService;

    public ClientsRestController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    // --- Ajouter un client ---
    @PostMapping
    public ResponseEntity<Client> ajouterClient(@RequestBody ClientDto clientDto) {
        Client client = clientsService.ajouterClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    // --- Supprimer un client ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerClient(@PathVariable("id") int idClient) {
        try {
            clientsService.supprimerClient(idClient);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Modifier complètement un client (infos + adresse) ---
    @PutMapping("/{id}")
    public ResponseEntity<Client> modifierClient(
            @PathVariable("id") int idClient,
            @RequestBody ClientDto clientDto
    ) {
        try {
            Client client = clientsService.modifierClient(idClient, clientDto);
            return ResponseEntity.ok(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Modifier uniquement l'adresse d'un client ---
    @PatchMapping("/{id}/adresse")
    public ResponseEntity<Client> modifierAdresseClient(
            @PathVariable("id") int idClient,
            @RequestBody ClientDto clientDto
    ) {
        try {
            Client client = clientsService.modifierAdresseClient(idClient, clientDto);
            return ResponseEntity.ok(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Rechercher les clients dont le nom commence par une chaîne ---
    @GetMapping("/recherche")
    public ResponseEntity<List<Client>> rechercherClients(
            @RequestParam("nom") String prefixe
    ) {
        List<Client> clients = clientsService.rechercherClientsParNomCommencePar(prefixe);
        return ResponseEntity.ok(clients);
    }

    // --- Rechercher un client par son ID ---
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") int idClient) {
        try {
            Client client = clientsService.trouverClientParId(idClient);
            return ResponseEntity.ok(client);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
