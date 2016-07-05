package pl.tomo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import pl.tomo.entity.Disease;
import pl.tomo.entity.File;
import pl.tomo.entity.User;
import pl.tomo.repository.FileRepository;
import pl.tomo.repository.FileRepositoryEntityGraph;
import pl.tomo.upload.FileBucket;

@Service
public class FileService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiseaseService diseaseService;
	
	private Logger logger = Logger.getLogger(FileService.class);
	
	private static String UPLOAD_LOCATION="C:/mytemp/";
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private FileRepositoryEntityGraph fileRepositoryEntityGraph;

	public void save(FileBucket fileBucket, HttpServletRequest request, int id) throws IOException {
		MultipartFile fileUpload = fileBucket.getFile();
		User user = userService.findByRequest(request);
		Disease disease = diseaseService.findById(id);
		File file = new pl.tomo.entity.File();
		file.setDisease(disease);
		file.setUser(user);
		Date date = new Date();
		file.setUploadDate(date);
		file.setName(fileUpload.getOriginalFilename());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss-SSS");
		String format = simpleDateFormat.format(date);
		java.io.File uploadedFile = new java.io.File( UPLOAD_LOCATION + format + "-" + fileUpload.getOriginalFilename());
		FileCopyUtils.copy(fileBucket.getFile().getBytes(), uploadedFile);
		file.setPath(uploadedFile.getAbsolutePath());
		fileRepository.save(file);
		logger.info("Save file to database: " + file);
	}

	public List<File> findByDisease(Disease disease) {
		List<File> files = fileRepositoryEntityGraph.findByDisease(disease);
		for (File file : files) {
			file.setUrl("http://localhost:8085/diseases/files/" + file.getId());
		}
		return files;
	}

	public File findById(int id) throws FileNotFoundException {
		File file = fileRepository.findOne(id);
		return file;
	}
	
	
}
