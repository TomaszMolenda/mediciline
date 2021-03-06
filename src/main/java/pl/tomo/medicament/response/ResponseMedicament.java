package pl.tomo.medicament.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.tomo.medicament.entity.Medicament;

@Getter
@Setter
@ToString
public class ResponseMedicament extends ResponseJson {

	private List<Medicament> rows;

}
