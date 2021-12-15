package br.com.moonlyRegister.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.moonlyRegister.exception.ExceptionResponseEntity;
import br.com.moonlyRegister.exception.GenericException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntity extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponseEntity> handlerAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponseEntity exceptionResponseEntity = new ExceptionResponseEntity(ex.getMessage(),
				request.getDescription(false), new Date());
		return new ResponseEntity<ExceptionResponseEntity>(exceptionResponseEntity, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(GenericException.class)
	public final ResponseEntity<ExceptionResponseEntity> handlerGenericException(Exception ex, WebRequest request) {
		ExceptionResponseEntity exceptionResponseEntity = new ExceptionResponseEntity(ex.getMessage(),
				request.getDescription(false), new Date());
		return new ResponseEntity<ExceptionResponseEntity>(exceptionResponseEntity, HttpStatus.BAD_REQUEST);
	}
}
