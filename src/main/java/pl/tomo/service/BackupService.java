package pl.tomo.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Backup;
import pl.tomo.repository.BackupRepository;

@Service
public class BackupService {
	
	private Logger logger = Logger.getLogger(BackupService.class);
	
	@Autowired
	private BackupRepository backupRepository;

	public void save(Backup backup) {
		backupRepository.save(backup);
		logger.info("Save backup to database: " + backup);
	}

	public Set<Backup> findAll() {
		Set<Backup> backups = new HashSet<Backup>(backupRepository.findAll());
		logger.info("Get all backups from database: " + backups);
		return backups;
	}

	public Backup getById(int id) {
		Backup backup = backupRepository.findOne(id);
		logger.info("Get backup from database: " + backup.toString());
		return backup;
	}
	
	
}
