package es.masingenieros.infinisense.reason;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.user.User;

@Entity
@Table(name="reason_project_participant")
public class ReasonProjectParticipant extends DomainObject implements Serializable{

	private static final long serialVersionUID = -1044014243119527667L;

	private Boolean active;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_id")
	private Reason reason;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private User user;

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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
 
}
