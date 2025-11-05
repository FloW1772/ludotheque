package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.dto.AdresseDTO;
import fr.eni.ludotheque.dto.ClientDTO;
import fr.eni.ludotheque.exceptions.DataNotFound;
import fr.eni.ludotheque.exceptions.EmailClientAlreadyExistException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exemplaires")
public class ExemplaireRestController {
    private  JeuService jeuService;

    public ExemplaireRestController(JeuService jeuService) {
        this.jeuService = jeuService;
    }


    @GetMapping("/{codebarre}/codebarre")
    public ResponseEntity<ApiResponse<Exemplaire>> findExemplaireByCodebarre(@PathVariable(name = "codebarre") String codebarre) {
        Exemplaire exemplaire = null;
        try {
            exemplaire = jeuService.trouverExemplaireByCodebarre(codebarre);
        } catch (DataNotFound notFound) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Exemplaire : codebarre " + codebarre + " non trouvé", null));
        }
        return ResponseEntity.ok(new ApiResponse(true, "ok", exemplaire));
    }


}
