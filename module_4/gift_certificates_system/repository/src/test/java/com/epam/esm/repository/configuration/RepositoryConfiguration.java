package com.epam.esm.repository.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Locale;

/**
 * Main ESM application configuration.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.epam.esm")
@ComponentScan("com.epam.esm")
@EntityScan("com.epam.esm")
@SpringBootConfiguration
public class RepositoryConfiguration {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messages = new ResourceBundleMessageSource();
        messages.setBasename("locale");
        messages.setDefaultLocale(Locale.ENGLISH);
        messages.setDefaultEncoding("UTF-8");
        return messages;
    }

    /**
     * In order to be able to validate the method arguments,
     * need to release this bean and annotate the class or method <code>@Validated</code>.
     * <br>
     * If the argument is invalid, then throw exception <code>javax.validation.ConstraintViolationException</code>.
     *
     * @return
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
