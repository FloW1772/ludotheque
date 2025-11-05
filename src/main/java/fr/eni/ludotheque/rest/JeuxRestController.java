package fr.eni.ludotheque.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bo.Jeu;

@RestController
public class JeuxRestController {
    private final JeuService jeuService;

    public JeuxRestController(JeuService jeuService) {
        this.jeuService = jeuService;
    }

    @GetMapping("/api/jeux")
    public ResponseEntity<ApiResponse<List<Jeu>>> listeJeuxCatalogue(@RequestParam(name="titre", required = false, defaultValue = "")String titre){
    	if(titre==null || "".equals(titre.trim())) {
    		titre = "TOUS";
    	}
    	List<Jeu> jeux =  jeuService.listeJeuxCatalogue(titre);
        return ResponseEntity.ok(new ApiResponse(true, "ok", jeux));
    }

    /* récupère la liste des jeux ainsi que le nombre d'exemplaire disponible en magasin DEPUIS UNE FONCTION SQLSERVER */
    @GetMapping("/api/v2/jeux")
    public ResponseEntity<ApiResponse<List<Jeu>>> listeJeuxCatalogueV2(@RequestParam(name="titre", required = false, defaultValue = "")String titre){
        if(titre==null || "".equals(titre.trim())) {
            titre = "TOUS";
        }
        List<Jeu> jeux =  jeuService.listeJeuxCatalogueV2(titre);
        return ResponseEntity.ok(new ApiResponse(true, "ok", jeux));
    }


}
