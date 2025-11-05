package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ExemplaireRepositoryTest {

	@Autowired
	private JeuRepository jeuRepository;

	@Autowired
	private ExemplaireRepository exemplaireRepository;
	
	@Test
	@DisplayName("test création exemplaire cas positif")
	@Transactional
	public void testCreationExemplaire() {
		//Arrange
		Jeu jeu = new Jeu("SkyJo", "refSkyJo", 5.6f );
		jeu.setAgeMin(8);
		jeu.setDescription("Descr skyjo");
		jeu.addGenre(new Genre(1, ""));
		jeu.addGenre(new Genre(2, ""));
		Jeu jeuBD = jeuRepository.save(jeu);
		Exemplaire skyJoBoite1 = new Exemplaire("1414141414141", jeuBD);

		//Act
		Exemplaire exemplaireBD = exemplaireRepository.save(skyJoBoite1);
		
		//Assert
		Exemplaire exemplaireFromBD = exemplaireRepository.findById(exemplaireBD.getNoExemplaire()).orElse(null);
		assertThat(exemplaireFromBD).isNotNull();
		assertThat(exemplaireFromBD.getNoExemplaire()).isNotNull();
		assertThat(exemplaireFromBD.getCodebarre()).isEqualTo(skyJoBoite1.getCodebarre());
		assertThat(exemplaireFromBD.isLouable()).isEqualTo(skyJoBoite1.isLouable());
		assertThat(exemplaireFromBD.getJeu()).isEqualTo(skyJoBoite1.getJeu());

	}

	@Test
	@DisplayName("test de la méthode findByCodebarre cas positif")
	@Transactional
	public void testFindByCodebarre() {

		//act
		Exemplaire exemplaire = exemplaireRepository.findByCodebarre("1111111111111");

		//Assert
		assertThat(exemplaire).isNotNull();
		assertThat(exemplaire.getCodebarre()).isEqualTo("1111111111111");
		assertThat(exemplaire.isLouable()).isTrue();
		assertThat(exemplaire.getJeu()).isNotNull();
		assertThat(exemplaire.getJeu().getReference()).isEqualTo("refPandemic");
	}

	}
