package es.masingenieros.infinisense.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.masingenieros.infinisense.lib.DomainObject;

@Entity
@Table(name="user_signature")
public class UserSignature extends DomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String path;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
