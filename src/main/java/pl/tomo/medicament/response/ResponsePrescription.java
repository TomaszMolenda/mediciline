package pl.tomo.medicament.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.medicament.entity.Prescription;

@Getter
@Setter
public class ResponsePrescription extends ResponseJson {

	private List<Prescription> rows;
	
}
