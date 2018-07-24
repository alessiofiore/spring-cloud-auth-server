package org.madbit.spring.auth.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Serialize a JSON response starting from the exception
 *
 * @author AFIORE
 * Created on 2018-07-20
 */

public class CustomOAuth2ExceptionJacksonSerializer extends	JsonSerializer<CustomOAuth2Exception> {
	@Override
	public void serialize(CustomOAuth2Exception value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		
		Map<String, String> map = new HashMap<>();
		map.put("key", "value");
		map.put("key1", "value1");
		
		jgen.writeStartObject();
		jgen.writeFieldName("errors");
		jgen.writeStartObject();
		map.entrySet().forEach(entry -> {
			try {
				jgen.writeStringField(entry.getKey(), entry.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		jgen.writeEndObject();
		jgen.writeEndObject();
	}

}
