package fr.eni.ludotheque.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.eni.ludotheque.bo.Exemplaire;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer>{

	@Query(nativeQuery = true,
			value="select count(e.codebarre) from jeux j inner join exemplaires e "
					+ "on j.no_jeu = e.no_jeu "
					+ "where j.no_jeu = :noJeu "
					+ "and e.louable = 1 "
					+ "and e.no_exemplaire not in "
					+ "(select l.no_exemplaire from locations l "
					+ "where l.date_retour IS null "
					+ "and l.no_exemplaire = e.no_exemplaire) "
					+ " group by e.codebarre ")
	int nbExemplairesDisponibleByNoJeu(@Param("noJeu") Integer noJeu);
	
	//@Query("SELECT e FROM Exemplaire e JOIN FETCH e.jeu WHERE e.codebarre = :codebarre")
	//Exemplaire findByCodebarreWithJeu(@Param("codebarre") String codebarre);

	Exemplaire findByCodebarre(String codebarre);
	
	
	
}
