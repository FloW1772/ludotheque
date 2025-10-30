package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.ClientDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @Controller + @ResponseBody == @RestController
public class ClientsRestController {

    @PostMapping("/clients")
    public Client ajouterClient(@RequestBody ClientDto clientDto) {
        return new Client("n1","p1", "e1",
                new Adresse("rue1","cp1","ville1"));
    }
}
