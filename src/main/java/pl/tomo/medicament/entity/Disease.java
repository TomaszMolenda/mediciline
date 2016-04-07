package pl.tomo.medicament.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
public class Disease {
	
	@Id
	@JsonProperty(value = "DiseaseID")
	private int diseaseID;
	@JsonProperty(value = "DiseaseName")
	@Column(columnDefinition="TEXT")
	private String diseaseName;
	@JsonProperty(value = "DiseaseNameShort")
	private String diseaseNameShort;
	@Type(type="true_false")
	private boolean active;
	
	public Disease() {
		active = true;
	}
	public int getDiseaseID() {
		return diseaseID;
	}
	public void setDiseaseID(int diseaseID) {
		this.diseaseID = diseaseID;
	}
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public String getDiseaseNameShort() {
		return diseaseNameShort;
	}
	public void setDiseaseNameShort(String diseaseNameShort) {
		this.diseaseNameShort = diseaseNameShort;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "Disease [diseaseID=" + diseaseID + ", diseaseName=" + diseaseName + ", diseaseNameShort="
				+ diseaseNameShort + ", active=" + active + "]";
	}
	
}
