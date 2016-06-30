package pl.tomo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public void save(Disease disease, String userName, Patient patient) {
		User user = userService.findByName(userName);
		disease.setUser(user);
		disease.setPatient(patient);
		try 
		{
			disease.setStart(new SimpleDateFormat("yyyy-MM-dd").parse(disease.getStartString()));
			disease.setStop(new SimpleDateFormat("yyyy-MM-dd").parse(disease.getStopString()));
		} 
		catch (ParseException e) 
		{
			logger.info("user : " + userName + " try parse date - no success");
			e.printStackTrace();
		}
		diseaseRepository.save(disease);
		logger.info("save disease, id: " + disease.getId());
	}
	
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

	public Disease findById(int diseaseId) {
		Disease disease = diseaseRepositoryEntityGraph.getById("select d from Disease d where d.id="+diseaseId, "user", "medicaments");
		logger.info("get disease, id: " + diseaseId);
		return disease;
	}
	
	public List<Disease> findByIdTest(int diseaseId) {
		List<Disease> diseases = diseaseRepositoryEntityGraph.getByIdTest();
		return diseases;
	}
	

	public void delete(int id) {
		diseaseRepository.delete(id);
		logger.info("delete disease, id: " + id);
	}

	public Disease findByIdWithUser(int id) {
		logger.info("get disease, id: " + id);
		return diseaseRepository.findByIdWithUser(id);
	}

	public List<Disease> findByPatient(Patient patient) {
		logger.info("get list diseases by patient: " + patient.getId());
		return diseaseRepository.findByPatient(patient);
	}
	
	public List<Disease> findAllActive(Patient patient, String list) {
		String query;
		Map<String, Object> parametrs = new HashMap<String, Object>();
		parametrs.put("patient", patient);
		switch (list) {
		case ALL:
			query = "SELECT d FROM Disease d WHERE d.patient = :patient";
			break;
		case ACTIVE:
			query = "SELECT d FROM Disease d WHERE d.archive = :archive AND d.patient = :patient";
			parametrs.put("archive", false);
			break;
		case ARCHIVE:
			query = "SELECT d FROM Disease d WHERE d.archive = :archive AND d.patient = :patient";
			parametrs.put("archive", true);
			break;
		default:
			throw new AccessDeniedException();
		}
		List<Disease> diseases = diseaseRepositoryEntityGraph.getAll(query, parametrs, "user", "medicaments", "patient", "files");
		return diseases;
	}

	public void delete(MedicamentForm medicamentForm) {
		User user = medicamentForm.getUser();
		String userName = "";
		if(user!=null) userName = user.getName();
		int diseaseId = medicamentForm.getDiseaseId();
		for (Integer id : medicamentForm.getIds()) {
			jdbcTemplateMySQL.update("DELETE FROM Disease_Medicament WHERE disease_id=? and medicaments_id=?", new Object[] { diseaseId, id });
			logger.info("user " + userName + "delete medicaments from disease id " + diseaseId + ", medicament id: " + id);
		}
	}

	public List<Disease> findByRequest(HttpServletRequest request) {
		User user = userService.findByRequest(request);
		return new ArrayList<Disease>(user.getDiseases());
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

	

	


	
	


}
