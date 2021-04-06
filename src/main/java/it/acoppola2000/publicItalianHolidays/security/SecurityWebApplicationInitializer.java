package it.acoppola2000.publicItalianHolidays.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//This would simply only register the springSecurityFilterChain Filter for every URL in your application.
//SEE https://docs.spring.io/spring-security/site/docs/current/reference/html5/#abstractsecuritywebapplicationinitializer-with-spring-mvc
class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}
