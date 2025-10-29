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
@Table(name="CLIENTS")
public class Client {
	@Id
	@GeneratedValue()
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
