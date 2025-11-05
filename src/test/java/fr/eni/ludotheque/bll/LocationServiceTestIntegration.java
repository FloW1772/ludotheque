package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Facture;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.bo.Location;
import fr.eni.ludotheque.dal.ClientRepository;
import fr.eni.ludotheque.dto.LocationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LocationServiceTestIntegration {

    @Autowired
    private LocationService locationService;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @Transactional
    public void testAjoutLocation() {
        //Arrange
        Client client = clientRepository.findByNoTelephone("123456789");

        LocationDTO locationDTO = new LocationDTO(client.getNoClient(), "6666666666666");

        //Act
        Location location = locationService.ajouterLocation(locationDTO);

        //Assert
        assertThat(location).isNotNull();
        assertThat(location.getDateDebut()).isNotNull();
        assertThat(location.getDateRetour()).isNull();
        assertThat(location.getTarifJour()).isEqualTo(9.3f);
    }


    @Test
    @Transactional
    @DisplayName("Test du retour d'exemplaire et creation de la facture")
    public void testRetourExemplairesEtCreationFacture() {
        //Arrange
        Client client = clientRepository.findByNoTelephone("123456789");
        LocationDTO locationDTO1 = new LocationDTO(client.getNoClient(), "6666666666666");
        Location loc1 = locationService.ajouterLocation(locationDTO1);
        LocationDTO locationDTO2 = new LocationDTO(client.getNoClient(), "1111111111111");
        Location loc2 = locationService.ajouterLocation(locationDTO2);

        List<String> codebarres = List.of("1111111111111", "6666666666666");

        //act
        Facture facture = locationService.retourExemplaires(codebarres);

        //Assert
        assertThat(facture).isNotNull();
        assertThat(facture.getPrix()).isEqualTo(21.8f);
        assertThat(facture.getLocations()).hasSize(2);
    }


    @Test
    @DisplayName("Test payer facture")
    @Transactional
    public void testPayerFacture() {
        //Arrange
        Client client = clientRepository.findByNoTelephone("123456789");
        LocationDTO locationDTO1 = new LocationDTO(client.getNoClient(), "6666666666666");
        Location loc1 = locationService.ajouterLocation(locationDTO1);
        LocationDTO locationDTO2 = new LocationDTO(client.getNoClient(), "1111111111111");
        Location loc2 = locationService.ajouterLocation(locationDTO2);
        List<String> codebarres = List.of("1111111111111", "6666666666666");
        Facture facture = locationService.retourExemplaires(codebarres);

        //act
        Facture facture2 = locationService.payerFacture(facture.getNoFacture());

        //Assert
        assertThat(facture2).isNotNull();
        assertThat(facture2.getDatePaiement()).isNotNull();
    }

}
