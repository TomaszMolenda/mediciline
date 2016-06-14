package pl.tomo.medicament.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.medicament.entity.DiseaseMini;

@Getter
@Setter
public class ResponseDieaseMini extends ResponseJson {

	private List<DiseaseMini> rows;



	
	
	
	
}
