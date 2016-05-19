package pl.tomo.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Backup;
import pl.tomo.entity.File;
import pl.tomo.repository.BackupRepository;
import pl.tomo.repository.FileRepository;

@Service
public class FileService {
	
	private Logger logger = Logger.getLogger(FileService.class);
	
	@Autowired
	private FileRepository fileRepository;

	public void save(File file) {
		fileRepository.save(file);
		logger.info("Save backup to database: " + file);
	}
	
	
}
