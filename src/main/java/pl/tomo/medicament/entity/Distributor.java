package pl.tomo.medicament.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Distributor {
	
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
	public int getDistributorID() {
		return distributorID;
	}
	public void setDistributorID(int distributorID) {
		this.distributorID = distributorID;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getDistributorShortName() {
		return distributorShortName;
	}
	public void setDistributorShortName(String distributorShortName) {
		this.distributorShortName = distributorShortName;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWww() {
		return www;
	}
	public void setWww(String www) {
		this.www = www;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public Set<Medicament> getMedicaments() {
		return medicaments;
	}
	@Override
	public String toString() {
		return "Distributor [distributorID=" + distributorID + ", distributorName=" + distributorName
				+ ", distributorShortName=" + distributorShortName + ", postalCode=" + postalCode + ", city=" + city
				+ ", address=" + address + ", email=" + email + ", www=" + www + ", tel=" + tel + ", fax=" + fax + "]";
	}
	
	
}
