package pl.tomo.medicament.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ATC {
	
	@Setter(value = AccessLevel.NONE)
	@ManyToMany(mappedBy = "atcs", fetch = FetchType.LAZY)
	private Set<Medicament> medicaments = new HashSet<Medicament>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonProperty(value = "ProductLineID")
	private int productLineID;
	
	@JsonProperty(value = "AtcCode")
	private String atcCode;
	
	@JsonProperty(value = "AtcName")
	private String atcName;
	
		
	
}
