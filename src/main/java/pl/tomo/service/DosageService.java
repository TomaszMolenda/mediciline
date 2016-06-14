package pl.tomo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Dosage;
import pl.tomo.repository.DosageRepository;
import pl.tomo.repository.DosageRepositoryEntityGraph;

@Service
public class DosageService {
	
	private Logger logger = Logger.getLogger(DosageService.class);
	
	@Autowired
	private DosageRepository dosageRepository;
	
	@Autowired
	private DosageRepositoryEntityGraph dosageRepositoryEntityGraph;

	public Dosage save(Dosage dosage) {
		Dosage saveedDosage = dosageRepository.save(dosage);
		logger.info("Save dosage to database: " + saveedDosage);
		return saveedDosage;
	}

	public List<Dosage> getDosages(int idMD) {
		logger.info("Get dosages with idMD: " + idMD);
		return dosageRepository.getDosages(idMD);
	}

	public void delete(int id) {
		dosageRepository.delete(id);
	}

	public Dosage findById(int id) {
		Dosage dosage = dosageRepositoryEntityGraph.getById("select d from Dosage d where d.id="+id, "user");
		logger.info("get dosage, id: " + id);
		return dosage;
	}

	
	
}
