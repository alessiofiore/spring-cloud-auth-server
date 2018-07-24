package org.madbit.spring.auth.config;

import java.util.Arrays;

import org.madbit.spring.auth.exception.AuthResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 *
 * @author AFIORE
 * Created on 2018-05-30
 */

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;	

	@Value("${security.jwt.signing-key}")
	private String signingKey;
	
	@Value("${security.jwt.token-validity-seconds}")
	private int accessTokenValiditySeconds;

	@Value("${security.security-realm}")
	private String securityRealm;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * OAuth2 resource client configuration
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
		.inMemory()
		.withClient(clientId)
		.secret(passwordEncoder.encode(clientSecret))
		.authorizedGrantTypes(grantType)
		.authorities("STANDARD_USER")
		.scopes(scopeRead, scopeWrite)
		.accessTokenValiditySeconds(accessTokenValiditySeconds)
		.resourceIds(resourceIds); // extensible with other clients using .and()
	}

	/*
	 * JWT configuration
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		
		endpoints
		.tokenStore(tokenStore)
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(enhancerChain)
		.authenticationManager(authenticationManager)
		.exceptionTranslator(new AuthResponseExceptionTranslator());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security
//		.tokenKeyAccess("isAnonymous() || hasAuthority('STANDARD_USER')") // enable access to /oauth/token_key GET endpoint used for get auth server key used for sign token
//		.checkTokenAccess("hasAuthority('STANDARD_USER')"); // enable access to /oauth/check_token POST endpoint used by clients to check token validity
	}
	
	/*
     * JTW Token Store configuration
     */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(false);
		return defaultTokenServices;
	}

}
