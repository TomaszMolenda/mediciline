package pl.tomo.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.AccessDeniedException;
import pl.tomo.entity.Disease;
import pl.tomo.entity.DiseaseMedicament;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.entity.form.DosageForm;
import pl.tomo.provider.EmailService;
import pl.tomo.repository.DosageRepository;
import pl.tomo.repository.DosageRepositoryEntityGraph;

@Service
public class DosageService {
	
	@Autowired
	private DosageRepository dosageRepository;
	
	@Autowired
	private DosageRepositoryEntityGraph dosageRepositoryEntityGraph;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiseaseService diseaseService;
	
	@Autowired
	private MedicamentService medicamentService;
	
	@Autowired
	private DiseaseMedicamentService diseaseMedicamentService;
	
	@Autowired
	private EmailService emailService;


	public void delete(int id, HttpServletRequest request) {
		User user = userService.findByRequestOnlyUser(request);
		Dosage dosage = findByIdWithUser(id);
		if(!dosage.getUser().equals(user)) 
			throw new AccessDeniedException();
		dosageRepository.delete(id);
	}

	
	private Dosage findByIdWithUser(int id) {
		return dosageRepositoryEntityGraph.findByIdWithUser(id);
	}

	public DosageForm getDosages(HttpServletRequest request, int idD, int idM) {
		User user = userService.findByRequestOnlyUser(request);
		Medicament medicament = medicamentService.findWithUser(idM);
		Disease disease = diseaseService.findWithUser(idD);
		if(!medicament.getUser().equals(user) | !disease.getUser().equals(user))
			throw new AccessDeniedException();
		DosageForm dosageForm = new DosageForm();
		Set<Dosage> dosages = diseaseMedicamentService.find(disease, medicament);
		dosageForm.setDosages(dosages);
		dosageForm.setMedicament(medicament);
		dosageForm.setDisease(disease);
		return dosageForm;
	}

	public Dosage save(Dosage dosage, HttpServletRequest request) {
		int id = dosage.getId();
		dosage.setId(0);
		int idM = dosage.getIdM();
		Medicament medicament = medicamentService.findWithUser(idM);
		int idD = dosage.getIdD();
		Disease disease = diseaseService.findWithUser(idD);
		User user = userService.findByRequest(request);
		if(!medicament.getUser().equals(user) | !disease.getUser().equals(user))
			throw new AccessDeniedException();
		DiseaseMedicament diseaseMedicament = diseaseMedicamentService.finOne(disease, medicament);
		dosage.setDiseaseMedicament(diseaseMedicament);
		dosage.setUser(user);
		dosage.setWholePackage(medicament.getQuantity());
		dosage.setUnit(medicament.getUnit());
		dosage = dosageRepository.save(dosage);
		dosage.setIdServer(dosage.getId());
		dosage.setId(id);
		return dosage;
	}

	public List<Dosage> findAll() {
		return dosageRepository.findAll();
		
	}

	public List<Dosage> findAllNotSended() {
		return dosageRepositoryEntityGraph.findAllNotSended();
	}


	public DiseaseMedicament findDiseaseMedicament(Dosage dosage) {
		return dosageRepositoryEntityGraph.findWithDiseaseMedicament(dosage).getDiseaseMedicament();
	}


	public void save(Dosage dosage) {
		dosageRepository.save(dosage);
		
	}


	public void setStatusToSend() {
		List<Dosage> dosages = dosageRepository.findAll();
		for (Dosage dosage : dosages) {
			dosage.setSended(false);
		}
		dosageRepository.save(dosages);
	}


	public void sendEmail(String email, int diseaseId, HttpServletRequest request) {
		List<Dosage> dosages = diseaseService.findDosages(diseaseId, request);
		Disease disease = diseaseService.findOne(diseaseId);
		String text = "<style type=\"text/css\">.tg  {border-collapse:collapse;border-spacing:0;border-color:#aabcfe;}" + 
		".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#669;background-color:#e8edff;}" + 
		".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#039;background-color:#b9c9fe;}" + 
		".tg .tg-baqh{text-align:center;vertical-align:top}" + 
		".tg .tg-mb3i{background-color:#D2E4FC;text-align:right;vertical-align:top}" + 
		".tg .tg-lqy6{text-align:right;vertical-align:top}" + 
		".tg .tg-6k2t{background-color:#D2E4FC;vertical-align:top}" + 
		".tg .tg-yw4l{vertical-align:top}" + 
		"</style>" + 
		"<table class=\"tg\">" + 
		  "<tr>" + 
		    "<th class=\"tg-baqh\" colspan=\"6\">Choroba: " + disease.getName() + "</th>" + 
		    "</tr>" + 
		  "<tr>" + 
		  	"<td class=\"tg-6k2t\">Lp</td>" + 
		  	"<td class=\"tg-6k2t\">Godzina</td>" + 
		    "<td class=\"tg-6k2t\">Dawka</td>" + 
		    "<td class=\"tg-6k2t\">Jednostka</td>" + 
		    "<td class=\"tg-6k2t\">Lek</td>" + 
		    "<td class=\"tg-6k2t\">Rodzaj</td>" + 
	    "</tr>";
		int i = 1;
		for (Dosage dosage : dosages) {
			  text += "<tr>" + 
			    "<td class=\"tg-6k2t\">" + i + "</td>" + 
			    "<td class=\"tg-6k2t\">" + dosage.getTakeTime() + "</td>" + 
			    "<td class=\"tg-mb3i\">" + dosage.getDose() + "</td>" + 
			    "<td class=\"tg-mb3i\">" + dosage.getUnit() + "</td>" + 
			    "<td class=\"tg-mb3i\">" + dosage.getDiseaseMedicament().getMedicament().getName() + "</td>" + 
			    "<td class=\"tg-mb3i\">" + dosage.getDiseaseMedicament().getMedicament().getKind() + "</td>" + 
			  "</tr>";
			  i++;
		}
		text += "</table>";
		String subject = "Dawkowania [choroba " + disease.getName() + "]";
		emailService.sendEmail(subject, text, email);
	}

	
	
}
