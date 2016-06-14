package pl.tomo.entity.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;

@Getter
@Setter
public class MedicamentForm {
	
	private List<Medicament> medicaments;
	
	private List<Integer> ids;
	
	private int diseaseId;
	
	private User user;
}
