package fr.eni.ludotheque.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.eni.ludotheque.bll.ClientsService;
import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dto.ClientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientsRestController.class)
class ClientsRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientsService clientsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Client clientExistant;
    private ClientDto clientDto;

    @BeforeEach
    void setUp() {
        // --- Données simulées ---
        Adresse adresse = new Adresse();
        adresse.setRue("Rue de la Paix");
        adresse.setCodePostal("75000");
        adresse.setVille("Paris");

        clientExistant = new Client("Dupont", "Jean", "jean.dupont@email.com", adresse);
        clientExistant.setNoClient(1);
        clientExistant.setNoTelephone("0606060606");

        clientDto = new ClientDto(
                "Dupont",
                "Jean",
                "jean.dupont@email.com",
                "0606060606",
                "Rue de la Paix",
                "75000",
                "Paris"
        );
    }

    @Test
    void testModifierClient_Succes() throws Exception {
        // --- Mock comportement du service ---
        Mockito.when(clientsService.modifierClient(eq(1), any(ClientDto.class)))
                .thenReturn(clientExistant);

        // --- Exécution de la requête PUT ---
        mockMvc.perform(put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Dupont"))
                .andExpect(jsonPath("$.email").value("jean.dupont@email.com"));
    }

    @Test
    void testModifierClient_ClientNonTrouve() throws Exception {
        // --- Mock : client introuvable ---
        Mockito.when(clientsService.modifierClient(eq(999), any(ClientDto.class)))
                .thenThrow(new IllegalArgumentException("Client non trouvé avec l'id : 999"));

        // --- Exécution ---
        mockMvc.perform(put("/clients/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isNotFound());
    }
}
