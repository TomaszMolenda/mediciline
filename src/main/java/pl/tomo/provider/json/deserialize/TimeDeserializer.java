package pl.tomo.provider.json.deserialize;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class TimeDeserializer extends JsonDeserializer<Date>{

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		JsonNode node = p.getCodec().readTree(p);
		try {
			return formatter.parse(node.asText());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
