package com.atradius.cibt.api.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.atradius.cibt.api.response.ErrorResponse;


@RestControllerAdvice
public class GlobleExceptionHandler {

	/**
	 * handling specific exception
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundHandling(ResourceNotFoundException exception,
			WebRequest request) {
		ErrorResponse errorDetails = new ErrorResponse(new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	/**
	 * handle globle exception
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> globalExceptionHandling(Exception exception, WebRequest request) {
		ErrorResponse errorDetails = new ErrorResponse(new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
