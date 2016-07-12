package pl.tomo.medicament.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
	
}
