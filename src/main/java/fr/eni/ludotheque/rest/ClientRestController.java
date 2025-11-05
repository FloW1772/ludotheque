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


    @GetMapping("/{noClient}")
    //@RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE}, "/{noClient}")
    public ResponseEntity<ApiResponse<Client>> findClientById(@PathVariable(name = "noClient") Integer noClient) {
        Client client = null;
        try {
            client = clientService.trouverClientParId(noClient);
        } catch (DataNotFound notFound) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Client : " + noClient + " non trouvé", null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "ok", client));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Client>> ajouterClient(@Valid @RequestBody ClientDTO client,
                                                             BindingResult result) {

        if (result.hasErrors()) {
            String errors = result.getFieldErrors()
                    .stream()
                    .map(f -> f.getField() + " : " +
                            f.getDefaultMessage()).collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(new ApiResponse(false, errors, null));
        }
        Client nouveauClient = null;
        try {
            nouveauClient = clientService.ajouterClient(client);
        } catch (EmailClientAlreadyExistException e) {
            ApiResponse<Client> apiResponse = new ApiResponse(false, "erreur de validation: email existe déjà.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        ApiResponse<Client> apiResponse = new ApiResponse(true, "ok", nouveauClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }


    @GetMapping()
    public ResponseEntity<ApiResponse<List<Client>>> findClientsByNomCommencePar(@RequestParam(required = false, defaultValue = "") String filtreNom) {
        List<Client> clientList = clientService.trouverClientsParNom(filtreNom);
        String message = clientList.size() > 0 ? "ok" : "aucun client trouvé";

        return ResponseEntity.ok(new ApiResponse(true, message, clientList));
    }

    /* Modification complète d'un client */
    @PutMapping("/{noClient}")
    public ResponseEntity<ApiResponse<Client>> modifierClient(@PathVariable(name = "noClient") Integer noClient, @RequestBody ClientDTO clientDto) {
        Client client = null;
        try {
            client = clientService.modifierClient(noClient, clientDto);
        } catch (DataNotFound notFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Client : " + noClient + " non trouvé", null));
        }

        ApiResponse<Client> apiResponse = new ApiResponse(true, "ok", client);
        return ResponseEntity.ok(apiResponse);
    }


    /* Modification de l'adresse d'un client */
    @PatchMapping("/{noClient}/adresse")
    public ResponseEntity<ApiResponse<Client>> modifierAdresse(@PathVariable(name = "noClient") Integer noClient, @RequestBody AdresseDTO adresseDto) {
        Client client = clientService.modifierAdresse(noClient, adresseDto);
        ApiResponse<Client> apiResponse = new ApiResponse(true, "ok", client);
        return ResponseEntity.ok(apiResponse);
    }


    @DeleteMapping("/{noClient}")
    public ResponseEntity<ApiResponse<?>> supprimerClient(@PathVariable(name = "noClient") int noClient) {
        try {
            clientService.supprimerClient(noClient);
        } catch (DataNotFound notFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Client : " + noClient + " non trouvé", null));
        }

        ApiResponse<Client> apiResponse = new ApiResponse(true, "ok", null);

        return ResponseEntity.ok(null);
    }
}
