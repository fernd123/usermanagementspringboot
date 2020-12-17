package es.masingenieros.infinisense.message;

import javax.persistence.Entity;

import es.masingenieros.infinisense.lib.DomainObject;

@Entity
public class Message extends DomainObject{

	private static final long serialVersionUID = 6219328101884542230L;

	private String name;

	private MessageEnum type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MessageEnum getType() {
		return type;
	}

	public void setType(MessageEnum type) {
		this.type = type;
	}
	
}

	