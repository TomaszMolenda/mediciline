package pl.tomo.medicament.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@ToString
public class Disease {
	
	@Setter(value = AccessLevel.NONE)
	@ManyToMany(mappedBy = "diseases", fetch = FetchType.LAZY)
	private Set<Medicament> medicaments = new HashSet<Medicament>();
	
	@Id
	@JsonProperty(value = "DiseaseID")
	private int diseaseID;
	
	@JsonProperty(value = "DiseaseName")
	@Column(columnDefinition="TEXT")
	private String diseaseName;
	
	@JsonProperty(value = "DiseaseNameShort")
	private String diseaseNameShort;

	private int dti;
	
	@Override
	public int hashCode() {
		int result = 17;
		int multipler = 31;
	
		//int
		result = multipler * result + dti;
		result = multipler * result + diseaseID;
		//string
		result = multipler * result + (diseaseName == null ? 0 : diseaseName.hashCode());
		result = multipler * result + (diseaseNameShort == null ? 0 : diseaseNameShort.hashCode());

		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof Disease))return false;
	    Disease disease = (Disease)obj;
	    if(this.hashCode() == disease.hashCode()) return true;
	    else return false;
	}
	
}
