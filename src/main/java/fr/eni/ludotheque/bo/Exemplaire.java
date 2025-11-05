package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="EXEMPLAIRES")
public class Exemplaire {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Exclude
	private Integer noExemplaire;
	
	@Column(length=13, nullable = false, unique = true)
	@NonNull private String codebarre;
	
	@Basic(optional = false)
	private boolean louable=true;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="no_jeu", referencedColumnName = "no_jeu")
	@NonNull
	private Jeu jeu;
	
}
