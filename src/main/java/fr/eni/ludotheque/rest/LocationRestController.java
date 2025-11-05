package fr.eni.ludotheque.rest;

import fr.eni.ludotheque.bo.Facture;
import fr.eni.ludotheque.dto.RetourLocationsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.eni.ludotheque.bll.LocationService;
import fr.eni.ludotheque.bo.Location;
import fr.eni.ludotheque.dto.LocationDTO;

@RestController
@RequestMapping("/api/locations")
public class LocationRestController {
    private final LocationService locationService;

    public LocationRestController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Location>> ajouterLocation(@RequestBody LocationDTO locationDto){
    	Location newLocation = locationService.ajouterLocation(locationDto);
    	return ResponseEntity.ok(new ApiResponse(true, "ajout ok", newLocation));
    	
    }

    /*
    Objectif : Retour exemplaires : enregistrer le retour des exemplaires et créer la facture
     */
    @PostMapping("/retour")
    public ResponseEntity<ApiResponse<Facture>> retourLocations(@RequestBody RetourLocationsDTO retourLocationsDto){
        Facture facture = locationService.retourExemplaires(retourLocationsDto.getCodebarres());
        return ResponseEntity.ok(new ApiResponse(true, "ok", facture));
    }

}
