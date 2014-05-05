/*
 * @author Mikko Koivunalho
 */
package io.iron.ironworker.sendgrid;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SendGridConfig implements Serializable {

	private static final long serialVersionUID = -7302213640489167533L;

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return new ToStringBuilder(this).append("username", username)
				.append("password", password).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SendGridConfig == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		SendGridConfig otherSendGridConfig = (SendGridConfig) obj;
		return new EqualsBuilder()
				.append(username, otherSendGridConfig.username)
				.append(password, otherSendGridConfig.password).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(username).append(password)
				.toHashCode();
	}

}
