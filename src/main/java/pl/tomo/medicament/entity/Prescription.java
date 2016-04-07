package pl.tomo.medicament.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Prescription {
	
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
	
	public int getPrescriptionID() {
		return prescriptionID;
	}
	public void setPrescriptionID(int prescriptionID) {
		this.prescriptionID = prescriptionID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShorName() {
		return shorName;
	}
	public void setShorName(String shorName) {
		this.shorName = shorName;
	}
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Prescription [prescriptionID=" + prescriptionID + ", name=" + name + ", shorName=" + shorName
				+ ", active=" + active + "]";
	}

	
	
	

}
