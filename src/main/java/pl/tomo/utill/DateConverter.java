package pl.tomo.utill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateConverter {
	
	private static final List<String> months = new ArrayList<String>();
	
	static {
		months.add("styczeń");
		months.add("luty");
		months.add("marzec");
		months.add("kwiecień");
		months.add("maj");
		months.add("czerwiec");
		months.add("lipiec");
		months.add("sierpień");
		months.add("wrzesień");
		months.add("październik");
		months.add("listopad");
		months.add("grudzień");
		
	}
	
	public static String longToMonthYear(long l) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(l);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		return months.get(month) + " " + year;
	}

}
