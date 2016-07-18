package pl.tomo.provider.json.serialize;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import pl.tomo.entity.Patient;

public class PatientSerializer implements JsonSerializer<Patient>{

	@Override
	public JsonElement serialize(Patient patient, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("id", new JsonPrimitive(patient.getId()));
		result.add("idServer", new JsonPrimitive(patient.getIdServer()));
		result.add("name", new JsonPrimitive(patient.getName()));
		result.add("birthdayLong", new JsonPrimitive(patient.getBirthdayLong()));
		return result;
	}



}
