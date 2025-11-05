package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.AdresseDTO;
import fr.eni.ludotheque.dto.ClientDTO;
import fr.eni.ludotheque.exceptions.DataNotFound;
import fr.eni.ludotheque.exceptions.EmailClientAlreadyExistException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {
    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Client>> findClientById(@PathVariable String id) {
        Client client;
        try {
            client = clientService.trouverClientParId(id);
        } catch (DataNotFound notFound) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Client : " + id + " non trouvé", null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "ok", client));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Client>> ajouterClient(@Valid @RequestBody ClientDTO clientDto,
                                                             BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(f -> f.getField() + " : " + f.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new ApiResponse(false, errors, null));
        }
        try {
            Client nouveauClient = clientService.ajouterClient(clientDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "ok", nouveauClient));
        } catch (EmailClientAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Email existe déjà", null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Client>>> findClientsByNomCommencePar(
            @RequestParam(required = false, defaultValue = "") String filtreNom) {
        List<Client> clients = clientService.trouverClientsParNom(filtreNom);
        String message = clients.isEmpty() ? "aucun client trouvé" : "ok";
        return ResponseEntity.ok(new ApiResponse(true, message, clients));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Client>> modifierClient(@PathVariable String id,
                                                              @RequestBody ClientDTO clientDto) {
        try {
            Client client = clientService.modifierClient(id, clientDto);
            return ResponseEntity.ok(new ApiResponse(true, "ok", client));
        } catch (DataNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Client : " + id + " non trouvé", null));
        }
    }

    @PatchMapping("/{id}/adresse")
    public ResponseEntity<ApiResponse<Client>> modifierAdresse(@PathVariable String id,
                                                               @RequestBody AdresseDTO adresseDto) {
        try {
            Client client = clientService.modifierAdresse(id, adresseDto);
            return ResponseEntity.ok(new ApiResponse(true, "ok", client));
        } catch (DataNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Client : " + id + " non trouvé", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> supprimerClient(@PathVariable String id) {
        try {
            clientService.supprimerClient(id);
            return ResponseEntity.ok(new ApiResponse(true, "ok", null));
        } catch (DataNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Client : " + id + " non trouvé", null));
        }
    }
}
