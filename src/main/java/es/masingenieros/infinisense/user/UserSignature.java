package es.masingenieros.infinisense.user;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.masingenieros.infinisense.lib.DomainObject;

@Entity
@Table(name="user_signature")
public class UserSignature extends DomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Lob
	private Blob signature;
	
	private String path;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.AUTO)
	private User user;

	public Blob getSignature() {
		return signature;
	}

	public void setSignature(Blob signature) {
		this.signature = signature;
	}

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
