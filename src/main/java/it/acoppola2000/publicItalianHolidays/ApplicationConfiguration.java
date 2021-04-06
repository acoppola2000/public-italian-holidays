package it.acoppola2000.publicItalianHolidays;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebMvc
@ComponentScan // @ComponentScan without arguments tells Spring to scan the current package and all of its sub-packages.
@PropertySource("${applicationPropertiesFilePath}")  //load application.properties from this external file
class ApplicationConfiguration {


}