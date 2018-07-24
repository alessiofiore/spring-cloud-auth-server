package org.madbit.spring.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * Catch the framework exception and translate it in a custom one
 * 
 * @author AFIORE
 * Created on 2018-07-20
 */

public class AuthResponseExceptionTranslator implements WebResponseExceptionTranslator {

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		
		return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CustomOAuth2Exception("100" ,"ciao"));
	}

}
