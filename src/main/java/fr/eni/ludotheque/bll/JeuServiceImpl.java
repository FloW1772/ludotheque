package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.GenreRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.exceptions.DataNotFound;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JeuServiceImpl implements JeuService {

    @NonNull
    private final JeuRepository jeuRepository;

    @NonNull
    private final ExemplaireRepository exemplaireRepository;

    @NonNull
    private final GenreRepository genreRepository;

    @Override
    public void ajouterJeu(Jeu jeu) {
        jeuRepository.save(jeu);
    }

    @Override
    public Jeu trouverJeuParNoJeu(String jeuId) {
        return jeuRepository.findById(jeuId)
                .orElseThrow(() -> new DataNotFound("Jeu", jeuId));
    }

    @Override
    public List<Jeu> listeJeuxCatalogue(String filtreTitre) {
        List<Jeu> jeux = jeuRepository.findByTitreContainingIgnoreCase(filtreTitre);

        for (Jeu jeu : jeux) {
            long nbDisponibles = exemplaireRepository.findAll().stream()
                    .filter(e -> e.getJeu().getId().equals(jeu.getId()) && e.isLouable())
                    .count();
            jeu.setNbExemplairesDisponibles((int) nbDisponibles);
        }
        return jeux;
    }

    @Override
    public List<Jeu> listeJeuxCatalogueV2(String filtreTitre) {
        List<Jeu> jeux = listeJeuxCatalogue(filtreTitre);

        for (Jeu jeu : jeux) {
            List<Genre> genres = genreRepository.findAll(); // simplifié, peut filtrer par id si nécessaire
            jeu.setGenres(genres);
        }

        return jeux;
    }

    @Override
    public Exemplaire trouverExemplaireByCodebarre(String codebarre) {
        Exemplaire exemplaire = exemplaireRepository.findByCodebarre(codebarre);
        if (exemplaire == null) {
            throw new DataNotFound("Exemplaire", codebarre);
        }
        return exemplaire;
    }
}
