package es.masingenieros.infinisense.wrapper;

import java.io.Serializable;

public class Tuple implements Serializable{

	private static final long serialVersionUID = -192134909673110426L;

	private String name;

	private int value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
