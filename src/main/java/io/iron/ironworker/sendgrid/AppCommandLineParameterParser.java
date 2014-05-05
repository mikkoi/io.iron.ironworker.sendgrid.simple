/*
 * @author Mikko Koivunalho
 */
package io.iron.ironworker.sendgrid;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class AppCommandLineParameterParser {

	@Parameter
	private List<String> parameters = new ArrayList<String>();

	@Parameter(names = "-payload", required = true, converter = ParameterToPayloadConverter.class, validateWith = IsParameterJSONFile.class, description = "Name of the file that contains the payload.")
	private Payload payload;

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

}
