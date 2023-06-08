package com.project.bookstore.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.bookstore.Payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		ApiResponse response = ApiResponse.builder().message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiResponse> badRequestExceptionHandler(BadRequestException ex) {
		ApiResponse response = ApiResponse.builder().message(ex.getMessage()).httpStatus(HttpStatus.BAD_REQUEST)
				.build();
		return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}

}
