package pl.tomo.medicament.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.tomo.medicament.entity.ATC;

@Getter
@Setter
@ToString
public class ResponseATC extends ResponseJson {

	private List<ATC> rows;
	
}
