package es.masingenieros.infinisense.reason;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.masingenieros.infinisense.lib.DomainObject;

@Entity
@Table(name="reason_project_token")
public class ReasonProjectToken extends DomainObject implements Serializable{

	private static final long serialVersionUID = 5020006791097834419L;

	private String token;

	private String email;
	
	private Boolean active;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id")
	private Reason reason;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
 
}
