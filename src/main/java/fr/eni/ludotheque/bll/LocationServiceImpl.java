package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Facture;
import fr.eni.ludotheque.bo.Location;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.FactureRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.dal.LocationRepository;
import fr.eni.ludotheque.dto.LocationDTO;
import fr.eni.ludotheque.exceptions.DataNotFound;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    @NonNull
    private final LocationRepository locationRepository;

    @NonNull
    private final JeuRepository jeuRepository;

    @NonNull
    private final ExemplaireRepository exemplaireRepository;

    @NonNull
    private final FactureRepository factureRepository;

    @Override
    public Location ajouterLocation(LocationDTO locationDto) {
        Exemplaire exemplaire = exemplaireRepository.findByCodebarre(locationDto.getCodebarre());
        if (exemplaire == null) {
            throw new DataNotFound("Exemplaire", locationDto.getCodebarre());
        }

        Client client = new Client();
        client.setId(locationDto.getNoClient());

        Location location = new Location(LocalDateTime.now(), client, exemplaire);
        location.setTarifJour(jeuRepository.findById(exemplaire.getJeu().getId())
                .orElseThrow(() -> new DataNotFound("Jeu", exemplaire.getJeu().getId()))
                .getTarifJour());

        return locationRepository.save(location);
    }

    @Override
    public Facture retourExemplaires(List<String> codebarres) {
        Facture facture = new Facture();
        float prix = 0;

        for (String codebarre : codebarres) {
            Location location = locationRepository.findByExemplaire_Codebarre(codebarre);
            if (location == null) continue;

            location.setDateRetour(LocalDateTime.now());
            locationRepository.save(location);

            facture.addLocation(location);

            long nbJours = ChronoUnit.DAYS.between(location.getDateDebut(), location.getDateRetour()) + 1;
            prix += nbJours * location.getTarifJour();
        }

        facture.setPrix(prix);
        return factureRepository.save(facture);
    }

    @Override
    public Facture payerFacture(String factureId) {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new DataNotFound("Facture", factureId));

        facture.setDatePaiement(LocalDateTime.now());
        return factureRepository.save(facture);
    }

    @Override
    public Location trouverLocationParExemplaireCodebarre(String codebarre) {
        Location location = locationRepository.findByExemplaire_Codebarre(codebarre);
        if (location == null) {
            throw new DataNotFound("Location", codebarre);
        }
        return location;
    }
}
