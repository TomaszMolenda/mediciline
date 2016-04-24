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

@Entity
public class ATC {
	
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
	public int getProductLineID() {
		return productLineID;
	}
	public void setProductLineID(int productLineID) {
		this.productLineID = productLineID;
	}
	public String getAtcCode() {
		return atcCode;
	}
	public void setAtcCode(String atcCode) {
		this.atcCode = atcCode;
	}
	public String getAtcName() {
		return atcName;
	}
	public void setAtcName(String atcName) {
		this.atcName = atcName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<Medicament> getMedicaments() {
		return medicaments;
	}
	@Override
	public String toString() {
		return "ATC [productLineID=" + productLineID + ", atcCode=" + atcCode + ", atcName=" + atcName + "]";
	}
	
	
	
	
}
