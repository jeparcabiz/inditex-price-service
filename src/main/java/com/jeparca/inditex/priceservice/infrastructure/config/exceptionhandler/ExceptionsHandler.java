package com.jeparca.inditex.priceservice.infrastructure.config.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jeparca.inditex.priceservice.application.exceptions.NotFoundException;
import com.jeparca.inditex.priceservice.model.v1.ErrorDTO;

@RestControllerAdvice
public class ExceptionsHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ErrorDTO handleNotFoundPrice(NotFoundException e) {
		return getError(e.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return getError("Bad parameters");
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ErrorDTO handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		return getError("Bad parameters");
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public ErrorDTO handleNotControlledExceptions(Throwable e) {
		return getError(e.getMessage());
	}

	private ErrorDTO getError(String msg) {
		return ErrorDTO.builder().id(UUID.randomUUID().toString()).timestamp(OffsetDateTime.now()).message(msg).build();
	}

}
