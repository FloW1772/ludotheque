package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="FACTURES")
public class Facture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer noFacture;
	
	@Basic(optional = true)
	private LocalDateTime datePaiement;
	
	@OneToMany
	@JoinColumn(name="no_facture")
	private List<Location> locations=new ArrayList<Location>();
	
	
	//@Transient // attribut calculable  
	private float prix;
	
	public void addLocation(Location location) {
		this.locations.add(location);
	}
}
