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

	public List<Medicament> findAll() {
		return medicamentRepository.findAll();
	}

	public void save(Medicament medicament, String name) {
		try {
			String date = medicament.getDateExpirationYearMonth().getYear() + "-"
					+ medicament.getDateExpirationYearMonth().getMonthId() + "-01";
			medicament.setDateExpiration(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		User user = userService.findByName(name);
		medicament.setUser(user);
		medicamentRepository.save(medicament);

	}
	public void save(Medicament medicament) {
		medicamentRepository.save(medicament);
	}

	public void delete(int id) {
		medicamentRepository.delete(id);

	}

	public List<Medicament> findByUser(User user) {

		return medicamentRepository.findByUser(user);
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
		return medicaments;
	}

	public Medicament findById(int id) {
		return medicamentRepository.findById(id);
	}

	public Medicament findByIdWithUser(int id) {
		return medicamentRepository.findByIdWithUser(id);
	}

	public void update(Medicament medicament) {
		System.out.println(medicament.getName() + ", " + medicament.getDateExpiration());

	}

	public List<Medicament> findByDisease(Disease disease) {

		return medicamentRepository.findByDisease(disease);
	}

	public List<Medicament> findByDisease(int id) {

		return medicamentRepository.findByDisease(id);
	}

	public List<Medicament> findWithUserByDisease(int id) {

		return medicamentRepository.findWithUserByDisease(id);
	}

}
