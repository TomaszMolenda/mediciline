package pl.tomo.upload;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileBucket {
	
	MultipartFile file;

}
