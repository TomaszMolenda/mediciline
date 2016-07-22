package pl.tomo.provider;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Dosage;
import pl.tomo.service.DosageService;

@Service
public class DosageReminder {
	
	private static final int TIME = 1000*60*5;
	
	@Autowired
	DosageService dosageService;
	
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
			}
			//if()
			
//			System.out.println(nowHour);
//			System.out.println(nowMinute);
//			System.out.println(takeHour);
//			System.out.println(takeMinute);
		}
	}
	
	public void reset() {
		System.out.println("reset");
	}

}
