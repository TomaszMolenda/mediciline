package pl.tomo.medicament.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseJson {
	
	private int page;
	
	private int total;
	
	private int records;
	
	@JsonProperty(value = "Msg")
	private String msg;
	
	@JsonProperty(value = "Err")
	private String err;
	
}
