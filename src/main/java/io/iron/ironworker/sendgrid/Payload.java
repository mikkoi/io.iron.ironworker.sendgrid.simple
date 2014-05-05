/*
 * @author Mikko Koivunalho
 */
package io.iron.ironworker.sendgrid;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Payload implements Serializable {

	private static final long serialVersionUID = -7302213640489167533L;

	private Email email;

	public Email getEmail() {
		return email;
	}

	public String toString() {
		return new ToStringBuilder(this).append("email", email.toString())
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Payload == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Payload otherConfig = (Payload) obj;
		return new EqualsBuilder().append(email, otherConfig.email).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(email).toHashCode();
	}

}
