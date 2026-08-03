package com.bnote.global.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.rsData.RsData;
import jakarta.validation.ConstraintViolationException;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<RsData<Void>> handle(NoSuchElementException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new RsData<>("404-1", "요청한 데이터가 존재하지 않습니다."));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RsData<Void>> handle(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult()
			.getAllErrors()
			.stream()
			.filter(error -> error instanceof FieldError)
			.map(error -> (FieldError) error)
			.map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
			.sorted(Comparator.comparing(String::toString))
			.collect(Collectors.joining("\n"));

		return ResponseEntity.status(BAD_REQUEST).body(new RsData<>("400-1", message));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<RsData<Void>> handle(HttpMessageNotReadableException ex) {
		return ResponseEntity.status(BAD_REQUEST).body(new RsData<>("400-1", "요청 본문이 올바르지 않습니다."));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<RsData<Void>> handle(ConstraintViolationException ex) {
		String message = ex.getConstraintViolations()
			.stream()
			.map(violation -> violation.getPropertyPath() + "-" + violation.getMessage())
			.sorted(Comparator.comparing(String::toString))
			.collect(Collectors.joining("\n"));

		return ResponseEntity.status(BAD_REQUEST).body(new RsData<>("400-1", message));
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<RsData<Void>> handle(ServiceException ex) {
		RsData<Void> rsData = ex.getRsData();
		return ResponseEntity.status(rsData.statusCode()).body(rsData);
	}
}