package es.masingenieros.infinisense.user;

import java.io.InputStream;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.visit.Visit;

@Entity
public class UserSignature extends DomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Lob
	private Blob signature;
	
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
	
	

}
