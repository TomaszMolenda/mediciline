package pl.tomo.entity;

public class DateExpirationYearMonth {
	
	private int year;
	
	private String month;
	
	private String monthAndYear;
	
	private int monthId;

	public DateExpirationYearMonth() {
	}
	
	public DateExpirationYearMonth(int year, int month)
	{
		super();
		this.year = year;
		this.month = theMonth(month);
		this.monthAndYear = theMonth(month) + " " + year;
		this.monthId = month; 
	}
	
	public static String theMonth(int month){
	    String[] monthNames = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};
	    return monthNames[month];
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getMonthId() {
		return monthId;
	}

	public void setMonthId(int monthId) {
		this.monthId = monthId;
	}

	public String getMonthAndYear() {
		return monthAndYear;
	}

	public void setMonthAndYear(String monthAndYear) {
		this.monthAndYear = monthAndYear;
	}

	
	
	
	

}
