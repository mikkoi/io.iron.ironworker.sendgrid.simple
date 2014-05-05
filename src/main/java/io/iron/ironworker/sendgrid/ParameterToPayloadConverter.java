/*
 * @author Mikko Koivunalho
 */
package io.iron.ironworker.sendgrid;

import com.beust.jcommander.IStringConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ParameterToPayloadConverter implements IStringConverter<Payload> {

	public Payload convert(String value) {
		JsonObject jsonConfig = io.iron.ironworker.sendgrid.Util
				.readFileToJsonObject(value);

		Gson gson = new GsonBuilder().create();
		Payload payload = gson.fromJson(jsonConfig, Payload.class);
		return payload;
	}
}
