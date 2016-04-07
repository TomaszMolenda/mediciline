package pl.tomo.medicament.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.Medicament;
import pl.tomo.medicament.repository.MedicamentMRepository;

@Service
public class MedicamentMService {
	
	@Autowired
	private MedicamentMRepository medicamentMRepository;
	
	public void save(Medicament medicament) {
		medicamentMRepository.save(medicament);
	}

	public Medicament getMedicamentById(int id) {
		return medicamentMRepository.getMedicamentById(id);
		
	}

	public List<Medicament> getAllMedicaments() {
		
		return medicamentMRepository.findAll();
	}

	public Set<Integer> getDistributorsID() {
		return medicamentMRepository.getDistributorsID();
	}

	public Set<Integer> getProductLinesID() {
		return medicamentMRepository.getProductLinesID();
	}

	public List<Integer> getPackageID() {
		return medicamentMRepository.getPackageID();
	}
	
	public List<Integer> getActivePackageID() {
		return medicamentMRepository.getActivePackageID();
	}

	public Medicament getMedicamentByPackageID(Integer packageID) {
		return medicamentMRepository.getMedicamentByPackageID(packageID);
	}

	public Medicament getMedicamentByPackageIDAndActive(Integer packageID, boolean active) {
		
		return medicamentMRepository.getMedicamentByPackageID(packageID, active);
	}

	public List<Medicament> findAll() {
		return medicamentMRepository.findAll();
	}

}