package pl.tomo.provider;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.DiseaseMedicament;
import pl.tomo.entity.Dosage;
import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.service.DiseaseMedicamentService;
import pl.tomo.service.DosageService;

@Service
public class DosageReminder {
	
	private static final int TIME = 1000*60*5;
	
	@Autowired
	private DosageService dosageService;
	
	@Autowired
	private EmailService emailService;
	
	public void sendEmail() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
		int nowMinute = calendar.get(Calendar.MINUTE);
		calendar.clear();
		calendar.set(Calendar.HOUR_OF_DAY, nowHour);
		calendar.set(Calendar.MINUTE, nowMinute);
		long nowMillis = calendar.getTimeInMillis();

		
		List<Dosage> dosages = dosageService.findAllNotSended();
		for (Dosage dosage : dosages) {
			calendar.setTime(dosage.getTakeTime());
			long takeMillis = calendar.getTimeInMillis();
			if(nowMillis <= takeMillis & nowMillis >= takeMillis - TIME) {
				System.out.println("czas na lek");
				DiseaseMedicament diseaseMedicament = dosageService.findDiseaseMedicament(dosage);
				HashMap<String, String> email = prepareEmail(dosage, diseaseMedicament);
				emailService.sendEmail(email.get("subject"), email.get("text"), email.get("email"));
				dosage.setSended(true);
				dosageService.save(dosage);
			}

		}
	}
	

	private HashMap<String, String> prepareEmail(Dosage dosage, DiseaseMedicament diseaseMedicament) {
		HashMap<String, String> email = new HashMap<String, String>();
		User user = dosage.getUser();
		Medicament medicament = diseaseMedicament.getMedicament();
		String subject = "[Przypomnienie] - Dawka leku " + medicament.getName() + " - " + 
				dosage.getDose() + " " + dosage.getUnit();
		String text = "Witaj " + user.getName() + "\n";
		text += "Choroba: " + diseaseMedicament.getDisease().getName() + "\n";
		text += "Lek: " + medicament.getName() + "\n";
		text += "Dawka: " + dosage.getDose() + " " + dosage.getUnit();
		email.put("subject", subject);
		email.put("text", text);
		email.put("email", user.getEmail());
		return email;
	}


	public void reset() {
		dosageService.setStatusToSend();
	}

}
