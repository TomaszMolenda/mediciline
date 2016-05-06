package pl.tomo.provider;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.medicament.api.Processing;



@Service
public class InitDbService {
	
	@Autowired
	private Processing processing;
	
	@PostConstruct
	public void init() throws Exception{

		//processing.process();
	}


}
