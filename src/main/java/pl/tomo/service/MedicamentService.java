package pl.tomo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;
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

	public Medicament save(Medicament medicament, String name) {
		User user = userService.findByName(name);
		medicament.setUser(user);
		return save(medicament);
	}
	
	public List<Medicament> save(List<Medicament> medicaments, String name) {
		List<Medicament> returnList = new ArrayList<Medicament>();
		for (Medicament medicament : medicaments) {
			int id = medicament.getId();
			medicament.setId(0);
			Medicament savedMedicament = save(medicament, name);
			savedMedicament.setId(id);
			returnList.add(savedMedicament);
		}
		return returnList;
	}
	
	public void save(Medicament medicament, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		medicament.setUser(user);
		save(medicament);
	}
	
	private Medicament save(Medicament medicament) {
		medicament.setDate();
		medicament.prepareDosage();
		Medicament savedMedicament = medicamentRepository.save(medicament);
		int id = savedMedicament.getId();
		logger.info("save medicament, id: " + id);
		savedMedicament.setIdServer(id);
		return savedMedicament;
	}
	
	public List<Medicament> findAll(User user) {
		String query = "SELECT m FROM Medicament m WHERE m.user = :user";
		Map<String, Object> parametrs = new HashMap<String, Object>();
		parametrs.put("user", user);
		List<Medicament> medicaments = medicamentRepositoryEntityGraph.getAll(query, parametrs, "user", "disease");
		logger.info("get list medicaments, by user: " + user.getName());
		return medicaments;
	}
	
	public List<Medicament> findAllActive(User user) {
		String query = "SELECT m FROM Medicament m WHERE m.archive = :archive AND m.user = :user";
		Map<String, Object> parametrs = new HashMap<String, Object>();
		parametrs.put("user", user);
		parametrs.put("archive", false);
		List<Medicament> medicaments = medicamentRepositoryEntityGraph.getAll(query, parametrs, "user", "disease");
		logger.info("get list active medicaments, by user: " + user.getName());
		return medicaments;
	}
	
	public List<Medicament> findAllActive(HttpServletRequest request) {
		User user = userService.findByRequest(request);
		logger.info("get list active medicaments, by user: " + user.getName());
		return findAllActive(user);
	}

	public Medicament findById(int id) {
		logger.info("get medicament,: " + id);
		return medicamentRepositoryEntityGraph.getOne("select m from Medicament m where m.id="+id, "user", "disease");
	}


	

	public void archive(Medicament medicament) {
		medicament.setArchive(true);
		medicamentRepository.save(medicament);
		logger.info("medicament id: " + medicament.getId() + " archived, by user: " + medicament.getUser().getName());
	}


	public void archive(int id, HttpServletRequest request) {
		User user = userService.findByRequest(request);
		Medicament medicament = findById(id);
		if(medicament.getUser().equals(user)) {
			archive(medicament);
		} else {
			throw new AccessDeniedException(request);
		}
		
	}

	

	


}
