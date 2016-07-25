package pl.tomo.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.controller.exception.WrongDataInputException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.DiseaseMedicament;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.entity.form.MedicamentForm;
import pl.tomo.entity.form.PatientForm;
import pl.tomo.repository.DiseaseMedicamentRepository;
import pl.tomo.repository.DiseaseMedicamentRepositoryEntityGraph;
import pl.tomo.repository.DiseaseRepository;
import pl.tomo.repository.DiseaseRepositoryEntityGraph;
import pl.tomo.repository.MedicamentRepositoryEntityGraph;

@Service
public class DiseaseService {
	
	private static final String ALL = "all";
	private static final String ACTIVE = "active";
	private static final String ARCHIVE = "archive";
	
	@Autowired
	private DiseaseRepository diseaseRepository;
	
	@Autowired
	private DiseaseRepositoryEntityGraph diseaseRepositoryEntityGraph;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DosageService dosagesService;
	
	@Autowired
	private DiseaseMedicamentService diseaseMedicamentService;
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private DiseaseMedicamentRepository diseaseMedicamentRepository;
	
	@Autowired
	private DiseaseMedicamentRepositoryEntityGraph diseaseMedicamentRepositoryEntityGraph; 
	
	@Autowired
	private MedicamentRepositoryEntityGraph medicamentRepositoryEntityGraph;
	
	public void save(Disease disease, Patient patient) {
		if(disease.getName().equals("") || disease.getStartLong() == 0)
			throw new WrongDataInputException();
		User user = patient.getUser();
		disease.setUser(user);
		disease.setPatient(patient);
		diseaseRepository.save(disease);
	}
	
	public Disease findOne(int id) {
		return diseaseRepositoryEntityGraph.findOne(id);
	}
	
	public Disease findWithFilesUser(int id) {
		return diseaseRepositoryEntityGraph.findWithFilesUser(id);
	}
	
	public Disease findWithUser(int id) {
		return diseaseRepositoryEntityGraph.findWithUser(id);
	}
	


	public List<Disease> findAllActive(Patient patient, String list) {
		switch (list) {
		case ALL:
			return diseaseRepositoryEntityGraph.getAll(patient);
		case ACTIVE:
			return diseaseRepositoryEntityGraph.getAll(patient, false);
		case ARCHIVE:
			return diseaseRepositoryEntityGraph.getAll(patient, true);
		default:
			throw new AccessDeniedException();
		}
	}


	public PatientForm getPatientForm(HttpServletRequest request) {
		User user = userService.findByRequest(request);
		List<Patient> patients = patientService.getAllByUser(user.getName());
		return new PatientForm(patients);
	}

	public void archive(int id, long date, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		Disease disease = findWithUser(id);
		if(disease.getUser().equals(user) && date >= disease.getStartLong() && !disease.isArchive()) {
			disease.setStopLong(date);
			archive(disease);
		} else {
			throw new AccessDeniedException(request);
		}
	}
	
	private void archive(Disease disease) {
		disease.setArchive(true);
		System.out.println("tuuu");
		diseaseRepository.save(disease);
	}

	public void addMedicaments(MedicamentForm medicamentForm, HttpServletRequest request) {
		List<Integer> ids = medicamentForm.getIds();
		if(ids == null) throw new AccessDeniedException();
		List<Medicament> medicaments = medicamentRepositoryEntityGraph.findWIthUser(ids);
		User user = userService.findWithMedicaments(request);
		for (Medicament medicament : medicaments) {
			if(!medicament.getUser().equals(user))
				throw new AccessDeniedException();
		}
		int diseaseId = medicamentForm.getDiseaseId();
		Disease disease = diseaseRepositoryEntityGraph.findWithUser(diseaseId);
		if(!disease.getUser().equals(user)) 
			throw new AccessDeniedException();
		
		for (Medicament medicament : medicaments) {
			DiseaseMedicament diseaseMedicament = new DiseaseMedicament();
			diseaseMedicament.setDisease(disease);
			diseaseMedicament.setMedicament(medicament);
			DiseaseMedicament findOne = null;
			try {
				findOne = diseaseMedicamentRepositoryEntityGraph.findOne(medicament, disease);
			} catch (NoResultException e) {
				// TODO: handle exception
			}
			if(findOne == null)
				diseaseMedicamentRepository.save(diseaseMedicament);
		}
	}
	
	public void deleteMedicaments(MedicamentForm medicamentForm, HttpServletRequest request) {
		List<Integer> ids = medicamentForm.getIds();
		List<Medicament> medicaments = medicamentRepositoryEntityGraph.findWIthUser(ids);
		
		int diseaseId = medicamentForm.getDiseaseId();
		Disease disease = diseaseRepositoryEntityGraph.findOne(diseaseId);
		for (Medicament medicament : medicaments) {
			DiseaseMedicament diseaseMedicament = diseaseMedicamentRepositoryEntityGraph.findOne(medicament, disease);
			diseaseMedicamentRepository.delete(diseaseMedicament);
		}
		
	}

	public boolean isRightOwner(Disease disease, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		if(disease.getUser().equals(user)) return true;
		return false;
	}

	public List<Disease> findAll() {
		return diseaseRepositoryEntityGraph.findAll();
	}

	public void delete(Disease disease) {
		diseaseRepository.delete(disease);
		
	}

	public List<Medicament> findMedicamentsInDisease(Disease disease) {
		return diseaseMedicamentRepositoryEntityGraph.findWithDisease(disease);
	}

	public List<Dosage> findDosages(int id, HttpServletRequest request) {
		User user = userService.findByRequestOnlyUser(request);
		Disease disease = diseaseRepositoryEntityGraph.findWithUser(id);
		if(!disease.getUser().equals(user))
			throw new AccessDeniedException();
		List<Dosage> dosages = diseaseMedicamentService.findDosages(disease);
		return dosages;
		
	}

	

}
