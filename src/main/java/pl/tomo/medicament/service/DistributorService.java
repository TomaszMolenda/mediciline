package pl.tomo.medicament.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.Distributor;
import pl.tomo.medicament.repository.DistributorRepository;

@Service
public class DistributorService {
	
	@Autowired
	private DistributorRepository distributorRepository;
	
	public void save(Distributor distributor) {
		distributorRepository.save(distributor);
	}

	public void save(List<Distributor> distributors) {
		for (Distributor distributor : distributors) {
			distributorRepository.save(distributor);
		}
		
	}

	public List<Integer> getAllId() {
		return distributorRepository.getAllId();
	}

	public Distributor getDistributor(int distributorID) {
		return distributorRepository.findOne(distributorID);
	}

}
