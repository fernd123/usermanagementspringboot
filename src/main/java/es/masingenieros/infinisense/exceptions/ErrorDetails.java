package es.masingenieros.infinisense.exceptions;

import java.util.Date;

public class ErrorDetails {

	private Date timestamp;
	private String type;
	private String details;
	private String fieldName;

	
	public ErrorDetails(Date timestamp, String message, String details, String fieldName) {
		super();
		this.timestamp = timestamp;
		this.type = message;
		this.details = details;
		this.fieldName = fieldName;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return type;
	}
	public void setMessage(String message) {
		this.type = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
	
}
