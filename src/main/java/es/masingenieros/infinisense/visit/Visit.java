package es.masingenieros.infinisense.visit;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.reason.Reason;
import es.masingenieros.infinisense.user.User;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Visit extends DomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id")
	//@JsonIgnore 
    //@JsonProperty(access = JsonProperty.Access.AUTO)
	private Reason reason;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	//@JsonIgnore 
    //@JsonProperty(access = JsonProperty.Access.AUTO)
	private User user;
	
	@Column(name="start_date")
	private Timestamp startDate;

	@Column(name="end_date")
	private Timestamp endDate;
	
	@Column(name="epis")
	private String epis;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="dni")
	private String dni;
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}

	@Column(name="signature")
	private String signature;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getEpis() {
		return epis;
	}
	public void setEpis(String epis) {
		this.epis = epis;
	}
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
