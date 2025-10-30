package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Location;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.LocationRepository;
import fr.eni.ludotheque.dto.LocationDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationsServiceImpl implements LocationsService {

    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;
    private final ExemplaireRepository exemplaireRepository;

    public LocationsServiceImpl(LocationRepository locationRepository,
                                ClientRepository clientRepository,
                                ExemplaireRepository exemplaireRepository) {
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
        this.exemplaireRepository = exemplaireRepository;
    }

    @Override
    public Location creerLocation(LocationDto dto) {
        // Vérification du client
        Optional<Client> optClient = clientRepository.findById(dto.idClient());
        if (optClient.isEmpty()) {
            throw new IllegalArgumentException("Client non trouvé avec l'id : " + dto.idClient());
        }

        // Vérification de l’exemplaire
        Optional<Exemplaire> optExemplaire = exemplaireRepository.findById(dto.idExemplaire());
        if (optExemplaire.isEmpty()) {
            throw new IllegalArgumentException("Exemplaire non trouvé avec l'id : " + dto.idExemplaire());
        }

        Exemplaire exemplaire = optExemplaire.get();
        if (!exemplaire.isLouable()) {
            throw new IllegalStateException("Exemplaire déjà loué !");
        }

        // Création de la location
        Location location = new Location();
        location.setClient(optClient.get());
        location.setExemplaire(exemplaire);
        location.setDateDebut(dto.dateDebut());
        location.setDateRetourPrevue(dto.dateRetourPrevue());

        // Mise à jour de la disponibilité
        exemplaire.setLouable(false);

        // Sauvegarde
        exemplaireRepository.save(exemplaire);
        return locationRepository.save(location);
    }
}
