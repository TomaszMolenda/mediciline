package pl.tomo.medicament.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.Distributor;
import pl.tomo.medicament.repository.DistributorRepository;

@Service
public class DistributorService {
	
	private Logger logger = Logger.getLogger(DistributorService.class);
	
	@Autowired
	private DistributorRepository distributorRepository;
	
	public void save(Distributor distributor) {
		distributorRepository.save(distributor);
		logger.info("save distributor, id " + distributor.getDistributorID());
	}

	public void save(List<Distributor> distributors) {
		for (Distributor distributor : distributors) {
			distributorRepository.save(distributor);
			logger.info("save distributor, id " + distributor.getDistributorID());
		}
		
	}

	public List<Integer> getAllId() {
		logger.info("get list distributors");
		return distributorRepository.getAllId();
	}

	public Distributor getDistributor(int distributorID) {
		logger.info("get distributor, id " + distributorID);
		return distributorRepository.findOne(distributorID);
	}

}
