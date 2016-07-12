package pl.tomo.entity.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;

@Getter
@Setter
@ToString
public class MedicamentForm {

	public MedicamentForm() {}
	
	public MedicamentForm(int id) {
		this.diseaseId = id;
	}

	private List<Medicament> medicaments;
	
	private List<Integer> ids;
	
	private int diseaseId;
	
	private User user;
}
