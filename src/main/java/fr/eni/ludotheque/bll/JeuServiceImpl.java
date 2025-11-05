package fr.eni.ludotheque.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.dal.GenreRepository;
import fr.eni.ludotheque.dto.JeuDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.exceptions.DataNotFound;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JeuServiceImpl implements JeuService{
	@NonNull
	private JeuRepository jeuRepository;
	
	@NonNull
	private ExemplaireRepository exemplaireRepository;

	@NonNull
	private GenreRepository genreRepository;

	@Override
	public void ajouterJeu(Jeu jeu) {
		
		jeuRepository.save(jeu);
		
		
	}


	@Override
	public Jeu trouverJeuParNoJeu(Integer noJeu) {
		Optional<Jeu> optJeu = jeuRepository.findById(noJeu);
		
		if(optJeu.isEmpty()) {
			throw new DataNotFound("Jeu", noJeu);
		}
		return optJeu.get();
		
	}


	@Override
	public List<Jeu> listeJeuxCatalogue(String filtreTitre) {
		List<Jeu> jeux = jeuRepository.findAllJeuxAvecNbExemplaires(filtreTitre);
		
		for(Jeu jeu : jeux) {
			jeu.setNbExemplairesDisponibles(exemplaireRepository.nbExemplairesDisponibleByNoJeu(jeu.getNoJeu()));
		}

		return jeux;
	}

	@Override
	public List<Jeu> listeJeuxCatalogueV2(String filtreTitre) {
		List<JeuDTO> jeuxDTO = jeuRepository.findAllJeuxAvecNbExemplairesV2(filtreTitre);
		List<Jeu> jeux = new ArrayList<>();

		List<Genre> genres = null;
		for(JeuDTO dto : jeuxDTO){
			Jeu jeu = new Jeu();
			BeanUtils.copyProperties(dto, jeu);
			jeu.setGenres(genreRepository.findGenresByNoJeu(dto.getNoJeu()));
			jeux.add(jeu);
		}

		return jeux;
	}

	@Override
	public Exemplaire trouverExemplaireByCodebarre(String codebarre) {

		Exemplaire exemplaire = exemplaireRepository.findByCodebarre(codebarre);

		if(exemplaire==null){
			throw new DataNotFound("Exemplaire",codebarre);
		}

		return exemplaire;
	}


}
