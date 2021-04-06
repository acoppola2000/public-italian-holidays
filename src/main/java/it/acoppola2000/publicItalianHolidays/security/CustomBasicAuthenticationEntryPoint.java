package it.acoppola2000.publicItalianHolidays.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * https://www.baeldung.com/spring-security-basic-authentication
 * By default, the BasicAuthenticationEntryPoint provisioned by Spring Security returns a full page for a 401 Unauthorized response back to the client.
 * This HTML representation of the error renders well in a browser, but it not well suited for other scenarios, such as a REST API where a json representation may be preferred.
 */
@Component
class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authEx.getMessage());
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("it.acoppola2000");
        super.afterPropertiesSet();
    }
}