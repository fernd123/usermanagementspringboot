package es.masingenieros.infinisense.exceptions;

import java.util.Date;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request){
		exception.printStackTrace();
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(true), null);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);	
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException exception){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Error", "", exception.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);	
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> customValidationErrorConstraintHandling(ConstraintViolationException exception){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Constraint Error", exception.getMessage(), null);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(FieldException.class)
	public ResponseEntity<?> customValidationFieldExceptionHandling(FieldException exception){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), exception.getErrorType(), exception.getFieldName());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);	
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> customValidationErrorDataIntegrityHandling(DataIntegrityViolationException exception){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Data Integrity", exception.getMessage(), null);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);	
	}

}
