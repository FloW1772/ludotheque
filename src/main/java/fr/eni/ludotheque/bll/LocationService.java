package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Facture;
import fr.eni.ludotheque.bo.Location;
import fr.eni.ludotheque.dto.LocationDTO;

import java.util.List;

public interface LocationService {
	
	Location ajouterLocation(LocationDTO locationDto);
	
	Facture retourExemplaires( List<String> codebarres);

	Facture payerFacture( Integer noFacture);

	void trouverLocationParExemplaireCodebarre(String codebarre);
}
