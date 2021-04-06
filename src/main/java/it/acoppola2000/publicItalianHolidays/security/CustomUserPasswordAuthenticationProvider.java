package it.acoppola2000.publicItalianHolidays.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Annamaria
 * Custom Authentication provider perl'autenticazione con username e password 
 * May return null if the AuthenticationProvider is unable to support authentication of the passed Authentication object. 
 * In such a case, the next AuthenticationProvider that supports the presented Authentication class will be tried.
 */
/* Authentication:
 * Represents the token for an authentication request or for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication) method.
 * Once the request has been authenticated, the Authentication will usually be stored in a thread-local SecurityContext managed by the SecurityContextHolder by the authentication mechanism which is being used. An explicit authentication can be achieved, without using one of Spring Security's authentication mechanisms, by creating an Authentication instance and using the code:
 * SecurityContextHolder.getContext().setAuthentication(anAuthentication);
 * Note that unless the Authentication has the authenticated property set to true, 
 * it will still be authenticated by any security interceptor (for method or web invocations) which encounters it
 * 
 * All Known Implementing Classes:
 * AbstractAuthenticationToken, 
 * AnonymousAuthenticationToken, 
 * CasAssertionAuthenticationToken, 
 * CasAuthenticationToken, 
 * JaasAuthenticationToken, 
 * OpenIDAuthenticationToken, 
 * PreAuthenticatedAuthenticationToken, 
 * RememberMeAuthenticationToken, 
 * RunAsUserToken, 
 * TestingAuthenticationToken, 
 * UsernamePasswordAuthenticationToken 
 */

/*AccountStatusException, 
 * ActiveDirectoryAuthenticationException, 
 * AuthenticationCancelledException, 
 * AuthenticationCredentialsNotFoundException, 
 * AuthenticationServiceException, 
 * BadCredentialsException, 
 * InsufficientAuthenticationException, 
 * NonceExpiredException, 
 * PreAuthenticatedCredentialsNotFoundException, 
 * ProviderNotFoundException, 
 * RememberMeAuthenticationException, 
 * SessionAuthenticationException, 
 * UsernameNotFoundException
 */

/*
 * AuthenticationException subclasses:----
 * 
    AccountStatusException (AccountExpiredException, CredentialsExpiredException, DisabledException, LockedException) 
	ActiveDirectoryAuthenticationException, 
	AuthenticationCancelledException, 
	AuthenticationCredentialsNotFoundException, 
	AuthenticationServiceException, 
	BadCredentialsException, 
	InsufficientAuthenticationException, 
	NonceExpiredException, 
	OAuth2AuthenticationException, 
	PreAuthenticatedCredentialsNotFoundException, 
	ProviderNotFoundException, 
	RememberMeAuthenticationException, 
	Saml2AuthenticationException, 
	SessionAuthenticationException, 
	UsernameNotFoundException
 */        



@Component
class CustomUserPasswordAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
    	
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        if ((username == null) || (!username.equalsIgnoreCase("annamaria"))){
            throw new UsernameNotFoundException("bad credentials for username" + username);
        }
        if ((password == null) || (!password.equals("coppola"))) {
            throw new BadCredentialsException("bad credentials for username" + username);
        }
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		UserDetails principal = new User(username, password, grantedAuths);
		Authentication authenticationOk = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
		return authenticationOk;
    }
 
    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}