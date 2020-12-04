package es.masingenieros.infinisense.exceptions;

import es.masingenieros.infinisense.company.service.impl.TenantException;

public class FieldException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4695622869801219677L;
	private String fieldName;
	private String errorType;


	public FieldException(String s, String fieldName, String errorType){
		super(s);  
		this.setFieldName(fieldName);
		this.setErrorType(errorType);
	}

	public FieldException() {
	}

	public FieldException(String message) {
		super(message);
	}

	public FieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldException(Throwable cause) {
		super(cause);
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public String getErrorType() {
		return errorType;
	}


	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}


	public static class InvalidValue extends TenantException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidValue() {
		}

		public InvalidValue(String message) {
			super(message);
		}

		public InvalidValue(String message, Throwable cause) {
			super(message, cause);
		}

		public InvalidValue(Throwable cause) {
			super(cause);
		}

	}
}  