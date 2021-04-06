package it.acoppola2000.publicItalianHolidays;

import it.acoppola2000.publicItalianHolidays.ApplicationConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//registers a ContextLoaderListener (optionally) and a DispatcherServlet
class ApplicationBootstrap extends AbstractAnnotationConfigDispatcherServletInitializer
{

    //Specify @Configuration and/or @Component classes for the root application context.
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {};
    }

    //Specify @Configuration and/or @Component classes for the Servlet application context.
    protected Class<?>[] getServletConfigClasses()  {
        return new Class[] {ApplicationConfiguration .class};
    }

    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}