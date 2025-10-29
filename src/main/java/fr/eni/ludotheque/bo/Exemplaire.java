package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Exemplaire {
	@Id
	@GeneratedValue()
	private Integer noExemplaire;
	
	@Column(length=13, nullable = false, unique = true)
	@NonNull private String codebarre;
	
	@Basic(optional = false)
	private boolean louable=true;
	
	@ManyToOne
	@JoinColumn(name="no_jeu", referencedColumnName = "no_jeu")
	@NonNull
	private Jeu jeu;
	
}
