/*
 * @author Mikko Koivunalho
 */
package io.iron.ironworker.sendgrid;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Config implements Serializable {

	private static final long serialVersionUID = -7302213640489167533L;

	/*
	 * The IronCache item which serves as a semaphore. When semaphore is on, the
	 * worker is waiting. Use semaphore to change SendGridAccess parameters.
	 */
	private ConfigIronCacheItem semaphore;

	/* The IronCache item which contains the SendGridAccess parameters. */
	private ConfigIronCacheItem sendGridAccess;

	/* Alternate means of providing Sendgrid access */
	private String sendGridAccessUsername;
	private String sendGridAccessPassword;

	public ConfigIronCacheItem getSemaphore() {
		return semaphore;
	}

	public ConfigIronCacheItem getSendGridAccess() {
		return sendGridAccess;
	}

	public String getSendGridAccessUsername() {
		return sendGridAccessUsername;
	}

	public String getSendGridAccessPassword() {
		return sendGridAccessPassword;
	}

	public String toString() {
		return new ToStringBuilder(this).append("semaphore", semaphore)
				.append("sendGridAccess", sendGridAccess)
				.append("sendGridAccessUsername", sendGridAccessUsername)
				.append("sendGridAccessPassword", sendGridAccessPassword)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Config == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Config otherConfig = (Config) obj;
		return new EqualsBuilder()
				.append(semaphore, otherConfig.semaphore)
				.append(sendGridAccess, otherConfig.sendGridAccess)
				.append(sendGridAccessUsername,
						otherConfig.sendGridAccessUsername)
				.append(sendGridAccessPassword,
						otherConfig.sendGridAccessPassword).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(semaphore)
				.append(sendGridAccess).append(sendGridAccessUsername)
				.append(sendGridAccessPassword).toHashCode();
	}

	public class ConfigIronCacheItem implements Serializable {

		private static final long serialVersionUID = -7302213640489167533L;

		private String token;
		private String project_id; // Non Java form of variable! Same as in
									// iron.json config!
		private String host;
		private String cacheName;
		private String key;

		public String toString() {
			return new ToStringBuilder(this).append("token", token)
					.append("project_id", project_id).append("host", host)
					.append("cacheName", cacheName).append("key", key)
					.toString();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof ConfigIronCacheItem == false) {
				return false;
			}
			if (this == obj) {
				return true;
			}
			ConfigIronCacheItem otherConfigIronCacheItem = (ConfigIronCacheItem) obj;
			return new EqualsBuilder()
					.append(token, otherConfigIronCacheItem.token)
					.append(project_id, otherConfigIronCacheItem.project_id)
					.append(host, otherConfigIronCacheItem.host)
					.append(cacheName, otherConfigIronCacheItem.cacheName)
					.append(key, otherConfigIronCacheItem.key).isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 37).append(token).append(project_id)
					.append(cacheName).append(key).toHashCode();
		}

		public String getToken() {
			return token;
		}

		public String getProjectId() {
			return project_id;
		}

		public String getHost() {
			return host;
		}

		public String getCacheName() {
			return cacheName;
		}

		public String getKey() {
			return key;
		}

	}
}
