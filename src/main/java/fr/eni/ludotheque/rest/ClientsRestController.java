package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.ClientsService;
import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.ClientDto;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @Controller + @ResponseBody == @RestController
public class ClientsRestController {

    @NonNull
    private ClientsService clientsService;

    @PostMapping("/clients")
    public ResponseEntity<Client> ajouterClient(@RequestBody ClientDto clientDto) {

        Client client = clientsService.ajouterClient(clientDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }
}
