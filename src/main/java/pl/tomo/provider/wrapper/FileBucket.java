package pl.tomo.provider.wrapper;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileBucket {
	
	MultipartFile file;

}
