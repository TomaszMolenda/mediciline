package pl.tomo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.DiseaseMedicament;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.repository.DiseaseMedicamentRepositoryEntityGraph;
import pl.tomo.repository.MedicamentRepository;
import pl.tomo.repository.MedicamentRepositoryEntityGraph;

@Service
public class MedicamentService {
	
	private static final String ALL = "all";
	private static final String ACTIVE = "active";
	private static final String ARCHIVE = "archive";
	private static final String OVERDUE = "overdue";

	@Autowired
	private MedicamentRepository medicamentRepository;
	
	@Autowired
	private MedicamentRepositoryEntityGraph medicamentRepositoryEntityGraph;

	@Autowired
	private UserService userService;
	
	@Autowired
	private DiseaseMedicamentRepositoryEntityGraph diseaseMedicamentRepositoryEntityGraph;

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
	
	
	public List<Medicament> findAll(HttpServletRequest request, String list) {
		User user = userService.findByRequestOnlyUser(request);
		List<Medicament> medicaments= null;
		switch (list) {
		case ALL:
			medicaments = medicamentRepositoryEntityGraph.findAll(user);
			break;
		case ACTIVE:
			medicaments = medicamentRepositoryEntityGraph.findByArchiveAndUser(false, user);
			break;
		case ARCHIVE:
			medicaments = medicamentRepositoryEntityGraph.findByArchiveAndUser(true, user);
			break;
		case OVERDUE:
			medicaments = medicamentRepositoryEntityGraph.findByArchiveAndOverdueAndUser(false, true, user);
			break;
		default:
			throw new AccessDeniedException();
		}
		return medicaments;
	}

	public Medicament findById(int id) {
		return medicamentRepositoryEntityGraph.findById(id);
	}
	
	public Medicament findByIdOnlyMedicament(int id) {
		return medicamentRepositoryEntityGraph.findByIdOnlyMedicament(id);
	}
	
	public Medicament findWithUser(int id) {
		return medicamentRepositoryEntityGraph.findByIdWithUser(id);
	}

	private void archive(Medicament medicament) {
		medicament.setArchive(true);
		medicamentRepository.save(medicament);
	}


	public void archive(int id, HttpServletRequest request) {
		User user = userService.findByRequestOnlyUser(request);
		Medicament medicament = findById(id);
		if(medicament.getUser().equals(user)) {
			archive(medicament);
		} else {
			throw new AccessDeniedException(request);
		}
		
	}

	public List<Disease> findDiseases(Medicament medicament, HttpServletRequest request) {
		User user = userService.findByRequestOnlyUser(request);
		if(!medicament.getUser().equals(user)) 
			throw new AccessDeniedException();
		List<DiseaseMedicament> diseaseMedicaments = diseaseMedicamentRepositoryEntityGraph.findWithDisease(medicament);
		List<Disease> diseases = new ArrayList<>();
		for (DiseaseMedicament diseaseMedicament : diseaseMedicaments) {
			diseases.add(diseaseMedicament.getDisease());
		}
		return diseases;
	}

	public List<Medicament> findAllActiveOnlyMedicaments(HttpServletRequest request, Disease disease) {
		List<Medicament> medicamentsInDisease = diseaseMedicamentRepositoryEntityGraph.findWithDisease(disease);
		User user = userService.findWithMedicaments(request);
		List<Medicament> medicaments = new ArrayList<>();
		for (Medicament medicament : user.getMedicaments()) {
			if(!medicamentsInDisease.contains(medicament) & !medicament.isArchive())
				medicaments.add(medicament);
		}
		return medicaments;
	}

	public void setMedicamentsOverdue() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH);
		
		List<Medicament> medicaments = medicamentRepository.findAll();
		for (Medicament medicament : medicaments) {
			calendar.setTime(medicament.getDateExpiration());
			int medicamentYear = calendar.get(Calendar.YEAR);
			int medicamentMonth = calendar.get(Calendar.MONTH);
			if(medicamentYear < nowYear)
				medicament.setOverdue(true);
			if(medicamentYear == nowYear & medicamentMonth < nowMonth)
				medicament.setOverdue(true);
		}
		medicamentRepository.save(medicaments);
	}

	
}
