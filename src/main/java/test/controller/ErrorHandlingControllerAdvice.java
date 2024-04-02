package test.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import lombok.RequiredArgsConstructor;

import test.exception.*; 

@RequiredArgsConstructor
@ControllerAdvice
public class ErrorHandlingControllerAdvice {
	@Autowired private final ErrorAttributes errorAttributes;
	
	@ExceptionHandler({InvalidPhoneNumberException.class, 
						UserNotFoundException.class, 
						InvalidUserException.class,
						DuplicatePhoneNumberException.class})
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		@ResponseBody Map<String, Object> onUserException(Exception ex, WebRequest request) {
		return getErrorAttributes(request, HttpStatus.BAD_REQUEST);
	}
	
	private Map<String, Object> getErrorAttributes(WebRequest request, HttpStatus httpStatus) {
		ErrorAttributeOptions errorOptions = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE);
		Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(request, errorOptions);
		errorAttributes.put("status", httpStatus.value());
		errorAttributes.put("error", httpStatus.getReasonPhrase());
		return errorAttributes;
	}
}
