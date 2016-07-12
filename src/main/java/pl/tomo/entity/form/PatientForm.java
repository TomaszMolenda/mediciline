package pl.tomo.entity.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.tomo.entity.Patient;

@Getter
@Setter
@ToString
public class PatientForm {
	
	public PatientForm() {
		
	}
	
	public PatientForm(List<Patient> patients) {
		this.patients = patients;
	}

	private Patient patient;
	
	private List<Patient> patients;
	
	private int id;
}
