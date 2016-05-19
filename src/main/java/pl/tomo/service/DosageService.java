package pl.tomo.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Backup;
import pl.tomo.entity.Dosage;
import pl.tomo.repository.BackupRepository;
import pl.tomo.repository.DosageRepository;

@Service
public class DosageService {
	
	private Logger logger = Logger.getLogger(DosageService.class);
	
	@Autowired
	private DosageRepository dosageRepository;

	public void save(Dosage dosage) {
		dosageRepository.save(dosage);
		logger.info("Save dosage to database: " + dosage);
	}
	
	
}
