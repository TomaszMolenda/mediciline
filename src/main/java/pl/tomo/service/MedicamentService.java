package pl.tomo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Disease;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.repository.MedicamentRepository;
import pl.tomo.repository.MedicamentRepositoryEntityGraph;

@Service
public class MedicamentService {
	
	private Logger logger = Logger.getLogger(MedicamentService.class);

	@Autowired
	private MedicamentRepository medicamentRepository;
	
	@Autowired
	private MedicamentRepositoryEntityGraph medicamentRepositoryEntityGraph;

	@Autowired
	private UserService userService;
	
	@Autowired
	private JdbcTemplate jdbcTemplateMySQL;

	public Medicament save(Medicament medicament, String name) {
		long l = medicament.getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(l);
		medicament.setDateExpiration(calendar.getTime());

		String kind = medicament.getKind();
		Dosage dosage = new Dosage(kind);
		medicament.setQuantity(dosage.getWholePackage());
		medicament.setUnit(dosage.getUnit());
		
		User user = userService.findByName(name);
		medicament.setUser(user);
		Medicament savedMedicament = medicamentRepository.save(medicament);
		logger.info("save medicament, id: " + savedMedicament.getId());
		return savedMedicament;
	}

	public void delete(Medicament medicament) {
		int id = medicament.getId();
		medicamentRepository.delete(id);
		String sqlIdMD = "select id from Disease_Medicament where medicaments_id=?";
		
		try {
			int idMD = jdbcTemplateMySQL.queryForObject(sqlIdMD, Integer.class, medicament.getId()).intValue();
			String sqlDelete = "delete from Dosage where idMD=" + idMD;
			logger.info("user " + medicament.getUser().getName() + " removed dosages, id: " + idMD);
			int update = jdbcTemplateMySQL.update(sqlDelete);
		} catch (EmptyResultDataAccessException e) {
			
		}
		
		logger.info("delete medicament, id: " + id);
	}

	public List<Medicament> findByUser(String name) {
		List<Medicament> medicaments = medicamentRepository.findByUser(name);
		for (Medicament medicament : medicaments) {
			medicament.setIdServer(medicament.getId());
			Date dateExpiration = medicament.getDateExpiration();
			long time = dateExpiration.getTime();
			medicament.setDate(time);
		}
		logger.info("get list medicaments, by user: " + name);
		return medicaments;
	}

	public Medicament findById(int id) {
		logger.info("get medicament,: " + id);
		return medicamentRepositoryEntityGraph.getById("select m from Medicament m where m.id="+id, "user", "disease");
	}

	public Medicament findByIdWithUser(int id) {
		Medicament medicament = medicamentRepository.findByIdWithUser(id);
		logger.info("get medicament,: " + id);
		Date dateExpiration = medicament.getDateExpiration();
		long time = dateExpiration.getTime();
		medicament.setDate(time);
		return medicament;
	}

	public List<Medicament> findByDisease(Disease disease) {
		logger.info("get list medicaments, by disease: " + disease.getId());
		return medicamentRepository.findByDisease(disease);
	}

	public List<Medicament> findByDisease(int id) {
		logger.info("get list medicaments, by disease: " + id);
		return medicamentRepository.findByDisease(id);
	}

	public List<Medicament> findWithUserByDisease(int id) {
		logger.info("get list medicaments, by disease: " + id);
		return medicamentRepository.findWithUserByDisease(id);
	}

	public List<Medicament> save(List<Medicament> medicaments, String name) {
		List<Medicament> returnList = new ArrayList<Medicament>();
		for (Medicament medicament : medicaments) {
			returnList.add(save(medicament, name));
		}
		return returnList;
	}


}
