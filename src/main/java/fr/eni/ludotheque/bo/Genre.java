package fr.eni.ludotheque.bo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="GENRES")
public class Genre {
	@Id
	@NonNull private Integer noGenre;
	@NonNull private String libelle;
	
}
