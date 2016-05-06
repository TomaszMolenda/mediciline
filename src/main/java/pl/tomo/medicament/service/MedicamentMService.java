package pl.tomo.medicament.service;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.Medicament;
import pl.tomo.medicament.repository.MedicamentMRepository;
import pl.tomo.medicament.repository.MedicamentMRepositoryEntityGraph;
import pl.tomo.service.PatientService;

@Service
public class MedicamentMService {
	
	private Logger logger = Logger.getLogger(MedicamentMService.class);
	
	@Autowired
	private MedicamentMRepository medicamentMRepository;
	
	@Autowired
	private MedicamentMRepositoryEntityGraph medicamentMRepositoryEntityGraph;
	
	public void save(Medicament medicament) {
		medicamentMRepository.save(medicament);
		logger.info("save medicament, id: " + medicament.getPackageID());
	}

	public List<Medicament> getAllMedicaments() {
		logger.info("get list medicaments");
		return medicamentMRepository.findAll();
	}

	public Set<Integer> getDistributorsID() {
		logger.info("get set DistributorsID");
		return medicamentMRepository.getDistributorsID();
	}

	public Set<Integer> getProductLinesID() {
		logger.info("get set ProductLinesID");
		return medicamentMRepository.getProductLinesID();
	}
	
	public List<Integer> getActivePackageID() {
		logger.info("get list ActivePackageID");
		return medicamentMRepository.getActivePackageID();
	}

	public Medicament getMedicamentByPackageID(Integer packageID) {
		Medicament medicament = medicamentMRepositoryEntityGraph.getMedicamentByPackageID(packageID);
		logger.info("get medicament, id: " + medicament.getPackageID());
		return medicament;
	}

	public Medicament getMedicamentByPackageIDAndActive(Integer packageID, boolean active) {
		logger.info("get active medicament, id: " + packageID);
		return medicamentMRepository.getMedicamentByPackageID(packageID, active);
	}

	public List<Medicament> getMedicamentBySearch(String search) {
		search = ("%" + search + "%").toLowerCase();
		logger.info("get list medicaments, search: " + search);
		return medicamentMRepository.getMedicamentBySearch(search);
	}

}
