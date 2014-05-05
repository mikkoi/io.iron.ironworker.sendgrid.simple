/*
 * @author Mikko Koivunalho
 */
package io.iron.ironworker.sendgrid;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class IsParameterJSONFile implements IParameterValidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.beust.jcommander.IParameterValidator#validate(java.lang.String,
	 * java.lang.String)
	 */
	public void validate(String arg0, String arg1) throws ParameterException {
		io.iron.ironworker.sendgrid.Util.readFileToJsonObject(arg1);
	}

}
