package pl.tomo.medicament.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.tomo.medicament.entity.Disease;

@Getter
@Setter
@ToString
public class ResponseDisease extends ResponseJson {

	private List<Disease> rows;

	
	
	
	
	
}
