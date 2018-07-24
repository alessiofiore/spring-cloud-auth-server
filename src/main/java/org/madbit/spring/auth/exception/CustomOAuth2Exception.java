package org.madbit.spring.auth.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 *
 * @author AFIORE
 * Created on 2018-07-20
 */

@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = CustomOAuth2ExceptionJacksonSerializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {
	
	private String code;

	public CustomOAuth2Exception(String code, String msg) {
		super(msg);
		this.code = code;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getCode() {
		return code;
	}
}
