package pl.tomo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
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
	private ServletContext servletContext;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiseaseService diseaseService;
	
	private Logger logger = Logger.getLogger(FileService.class);
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private FileRepositoryEntityGraph fileRepositoryEntityGraph;

	public void save(FileBucket fileBucket, HttpServletRequest request, int id) throws IOException {
		MultipartFile multipartFile = fileBucket.getFile();
		Disease disease = diseaseService.findById(id);
		File file = new pl.tomo.entity.File();
		file.setDisease(disease);
		save(file, multipartFile, request);
	}
	
	public void save(MultipartFile multipartFile, HttpServletRequest request) throws IOException {
		File file = new pl.tomo.entity.File();
		file.setProfile(true);
		save(file, multipartFile, request);

		
	}

	private void save(pl.tomo.entity.File file, MultipartFile multipartFile, HttpServletRequest request) throws IOException {
		User user = userService.findByRequest(request);
		file.setUser(user);
		file.setName(multipartFile.getOriginalFilename());
		Calendar calendar = Calendar.getInstance();
		file.setUploadDate(calendar.getTime());
		java.io.File uploadedFile = new java.io.File(servletContext.getRealPath("/uploadfiles/") + "/" + calendar.getTimeInMillis() + "-" + file.getName());
		FileCopyUtils.copy(multipartFile.getBytes(), uploadedFile);
		file.setPath(uploadedFile.getAbsolutePath());
		fileRepository.save(file);
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
