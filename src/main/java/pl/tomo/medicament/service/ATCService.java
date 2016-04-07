package pl.tomo.medicament.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.entity.ATC;
import pl.tomo.medicament.repository.ATCRepository;

@Service
public class ATCService {
	
	@Autowired
	private ATCRepository atcRepository;
	
	public void save(ATC atc) {
		atcRepository.save(atc);
	}

	public void save(List<ATC> atcs) {
		for (ATC atc : atcs) {
			atcRepository.save(atc);
		}
		
	}

	public List<Integer> getAllProductLinesId() {
		return atcRepository.getAllProductLinesId();
	}
}
