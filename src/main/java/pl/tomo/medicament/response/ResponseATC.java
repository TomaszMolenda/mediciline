package pl.tomo.medicament.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.medicament.entity.ATC;

@Getter
@Setter
public class ResponseATC extends ResponseJson {

	private List<ATC> rows;
	
}
