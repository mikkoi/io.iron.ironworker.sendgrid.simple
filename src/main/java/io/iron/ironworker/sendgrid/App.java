/*
 * @author Mikko Koivunalho
 */
package io.iron.ironworker.sendgrid;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.github.mrcritical.ironcache.CacheItem;
import com.github.mrcritical.ironcache.DefaultIronCache;
import com.github.mrcritical.ironcache.IronCache;
import com.github.sendgrid.SendGrid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(App.class);
		logger.debug("Worker entry point!");

		// Log all command line parameters.
		// Important: Must not write usernames or passwords to the log.
		String argsAsString = new String();
		for (int i = 0; i < args.length; i++) {
			argsAsString = argsAsString.concat(args[i]);
			argsAsString = argsAsString.concat(" ");
		}
		logger.debug("Command line parameters:{}.", argsAsString.toString());

		// Parse command line parameters.
		AppCommandLineParameterParser cliParams = new AppCommandLineParameterParser();
		JCommander jc = new JCommander();
		jc.addObject(cliParams);
		jc.setAcceptUnknownOptions(true);
		jc.parse(args);
		Payload payload = cliParams.getPayload();

		// Parse config file (included in worker code package).
		JsonObject jsonConfig = io.iron.ironworker.sendgrid.Util
				.readFileToJsonObject("config.json");
		Gson gson = new GsonBuilder().create();
		Config config = gson.fromJson(jsonConfig, Config.class);
		logger.debug("Config file read.");

		if (config.getSemaphore() != null) {
			// Semaphore (action stops here until the semaphore IronCache item
			// is active again.)
			// Semaphore is used for example to change the PaperTrail access
			// parameters without turn off the worker.
			logger.debug("Semaphore set. Checking...");
			String semaphoreValue = "1"; // Semaphore is "raised" (=odd number).
			IronCache cache = new DefaultIronCache(config.getSemaphore()
					.getToken(), config.getSemaphore().getProjectId(), config
					.getSemaphore().getCacheName());
			while (Integer.parseInt(semaphoreValue) >= 0
					&& Integer.parseInt(semaphoreValue) % 2 == 1) {
				try {
					CacheItem item = cache.get(config.getSemaphore().getKey());
					semaphoreValue = item.getValue();
					logger.debug("Fetched semaphore value is '{}'.",
							semaphoreValue);
					if (semaphoreValue.isEmpty()) {
						semaphoreValue = "2";
					}
					if (Integer.parseInt(semaphoreValue) >= 0
							&& Integer.parseInt(semaphoreValue) % 2 == 1) {
						logger.debug(
								"Sleep for 5 seconds before another attempt.",
								semaphoreValue);
						try {
							TimeUnit.SECONDS.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.debug("Semaphore not set. Skipping...");
		}

		// Get the SendGrid account userid and password.
		SendGridConfig sendGridConfig = new SendGridConfig();
		if ((config.getSendGridAccessUsername() == null || config
				.getSendGridAccessUsername().isEmpty())
				&& (config.getSendGridAccessPassword() == null || config
						.getSendGridAccessPassword().isEmpty())) {
			logger.debug("Config SendGrid username/password was uninitialized so we assume we need to use IronCache to get that information.");
			try {
				IronCache cache = new DefaultIronCache(config
						.getSendGridAccess().getToken(), config
						.getSendGridAccess().getProjectId(), config
						.getSendGridAccess().getCacheName());
				CacheItem item = cache.get(config.getSendGridAccess().getKey());
				logger.debug("SendGrid config fetched.");

				// Attempt to parse the string as JSON
				JsonParser parser = new JsonParser();
				JsonObject jsonObject = parser.parse(item.getValue())
						.getAsJsonObject();
				sendGridConfig = gson
						.fromJson(jsonObject, SendGridConfig.class);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sendGridConfig.setUsername(config.getSendGridAccessUsername());
			sendGridConfig.setPassword(config.getSendGridAccessPassword());
		}
		logger.debug("SendGridConfig read.");

		// Let's send an email
		SendGrid sendgrid = new SendGrid(sendGridConfig.getUsername(),
				sendGridConfig.getPassword());

		sendgrid.addTo(payload.getEmail().getTo());
		sendgrid.setFrom(payload.getEmail().getFrom());
		sendgrid.setSubject(payload.getEmail().getSubject());
		sendgrid.setText(payload.getEmail().getBody());
		sendgrid.send();
		logger.debug("SendGrid email sent.");

		logger.debug("Worker exit point!");
	}
}
