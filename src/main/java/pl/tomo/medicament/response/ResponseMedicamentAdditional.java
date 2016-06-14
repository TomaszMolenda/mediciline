package pl.tomo.medicament.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Information {
	
	@JsonProperty(value = "ProductLineID")
	private int productLineID;
	
	@JsonProperty(value = "Field")
	private String field;
	
	@JsonProperty(value = "Value")
	private String value;
	
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
	
}
