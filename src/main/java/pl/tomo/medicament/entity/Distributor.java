package pl.tomo.medicament.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Distributor {
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy = "distributor", fetch = FetchType.LAZY)
	private Set<Medicament> medicaments;

	@Id
	@JsonProperty(value = "DistributorID")
	private int distributorID;
	
	@JsonProperty(value = "DistributorName")
	private String distributorName;
	
	@JsonProperty(value = "DistributorShortName")
	private String distributorShortName;
	
	@JsonProperty(value = "PostalCode")
	private String postalCode;
	
	@JsonProperty(value = "City")
	private String city;
	
	@JsonProperty(value = "Address")
	private String address;
	
	@JsonProperty(value = "Email")
	private String email;
	
	@JsonProperty(value = "WWW")
	private String www;
	
	@JsonProperty(value = "Tel")
	private String tel;
	
	@JsonProperty(value = "Fax")
	private String fax;
	
}
