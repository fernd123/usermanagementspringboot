package es.masingenieros.infinisense.reason;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import es.masingenieros.infinisense.lib.DomainObject;

@Entity
@Table(name="reason_project_email")
public class ReasonProjectEmail extends DomainObject implements Serializable{

	private static final long serialVersionUID = -1044014243119527667L;

	@Email
	private String email;
	
	private String company;

	private Boolean sended;
	
	private Boolean answered;
	
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

	public Boolean getSended() {
		return sended;
	}

	public void setSended(Boolean sended) {
		this.sended = sended;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	public Boolean getAnswered() {
		return answered;
	}

	public void setAnswered(Boolean answered) {
		this.answered = answered;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
 
}
