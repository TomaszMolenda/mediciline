package pl.tomo.controller.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.tomo.service.FileService;

@RestController
@RequestMapping(value = "/api")
public class RestFileController {
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public ResponseEntity<?> handleProfilePhoto(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			fileService.save(file, request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
