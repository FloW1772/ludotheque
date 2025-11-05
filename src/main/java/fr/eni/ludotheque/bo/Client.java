package fr.eni.ludotheque.bo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="CLIENTS")
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Exclude
	private Integer noClient;
	
	@Column(length = 50, nullable = false)
	@NonNull private String nom;
	
	@Column(length = 50, nullable = false)
	@NonNull private String prenom;
	
	@Column(length = 50, nullable = false, unique = true)
	@NonNull private String email;
	
	@Column(length = 15, nullable = false)
	private String noTelephone;

	@NonNull
	@OneToOne(cascade = CascadeType.ALL,
			orphanRemoval = true, optional = false,
			fetch = FetchType.EAGER)
	@JoinColumn(name = "no_adresse")
	private Adresse adresse;
}
