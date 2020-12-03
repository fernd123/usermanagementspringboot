package es.masingenieros.infinisense.company.service.impl;

public class TenantException extends Exception {
	private static final long serialVersionUID = 1L;

	public TenantException() {
	}

	public TenantException(String message) {
		super(message);
	}

	public TenantException(String message, Throwable cause) {
		super(message, cause);
	}

	public TenantException(Throwable cause) {
		super(cause);
	}

	public static class CreateUpdateTenantSchemaException extends TenantException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CreateUpdateTenantSchemaException() {
		}

		public CreateUpdateTenantSchemaException(String message) {
			super(message);
		}

		public CreateUpdateTenantSchemaException(String message, Throwable cause) {
			super(message, cause);
		}

		public CreateUpdateTenantSchemaException(Throwable cause) {
			super(cause);
		}

	}

	public static class TenantSchemaAlreadyExistsException extends TenantException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TenantSchemaAlreadyExistsException() {
		}

		public TenantSchemaAlreadyExistsException(String message) {
			super(message);
		}

		public TenantSchemaAlreadyExistsException(String message, Throwable cause) {
			super(message, cause);
		}

		public TenantSchemaAlreadyExistsException(Throwable cause) {
			super(cause);
		}

	}

	public static class DeleteTenantSchemaException extends TenantException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DeleteTenantSchemaException() {
		}

		public DeleteTenantSchemaException(String message) {
			super(message);
		}

		public DeleteTenantSchemaException(String message, Throwable cause) {
			super(message, cause);
		}

		public DeleteTenantSchemaException(Throwable cause) {
			super(cause);
		}

	}

	public static class QueryTenantSchemaException extends TenantException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public QueryTenantSchemaException() {
		}

		public QueryTenantSchemaException(String message) {
			super(message);
		}

		public QueryTenantSchemaException(String message, Throwable cause) {
			super(message, cause);
		}

		public QueryTenantSchemaException(Throwable cause) {
			super(cause);
		}

	}

	public static class TenantNotDefinedException extends TenantException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TenantNotDefinedException() {
		}

		public TenantNotDefinedException(String message) {
			super(message);
		}

		public TenantNotDefinedException(String message, Throwable cause) {
			super(message, cause);
		}

		public TenantNotDefinedException(Throwable cause) {
			super(cause);
		}

	}

}
