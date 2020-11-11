package es.masingenieros.infinisense.visit;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.reason.Reason;
import es.masingenieros.infinisense.user.User;

@Entity
public class Visit extends DomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reason_id")
    @JsonProperty(access = JsonProperty.Access.AUTO)
	private Reason reason;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.AUTO)
	private User user;
	
	private Timestamp startDate;
	private Timestamp endDate;
	
	public Reason getReason() {
		return reason;
	}
	public void setReason(Reason reason) {
		this.reason = reason;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	
	
	
	
	
}
