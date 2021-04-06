package it.acoppola2000.publicItalianHolidays.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration 
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomBasicAuthenticationEntryPoint authenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic()
				.authenticationEntryPoint(authenticationEntryPoint);	//customized

		httpSecurity
			.requiresChannel()
				.anyRequest().requiresSecure();							//https required

		httpSecurity
			.authorizeRequests()
				.anyRequest().authenticated();

	}


	@Autowired
	CustomUserPasswordAuthenticationProvider customAuthProvider;
	
	
	/* For example, one AuthenticationProvider might be able to validate a username/password, while another might be able to authenticate a SAML assertion.
	 * Each AuthenticationProvider has an opportunity to indicate that authentication should be successful, fail, 
	 * or indicate it cannot make a decision and allow a downstream AuthenticationProvider to decide. (return null) 
	 */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception { 
		authenticationManagerBuilder.authenticationProvider(customAuthProvider);
    }


}