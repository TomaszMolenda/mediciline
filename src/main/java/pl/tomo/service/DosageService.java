package pl.tomo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.entity.form.DosageForm;
import pl.tomo.repository.DosageRepository;
import pl.tomo.repository.DosageRepositoryEntityGraph;

@Service
public class DosageService {
	
	@Autowired
	private DosageRepository dosageRepository;
	
	@Autowired
	private DosageRepositoryEntityGraph dosageRepositoryEntityGraph;
	
	@Autowired
	private JdbcTemplate jdbcTemplateMySQL;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiseaseService diseaseService;
	
	@Autowired
	private MedicamentService medicamentService;


	private List<Dosage> getDosages(int idMD) {
		return dosageRepository.getDosages(idMD);
	}

	public void delete(int id, HttpServletRequest request) {
		User user = userService.findByRequestOnlyUser(request);
		Dosage dosage = findByIdWithUser(id);
		if(!dosage.getUser().equals(user)) 
			throw new AccessDeniedException();
		dosageRepository.delete(id);
	}

	public Dosage findById(int id) {
		return dosageRepositoryEntityGraph.getById("select d from Dosage d where d.id="+id, "user");

	}
	
	private Dosage findByIdWithUser(int id) {
		return dosageRepositoryEntityGraph.findByIdWithUser(id);
	}

	public DosageForm getDosages(HttpServletRequest request, int idD, int idM) {
		User user = userService.findByRequestOnlyUser(request);
		Medicament medicament = medicamentService.findByIdWithUser(idM);
		Disease disease = diseaseService.findByIdWithUser(idD);
		if(!medicament.getUser().equals(user) | !disease.getUser().equals(user))
			throw new AccessDeniedException();
		DosageForm dosageForm = new DosageForm();
		String sql = "select id from Disease_Medicament where disease_id=? and medicaments_id=?";
		int idMD = jdbcTemplateMySQL.queryForObject(sql, Integer.class, idD, idM).intValue();
		List<Dosage> dosages = getDosages(idMD);
		dosageForm.setDosages(dosages);
		dosageForm.setMedicament(medicament);
		dosageForm.setDisease(disease);
		return dosageForm;
	}

	public Dosage save(Dosage dosage, HttpServletRequest request) {
		int idM = dosage.getIdM();
		Medicament medicament = medicamentService.findByIdWithUser(idM);
		int idD = dosage.getIdD();
		Disease disease = diseaseService.findByIdWithUser(idD);
		User user = userService.findByRequest(request);
		if(!medicament.getUser().equals(user) | !disease.getUser().equals(user))
			throw new AccessDeniedException();
		String sql = "select id from Disease_Medicament where disease_id=? and medicaments_id=?";
		int idMD = jdbcTemplateMySQL.queryForObject(sql, Integer.class, idD, idM).intValue();
		dosage.setIdMD(idMD);
		dosage.setUser(user);
		dosageRepository.save(dosage);
		return dosage;
	}

	
	
}
