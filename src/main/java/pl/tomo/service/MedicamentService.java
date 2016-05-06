package pl.tomo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.controller.MedicamentController;
import pl.tomo.entity.DateExpirationYearMonth;
import pl.tomo.entity.Disease;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.repository.MedicamentRepository;

@Service
public class MedicamentService {
	
	private Logger logger = Logger.getLogger(MedicamentService.class);

	@Autowired
	private MedicamentRepository medicamentRepository;

	@Autowired
	private UserService userService;

	public void save(Medicament medicament, String name) {
		try {
			String date = medicament.getDateExpirationYearMonth().getYear() + "-"
					+ medicament.getDateExpirationYearMonth().getMonthId() + "-01";
			medicament.setDateExpiration(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			logger.info("user: " + name + "try parse date - no success");
		}
		User user = userService.findByName(name);
		medicament.setUser(user);
		medicamentRepository.save(medicament);
		logger.info("save medicament, id: " + medicament.getId());
	}

	public void delete(int id) {
		medicamentRepository.delete(id);
		logger.info("delete medicament, id: " + id);
	}

	public List<Medicament> findByUser(String name) {
		List<Medicament> medicaments = medicamentRepository.findByUser(name);
		for (Medicament medicament : medicaments) {
			Date dateExpiration = medicament.getDateExpiration();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateExpiration);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			DateExpirationYearMonth dateExpirationYearMonth = new DateExpirationYearMonth(year, month);
			medicament.setDateExpirationYearMonth(dateExpirationYearMonth);
		}
		logger.info("get list medicaments, by user: " + name);
		return medicaments;
	}

	public Medicament findById(int id) {
		logger.info("get medicament,: " + id);
		return medicamentRepository.findById(id);
	}

	public Medicament findByIdWithUser(int id) {
		logger.info("get medicament,: " + id);
		return medicamentRepository.findByIdWithUser(id);
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


}
