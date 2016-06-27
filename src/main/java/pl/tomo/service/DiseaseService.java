package pl.tomo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.Patient;
import pl.tomo.entity.User;
import pl.tomo.entity.form.MedicamentForm;
import pl.tomo.repository.DiseaseRepository;
import pl.tomo.repository.DiseaseRepositoryEntityGraph;

@Service
public class DiseaseService {
	
	private Logger logger = Logger.getLogger(BackupService.class);
	
	@Autowired
	private DiseaseRepository diseaseRepository;
	
	@Autowired
	private DiseaseRepositoryEntityGraph diseaseRepositoryEntityGraph;
	
	@Autowired
	private MedicamentService medicamentService; 
	
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

	public Disease findById(int diseaseId) {
		Disease disease = diseaseRepositoryEntityGraph.getById("select d from Disease d where d.id="+diseaseId, "user", "medicaments");
		logger.info("get disease, id: " + diseaseId);
		return disease;
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


	
	


}
