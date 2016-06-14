package pl.tomo.entity.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.entity.Patient;

@Getter
@Setter
public class PatientForm {
	
	private Patient patient;
	
	private List<Patient> patients;
	
	private int id;
}
