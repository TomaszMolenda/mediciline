package pl.tomo.medicament.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiseaseMini {
	
	private int packageID;

	@JsonProperty(value = "Disease")
	private String disease;
	
	@JsonProperty(value = "DiseaseTypeID")
	private int diseaseTypeID;
	
	@JsonProperty(value = "Payment")
	private String payment;
	
	@JsonProperty(value = "Price")
	private double price;
	
	private int dti;
	
	public String getDisease() {
		return disease;
	}
	public void setDisease(String disease) {
		this.disease = disease;
	}
	public int getDiseaseTypeID() {
		return diseaseTypeID;
	}
	public void setDiseaseTypeID(int diseaseTypeID) {
		this.diseaseTypeID = diseaseTypeID;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getPackageID() {
		return packageID;
	}
	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}
	public int getDti() {
		return dti;
	}
	public void setDti(int dti) {
		this.dti = dti;
	}
	
	

}
