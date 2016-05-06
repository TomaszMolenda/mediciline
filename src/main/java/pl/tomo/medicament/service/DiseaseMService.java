package pl.tomo.medicament.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.Disease;
import pl.tomo.medicament.repository.DiseaseMRepository;

@Service
public class DiseaseMService {
	
	private Logger logger = Logger.getLogger(ATCService.class);
	
	@Autowired
	private DiseaseMRepository diseaseMRepository;
	
	public void save(Disease disease) {
		diseaseMRepository.save(disease);
		logger.info("save disease, id: " + disease.getDiseaseID());
	}

	public List<Integer> getAllId() {
		logger.info("get list diseases id");
		return diseaseMRepository.getAllId();
	}

	public List<Disease> getByName(String disease) {
		logger.info("get list diseases, name: " + disease);
		return diseaseMRepository.getByName(disease);
	}

	public int getMaxId() {
		int maxId = diseaseMRepository.getMaxId();
		logger.info("get maxId: " + maxId);
		return maxId;
	}

}
