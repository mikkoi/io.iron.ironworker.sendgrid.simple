/*
 * @author Mikko Koivunalho
 */
package io.iron.ironworker.sendgrid;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Email implements Serializable {

	private static final long serialVersionUID = -7302213640489167533L;

	private String to;
	private String replyTo;
	private String from;
	private String subject;
	private String body;

	public String getTo() {
		return to;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public String getFrom() {
		return from;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public String toString() {
		return new ToStringBuilder(this).append("to", to)
				.append("replyTo", replyTo).append("from", from)
				.append("subject", subject).append("body", body).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Email == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Email otherConfig = (Email) obj;
		return new EqualsBuilder().append(to, otherConfig.to)
				.append(replyTo, otherConfig.replyTo)
				.append(from, otherConfig.from)
				.append(subject, otherConfig.subject)
				.append(body, otherConfig.body).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(to).append(replyTo)
				.append(from).append(subject).append(body).toHashCode();
	}

}
