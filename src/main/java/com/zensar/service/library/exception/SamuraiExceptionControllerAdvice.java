package com.zensar.service.library.exception;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class SamuraiExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value());

		/*
		 * List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x
		 * -> x.getDefaultMessage()) .collect(Collectors.toList());
		 */
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		List<String> errors1 = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getField())
				.collect(Collectors.toList());
		errors.addAll(errors1);

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@Override
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);

			errors.add(ex.getMessage());
			errors.add(ex.getCause().getLocalizedMessage());
		}
		return new ResponseEntity<>(errors, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFound.class)
	protected ResponseEntity<Object> handleNotFound(ResourceNotFound ex, WebRequest request) {

		ErrorDetails errors = new ErrorDetails();
		errors.setTimestamp(new Date());
		errors.setMessage(ex.getMessage());
		errors.setDetails(ex.getLocalizedMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.name());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
}
