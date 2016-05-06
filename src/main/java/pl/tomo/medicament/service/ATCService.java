package pl.tomo.medicament.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.ATC;
import pl.tomo.medicament.repository.ATCRepository;

@Service
public class ATCService {
	
	private Logger logger = Logger.getLogger(ATCService.class);
	
	@Autowired
	private ATCRepository atcRepository;
	
	public void save(ATC atc) {
		atcRepository.save(atc);
		logger.info("save atc " + atc.getId());
	}

	public void save(List<ATC> atcs) {
		for (ATC atc : atcs) {
			logger.info("save atc " + atc.getId());
			atcRepository.save(atc);
		}
		
	}

	public List<Integer> getAllProductLinesId() {
		logger.info("get list productlines id from atc");
		return atcRepository.getAllProductLinesId();
	}

	public List<ATC> getATCs(int productLineID) {
		logger.info("get list atc by productlines id: " + productLineID);
		return atcRepository.getATCs(productLineID);
	}
}
