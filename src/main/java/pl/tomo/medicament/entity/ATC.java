package pl.tomo.medicament.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class ATC {

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
	@Override
	public String toString() {
		return "ATC [productLineID=" + productLineID + ", atcCode=" + atcCode + ", atcName=" + atcName + "]";
	}
	
	
	
	
}
