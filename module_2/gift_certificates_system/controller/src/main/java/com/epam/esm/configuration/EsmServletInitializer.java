package com.epam.esm.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * WebApplicationInitializer to register a DispatcherServlet and use Java-based Spring configuration.
 * Implementations are required to implement:
 * getRootConfigClasses() -- for "root" application context (non-web infrastructure) configuration.
 * getServletConfigClasses() -- for DispatcherServlet application context (Spring MVC infrastructure) configuration.
 */
public class EsmServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * For "root" application context (non-web infrastructure) configuration.
     *
     * @return - Specify @Configuration and/or @Component classes for the root application context.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /**
     * For DispatcherServlet application context (Spring MVC infrastructure) configuration.
     *
     * @return - Specify @Configuration and/or @Component classes for the Servlet application context.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                DatabaseConfiguration.class
        };
    }

    /**
     * Specify the servlet mapping(s) for the DispatcherServlet — for example "/", "/app", etc.
     *
     * @return - Specify @Configuration and/or @Component classes for the mapping application context.
     */
    @Override
    protected String @NotNull [] getServletMappings() {
        return new String[]{"/"};
    }
}
