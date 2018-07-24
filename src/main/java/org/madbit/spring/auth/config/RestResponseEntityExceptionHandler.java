package org.madbit.spring.auth.config;

import org.madbit.spring.auth.exception.CustomOAuth2Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author AFIORE
 * Created on 2018-06-25
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such master data")
	@ExceptionHandler(CustomOAuth2Exception.class)
	public void handleNotFound(Exception e) {
		System.out.println("@@@@@@@@@@@ ciao");
	}
}
