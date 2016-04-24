package pl.tomo.medicament.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.Prescription;
import pl.tomo.medicament.repository.PrescriptionRepository;

@Service
public class PrescriptionService {
	
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	public void save(Prescription prescription) {
		prescriptionRepository.save(prescription);
	}
	
	public void save(List<Prescription> prescriptions) {
		for (Prescription prescription : prescriptions) {
			prescriptionRepository.save(prescription);
		}
	}

	public List<Integer> getAllId() {
		return prescriptionRepository.getAllId();
	}

	public Prescription getActiveById(Integer integer) {
		return prescriptionRepository.getActiveById(integer);
	}

	public Prescription getPrescription(int prescriptionID) {
		return prescriptionRepository.getOne(prescriptionID);
	}

}
