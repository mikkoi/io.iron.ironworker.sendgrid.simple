/*
 * @author Mikko Koivunalho
 */
package io.iron.ironworker.sendgrid;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.beust.jcommander.ParameterException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Util {

	public static JsonObject readFileToJsonObject(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			throw new ParameterException(
					"The file should be the name of a JSON encoded file. No file '"
							+ filename + "' found!");
		}
		if (!file.canRead()) {
			throw new ParameterException(
					"The file should be the name of a JSON encoded file. The file '"
							+ filename + "' is not readable!");
		}

		// read the contents of the file to a string
		String fileContents = "";
		try {
			List<String> fileLines = Files.readAllLines(Paths.get(filename),
					Charset.forName("UTF-8"));
			fileContents = StringUtils.join(fileLines.toArray());
		} catch (IOException e) {
			throw new ParameterException(
					"The file should be the name of a JSON encoded file. Could not read the file '"
							+ filename + "'!");
		}

		// Attempt to parse the string as JSON
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(fileContents).getAsJsonObject();

		return jsonObject;
	}
}
