package pl.tomo.entity.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;

@Getter
@Setter
public class DosageForm {
	
	private List<Dosage> dosages;
	private Medicament medicament;
	private Disease disease;
}
