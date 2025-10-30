package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.ExemplaireRepository;
import fr.eni.ludotheque.dal.GenreRepository;
import fr.eni.ludotheque.dal.JeuRepository;
import fr.eni.ludotheque.dto.JeuDisponibleDto;
import fr.eni.ludotheque.dto.JeuDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JeuxServiceImpl implements JeuxService {

    private final JeuRepository jeuRepository;
    private final GenreRepository genreRepository;
    private final ExemplaireRepository exemplaireRepository;

    public JeuxServiceImpl(JeuRepository jeuRepository,
                           GenreRepository genreRepository,
                           ExemplaireRepository exemplaireRepository) {
        this.jeuRepository = jeuRepository;
        this.genreRepository = genreRepository;
        this.exemplaireRepository = exemplaireRepository;
    }

    @Override
    public Jeu ajouterJeu(JeuDto jeuDto) {
        Jeu jeu = new Jeu(jeuDto.titre(), jeuDto.reference(), jeuDto.tarifJour());
        jeu.setAgeMin(jeuDto.ageMin());
        jeu.setDescription(jeuDto.description());
        jeu.setDuree(jeuDto.duree());

        if (jeuDto.idsGenres() != null && !jeuDto.idsGenres().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(jeuDto.idsGenres());
            jeu.getGenres().addAll(genres);
        }

        return jeuRepository.save(jeu);
    }

    @Override
    public List<JeuDisponibleDto> consulterJeuxDisponibles(String filtreTitre, List<Integer> idsGenres) {
        List<Jeu> jeux = jeuRepository.findAll();

        // Application du filtre par titre
        if (filtreTitre != null && !filtreTitre.isBlank()) {
            jeux = jeux.stream()
                    .filter(j -> j.getTitre().toLowerCase().contains(filtreTitre.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Application du filtre par genres
        if (idsGenres != null && !idsGenres.isEmpty()) {
            jeux = jeux.stream()
                    .filter(j -> j.getGenres().stream().anyMatch(g -> idsGenres.contains(g.getNoGenre())))
                    .collect(Collectors.toList());
        }

        // Construction du DTO avec le nombre d’exemplaires louables
        return jeux.stream()
                .map(jeu -> {
                    long nbDispo = exemplaireRepository.findByJeu(jeu).stream()
                            .filter(Exemplaire::isLouable)
                            .count();
                    return new JeuDisponibleDto(jeu.getNoJeu(), jeu.getTitre(), nbDispo);
                })
                .filter(dto -> dto.nbExemplairesDispo() > 0)
                .collect(Collectors.toList());
    }
}
