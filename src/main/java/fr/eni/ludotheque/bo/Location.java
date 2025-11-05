package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "LOCATIONS")
public class Location {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer noLocation;

	@EqualsAndHashCode.Include
	@Basic(optional = false)
	@NonNull private LocalDateTime dateDebut;
	@Basic(optional = true)
	private LocalDateTime dateRetour;
	@Basic(optional = false)
	private float tarifJour;

	@EqualsAndHashCode.Include
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name="no_client")
	@NonNull private Client client;

	@EqualsAndHashCode.Include
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name="no_exemplaire")
	@NonNull private Exemplaire exemplaire;
	

}
