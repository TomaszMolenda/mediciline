package pl.tomo.provider.wrapper;

import java.util.List;
import java.util.SortedSet;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.entity.Disease;
import pl.tomo.entity.File;
import pl.tomo.entity.Medicament;

@Getter
@Setter
public class DiseaseInfo {
	
	private SortedSet<File> files;
	private Disease disease;
	private List<Medicament> medicamentsToAdd;
	private List<Medicament> medicamentsToDelete;
	private String urlFileUpload;
	private FileBucket fileBucket;

	public DiseaseInfo(Disease disease, MedicamentsInDisease medicamentsInDisease) {
		this.files = disease.getFiles();
		this.disease = disease;
		this.medicamentsToAdd = medicamentsInDisease.getMedicamentsToAdd();
		this.medicamentsToDelete = medicamentsInDisease.getMedicamentsToDelete();
		this.urlFileUpload = "/diseases/" + disease.getId() + "/upload";
		this.fileBucket = new FileBucket();
	}
	
	

}
