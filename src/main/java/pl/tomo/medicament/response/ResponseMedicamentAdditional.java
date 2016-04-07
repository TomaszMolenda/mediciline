package pl.tomo.medicament.response;

import com.fasterxml.jackson.annotation.JsonProperty;

class Information {
	
	@JsonProperty(value = "ProductLineID")
	private int productLineID;
	@JsonProperty(value = "Field")
	private String field;
	@JsonProperty(value = "Value")
	private String value;
	public int getProductLineID() {
		return productLineID;
	}
	public void setProductLineID(int productLineID) {
		this.productLineID = productLineID;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Information [productLineID=" + productLineID + ", field=" + field + ", value=" + value + "]";
	}

}

public class ResponseMedicamentAdditional extends ResponseJson{


	@JsonProperty(value = "Result")
	private Information information;
	
	public String getField() {
		return information.getField();
	}
	
	public String getValue() {
		return information.getValue();
	}
		
	public String getInformationToString() {
		return information.toString();
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	@Override
	public String toString() {
		return "ResponseMedicamentAdditional [information=" + information + "]";
	}




 
	
	
	
	
	
}
