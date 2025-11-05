package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="JEUX")
public class Jeu {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="no_jeu")
	private Integer noJeu;
	
	@Column( length=50, nullable=false)
	@NonNull
	private String titre;

	@EqualsAndHashCode.Include
	@Column(length=13, nullable=false, unique=true)
	@NonNull private String reference;
	
	@Column(nullable=true)
	private int ageMin;
	
	@Column( nullable=true)
	private String description;

	private int duree;
	
	@Column(nullable=false)
	@NonNull
	private Float tarifJour;

	@Transient
	private Integer nbExemplairesDisponibles;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "JEUX_GENRES", 
		joinColumns = @JoinColumn(name="no_jeu"),
		inverseJoinColumns = @JoinColumn(name="no_genre"))
	private List<Genre> genres = new ArrayList<>();
	
	public void addGenre(Genre g) {
		genres.add(g);
	}
}
