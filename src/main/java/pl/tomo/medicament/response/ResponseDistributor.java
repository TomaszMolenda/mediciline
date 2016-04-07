package pl.tomo.medicament.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import pl.tomo.medicament.entity.Distributor;

public class ResponseDistributor extends ResponseJson{

	@JsonProperty(value = "Result")
	private Distributor result;

	public Distributor getResult() {
		return result;
	}

	public void setResult(Distributor result) {
		this.result = result;
	}


 
	
	
	
	
	
}
