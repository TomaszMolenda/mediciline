package pl.tomo.medicament.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.tomo.medicament.entity.Medicament;
import pl.tomo.medicament.entity.MedicamentAdditional;
import pl.tomo.medicament.response.ResponseJson;
import pl.tomo.medicament.response.ResponseMedicament;
import pl.tomo.medicament.response.ResponseMedicamentAdditional;

class Letters {
	private char[] chars;
	public Letters() {
		String string = "abcdefghijklmnopqrstuvwxyz1234567890";
		chars = string.toCharArray();
	}
	public char[] getChars() {
		return chars;
	}
}



public class Crawler {
	
private Map<String, String> parametrs = new HashMap<String, String>();
	
	private String url;
	
	public Crawler(String url, Map<String, String> parametrs) {
		this.url = url;
		this.parametrs = parametrs;
	}

	public Map<String, String> getParametrs() {
		return parametrs;
	}

	MedicamentAdditional getMedicamentAdditional(int id) throws Exception {
		Map<String, String> information = new HashMap<String, String>();
		information.put("id", Integer.toString(id));
		for (int i = 1; i < 11; i++) {
			String number = "10";
			if(i < 10) number = "0" + Integer.toString(i);
			ResponseMedicamentAdditional responseMedicamentAdditional = (ResponseMedicamentAdditional) getData(ResponseMedicamentAdditional.class, parametrs.get("param1"), parametrs.get("param2") + number, parametrs.get("param3"), Integer.toString(id));
			if(responseMedicamentAdditional.getInformation() != null) 
				information.put(responseMedicamentAdditional.getField(), responseMedicamentAdditional.getValue());
		}
		return new MedicamentAdditional(information);
	}

	Medicament getMedicament(int i) throws Exception{
		Class response = ResponseMedicament.class;
		String responseJsonTxt = getContent(response, parametrs.get("param1"), parametrs.get("param4"), parametrs.get("param5"), Integer.toString(i), parametrs.get("param6"), parametrs.get("param7"));
		ResponseMedicament responseMedicament = (ResponseMedicament) getResponseJson(responseJsonTxt, ResponseMedicament.class);
		return responseMedicament.getRows().get(0);
	}
	
	ResponseJson getData(Class response, String...strings) throws Exception {
		String responseJsonTxt = getContent(response, strings);
		return getResponseJson(responseJsonTxt, response);
	}
	
	List<Medicament> getMedicaments() throws Exception {
		Class response = ResponseMedicament.class;
		Letters letters = new Letters();
		char[] chars = letters.getChars();
		Set<Medicament> medicaments = new HashSet<Medicament>();
		for (char c : chars) {
			String responseJsonTxt = getContent(response, parametrs.get("param1"), parametrs.get("param8"), 
					parametrs.get("param9"), parametrs.get("param10"), parametrs.get("param11"), 
					parametrs.get("param12"), parametrs.get("param13"), parametrs.get("param14"), 
					parametrs.get("param15"), Character.toString(c));
			ResponseMedicament responseMedicament = (ResponseMedicament) getResponseJson(responseJsonTxt, ResponseMedicament.class);
			List<Medicament> responseMedicaments = responseMedicament.getRows();
			if(responseMedicaments != null) medicaments.addAll(responseMedicaments);
			if(responseMedicament.getTotal() > 1) addMoreThan1000Medicaments(medicaments, c, responseMedicament.getTotal(), response);
		}
		
		
		return new ArrayList<Medicament>(medicaments);
	}
	
	void addMoreThan1000Medicaments(Set<Medicament> medicaments, char c, int total, Class response) throws Exception {
		for (int i = 1; i < total; i++) {
			String responseJsonTxt = getContent(response, parametrs.get("param1"), parametrs.get("param8"), 
					parametrs.get("param9"), Integer.toString(i), parametrs.get("param11"), parametrs.get("param12"), 
					parametrs.get("param13"), parametrs.get("param14"), parametrs.get("param15"), Character.toString(c));
			ResponseMedicament responseMedicament = (ResponseMedicament) getResponseJson(responseJsonTxt, ResponseMedicament.class);
			List<Medicament> responseMedicaments = responseMedicament.getRows();
			medicaments.addAll(responseMedicaments);
		}
	}
	
	@SuppressWarnings("unchecked")
	private ResponseJson getResponseJson(String responseJsonTxt, Class responseClass) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Object responseJson = mapper.readValue(responseJsonTxt, responseClass);
		return (ResponseJson) responseJson;
	}
	
	private String getContent(Class response, String...strings) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		for (int i = 0; i < strings.length; i = i + 2 ) data.put(strings[i], strings[i + 1]);
		Response res = null;
		System.out.println("Start connect: " + response.getName() + ", " + new Date());
		while(res == null) {
			try {
				res = Jsoup
						.connect(url)
					    .data(data)
					    .timeout(0)
					    .method(Method.POST)
					    .execute();
			} catch (IOException e) {
				System.out.println("exception: " + response.getName() + ", " + new Date());
			}
		}
		System.out.println("Finish connect: " + response.getName() + ", " + new Date());
		
		return changeEncoding(res.body());
	}
	
	private String changeEncoding(String s) {
		//http://stackoverflow.com/questions/24215063/unicode-replacement-with-ascii
		String data = s;
		Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
		Matcher m = p.matcher(data);
		StringBuffer buf = new StringBuffer(data.length());
		while (m.find()) {
		  String ch = String.valueOf((char) Integer.parseInt(m.group(1), 16));
		  m.appendReplacement(buf, Matcher.quoteReplacement(ch));
		}
		m.appendTail(buf);
		return buf.toString();
	}
}
