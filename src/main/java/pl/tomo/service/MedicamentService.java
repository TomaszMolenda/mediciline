package pl.tomo.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.repository.MedicamentRepository;
import pl.tomo.repository.MedicamentRepositoryEntityGraph;

@Service
public class MedicamentService {

	@Autowired
	private MedicamentRepository medicamentRepository;
	
	@Autowired
	private MedicamentRepositoryEntityGraph medicamentRepositoryEntityGraph;

	@Autowired
	private UserService userService;

	public List<Medicament> save(List<Medicament> medicaments, HttpServletRequest request) {
		List<Medicament> returnList = new ArrayList<Medicament>();
		for (Medicament medicament : medicaments) {
			Medicament savedMedicament = save(medicament, request);
			returnList.add(savedMedicament);
		}
		return returnList;
	}
	
	public Medicament save(Medicament medicament, HttpServletRequest request) {
		User user = userService.findByRequestOnlyUser(request);
		medicament.setUser(user);
		return save(medicament);
	}
	
	private Medicament save(Medicament medicament) {
		long date = medicament.setDate();
		medicament.prepareDosage();
		int clientId = medicament.getId();
		medicament.setId(0);
		Medicament savedMedicament = medicamentRepository.save(medicament);
		int idServer = savedMedicament.getId();
		savedMedicament.setIdServer(idServer);
		savedMedicament.setId(clientId);
		savedMedicament.setDate(date);
		return savedMedicament;
	}
	
	public List<Medicament> findAll(HttpServletRequest request) {
		User user = userService.findByRequest(request);
		List<Medicament> medicaments = medicamentRepositoryEntityGraph.findByUser(user);
		return medicaments;
	}
	
	
	public List<Medicament> findAllActive(User user) {
		List<Medicament> medicaments = medicamentRepositoryEntityGraph.findByArchiveAndUser(false, user);
		return medicaments;
	}
	
	public List<Medicament> findAllActive(HttpServletRequest request) {
		User user = userService.findByRequest(request);
		return findAllActive(user);
	}

	public Medicament findById(int id) {
		return medicamentRepositoryEntityGraph.findById(id);
	}
	
	public Medicament findByIdOnlyMedicament(int id) {
		return medicamentRepositoryEntityGraph.findByIdOnlyMedicament(id);
	}
	
	public Medicament findByIdWithUser(int id) {
		return medicamentRepositoryEntityGraph.findByIdWithUser(id);
	}

	private void archive(Medicament medicament) {
		medicament.setArchive(true);
		medicamentRepository.save(medicament);
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
