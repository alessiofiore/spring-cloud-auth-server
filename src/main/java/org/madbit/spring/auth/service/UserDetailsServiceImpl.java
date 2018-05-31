package org.madbit.spring.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.madbit.spring.auth.dto.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class implements the authentication logic
 * 
 * @author AFIORE
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	logger.debug("#################### loadUserByUsername");
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	authorities.add(new SimpleGrantedAuthority("STANDARD_USER"));
    	
    	return new JwtUser(username, "$2a$10$PD3kHtFOnuv.aR.SPp6Le.yPsgnsrIy0CzHGyDULE0sFi7SaHXgGK", authorities, true);
    }
}