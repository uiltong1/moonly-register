package br.com.moonlyRegister.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileStorageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileStorageNotFoundException(String exception) {
		super(exception);
	}

	public FileStorageNotFoundException(String exception, Throwable cause) {
		super(exception, cause);
	}
}
