package pl.tomo.medicament.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Prescription {
	
	@OneToMany(mappedBy = "prescription", fetch = FetchType.LAZY)
	@Setter(value = AccessLevel.NONE)
	private Set<Medicament> medicaments;
	
	@Id
	@JsonProperty(value = "PrescriptionID")
	private int prescriptionID;
	
	@JsonProperty(value = "Name")
	private String name;
	
	@JsonProperty(value = "ShortName")
	private String shorName;
	
	@Type(type="true_false")
	private boolean active;
	
	
	public Prescription() {
		active = true;
	}

}
