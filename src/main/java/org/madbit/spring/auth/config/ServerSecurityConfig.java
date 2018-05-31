package org.madbit.spring.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author AFIORE
 * Created on 2018-05-29
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.security-realm}")
	private String securityRealm;
	
	@Autowired
	private UserDetailsService userDetailsService;

	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	/*
	 *  Client authentication configuration
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
        .userDetailsService(this.userDetailsService)
        .passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * Security configuration
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.httpBasic().realmName(securityRealm)
		.and()
		.csrf().disable();
	}
}
