package pl.tomo.medicament.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.medicament.entity.Disease;

@Getter
@Setter
public class ResponseDisease extends ResponseJson {

	private List<Disease> rows;

	
	
	
	
	
}
