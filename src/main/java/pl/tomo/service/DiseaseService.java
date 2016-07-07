package pl.tomo.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.controller.exception.WrongDataInputException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.entity.form.MedicamentForm;
import pl.tomo.entity.form.PatientForm;
import pl.tomo.repository.DiseaseRepository;
import pl.tomo.repository.DiseaseRepositoryEntityGraph;

@Service
public class DiseaseService {
	
	private static final String ALL = "all";
	private static final String ACTIVE = "active";
	private static final String ARCHIVE = "archive";
	
	private Logger logger = Logger.getLogger(BackupService.class);
	
	@Autowired
	private DiseaseRepository diseaseRepository;
	
	@Autowired
	private DiseaseRepositoryEntityGraph diseaseRepositoryEntityGraph;
	
	@Autowired
	private MedicamentService medicamentService; 
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private JdbcTemplate jdbcTemplateMySQL;
	
	
	@Autowired
	private UserService userService; 

	
	public void save(Disease disease, List<Integer> ids) {
		for (Integer id : ids) {
			Medicament medicament = medicamentService.findById(id);
			disease.getMedicaments().add(medicament);
			logger.info("user " + medicament.getUser().getName() + " add medicaments to disease id " + disease.getId() + ", medicament id: " + medicament.getId());
		}
		diseaseRepository.save(disease);
		logger.info("save disease, id: " + disease.getId());
	}
	
	public void save(Disease disease, Patient patient) {
		if(disease.getName().equals("") || disease.getStartLong() == 0)
			throw new WrongDataInputException();
		User user = patient.getUser();
		disease.setUser(user);
		disease.setPatient(patient);
		diseaseRepository.save(disease);
	}

	public Disease findById(int id) {
		Disease disease = diseaseRepositoryEntityGraph.finById(id);
		Set<Medicament> medicaments = disease.getUser().getMedicaments();
		Set<Medicament> iterMedicaments = new LinkedHashSet<Medicament>(medicaments);

		for (Medicament medicament : iterMedicaments) {
			Set<Disease> setDiseases = medicament.getDisease();
			for (Disease d : setDiseases) {
				if(d.getId() == id) medicaments.remove(medicament);
			}
		}
		return disease;
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
		Disease disease = findById(id);
		System.out.println(disease);
		if(disease.getUser().equals(user) && date >= disease.getStartLong() && !disease.isArchive()) {
			disease.setStopLong(date);
			archive(disease);
		} else {
			throw new AccessDeniedException(request);
		}
	}
	
	private void archive(Disease disease) {
		disease.setArchive(true);
		diseaseRepository.save(disease);
		logger.info("disease id: " + disease.getId() + " archived, by user: " + disease.getUser().getName());
	}

	public void addMedicaments(MedicamentForm medicamentForm) {
		List<Integer> ids = medicamentForm.getIds();
		if(ids == null) throw new AccessDeniedException();
		Disease disease = diseaseRepositoryEntityGraph.finById(medicamentForm.getDiseaseId());
		SortedSet<Medicament> medicaments = disease.getMedicaments();
		List<Integer> medicamentsId = disease.getMedicamentsId();
		
		for (Integer id : ids) {
			if(medicamentsId.contains(id))
				medicaments.remove(medicamentService.findById(id));
			else
				medicaments.add(medicamentService.findById(id));
		}
		diseaseRepository.save(disease);
	}

	

	


	
	


}
