package pl.tomo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.jcabi.aspects.Loggable;

import pl.tomo.entity.Dosage;
import pl.tomo.entity.User;
import pl.tomo.entity.form.DosageForm;
import pl.tomo.repository.DosageRepository;
import pl.tomo.repository.DosageRepositoryEntityGraph;

@Service
@Loggable
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

	public void delete(int id) {
		dosageRepository.delete(id);
	}

	public Dosage findById(int id) {
		Dosage dosage = dosageRepositoryEntityGraph.getById("select d from Dosage d where d.id="+id, "user");
		return dosage;
	}

	public DosageForm getDosages(HttpServletRequest request, int idD, int idM) {
		DosageForm dosageForm = new DosageForm();
		String sql = "select id from Disease_Medicament where disease_id=? and medicaments_id=?";
		int idMD = jdbcTemplateMySQL.queryForObject(sql, Integer.class, idD, idM).intValue();
		List<Dosage> dosages = getDosages(idMD);
		dosageForm.setDosages(dosages);
		dosageForm.setMedicament(medicamentService.findById(idM));
		dosageForm.setDisease(diseaseService.findById(idD));
		return dosageForm;
	}

	public Dosage save(Dosage dosage, HttpServletRequest request) {
		String sql = "select id from Disease_Medicament where disease_id=? and medicaments_id=?";
		int idMD = jdbcTemplateMySQL.queryForObject(sql, Integer.class, dosage.getIdD(), dosage.getIdM()).intValue();
		User user = userService.findByRequest(request);
		dosage.setIdMD(idMD);
		dosage.setUser(user);
		dosageRepository.save(dosage);
		return dosage;
	}

	
	
}
