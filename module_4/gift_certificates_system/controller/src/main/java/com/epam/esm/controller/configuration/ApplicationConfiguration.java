package com.epam.esm.controller.configuration;

import com.epam.esm.service.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Locale;

/**
 * Main ESM application configuration.
 */
@Configuration
@EnableTransactionManagement
public class ApplicationConfiguration implements WebMvcConfigurer {

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

    /**
     * For converting entity object from\to DTO.
     *
     * @return - ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PageMapper pageMapper() {
        return new PageMapper();
    }

    /**
     * Used to construct criteria queries, compound selections, expressions, predicates, orderings.
     *
     * @param entityManager Entity manager.
     * @return CriteriaBuilder.
     */
    @Bean
    public CriteriaBuilder criteriaBuilder(EntityManager entityManager) {
        return entityManager.getCriteriaBuilder();
    }
}
