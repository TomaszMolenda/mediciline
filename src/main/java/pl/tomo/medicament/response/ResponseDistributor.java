package pl.tomo.medicament.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.tomo.medicament.entity.Distributor;

@Getter
@Setter
@ToString
public class ResponseDistributor extends ResponseJson{

	@JsonProperty(value = "Result")
	private Distributor result;
	
}
