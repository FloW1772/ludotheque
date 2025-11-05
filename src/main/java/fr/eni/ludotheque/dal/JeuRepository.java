package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dto.JeuDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JeuRepository extends JpaRepository<Jeu, Integer>{
    @Query(nativeQuery = true, value = "select ex.no_jeu, j.titre, j.reference, j.description, j.tarif_jour, j.duree, j.age_min "
            // version ci dessous ne fonctionne pas car nbExemplairesDisponibles transient dans Jeu
            //@Query(nativeQuery = true, value = "select ex.no_jeu, j.titre, j.reference, j.description, j.tarif_jour, j.duree, j.age_min, COUNT(ex.no_jeu) as nbExemplairesDisponibles "
            + " from jeux j left join exemplaires ex on j.no_jeu = ex.no_jeu "
            + " where ex.louable = 1 "
          //  + " and ex.no_exemplaire not in (select l.no_exemplaire from locations l where date_retour is null) "
            + " and (:titre = 'TOUS' OR titre like '%' + :titre + '%')"
            + " group by ex.no_jeu, j.titre, j.reference, j.description, j.tarif_jour, j.duree, j.age_min ")
    List<Jeu> findAllJeuxAvecNbExemplaires(@Param("titre") String titre);

    @Query(nativeQuery = true, value="select tarif_jour from jeux where no_jeu = :noJeu")
    Float findTarifJour(@Param("noJeu") Integer noJeu);

    Jeu findByReference(String reference);

    @Query(nativeQuery = true, value="select noJeu, titre, reference, ageMin, description,  duree, tarifJour, nbExemplairesDisponibles from dbo.listeJeux(:titre)")
    List<JeuDTO> findAllJeuxAvecNbExemplairesV2(@Param("titre") String titre);


}
