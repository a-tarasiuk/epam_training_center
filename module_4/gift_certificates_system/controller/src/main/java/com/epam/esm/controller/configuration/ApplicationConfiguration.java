package com.epam.esm.controller.configuration;

import com.epam.esm.controller.converter.StringToRequestParameterConverter;
import com.epam.esm.controller.converter.StringToSqlSortOperatorConverter;
import com.epam.esm.service.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
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

    /**
     * Provides an opportunity to use ENUM's as request parameters.
     * The input name string must exactly match one of the declared enum values.
     * When we make a web request with a string value that doesn't match one of our enum values,
     * Spring will fail to convert it to the specified enum type.
     * In this case, we'll get a ConversionFailedException.
     * Need <code>implements WebMvcConfigurer</code>
     *
     * @param registry - Provides registry of field formatting logic.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSqlSortOperatorConverter());
        registry.addConverter(new StringToRequestParameterConverter());
    }
}
