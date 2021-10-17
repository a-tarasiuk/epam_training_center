package com.epam.esm.configuration;

import com.epam.esm.converter.StringToColumnNameConverter;
import com.epam.esm.converter.StringToRequestParameterConverter;
import com.epam.esm.converter.StringToSqlSortOperatorConverter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

/**
 * Main ESM application configuration.
 */
@Configuration
@ComponentScan("com.epam.esm")
public class EsmConfiguration implements WebMvcConfigurer {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messages = new ResourceBundleMessageSource();
        messages.setBasename("/locale");
        messages.setDefaultLocale(Locale.ENGLISH);
        messages.setDefaultEncoding("UTF-8");
        return messages;
    }

    /**
     * Provides an opportunity to use ENUM's as request parameters.
     * The input name string must exactly match one of the declared enum values.
     * When we make a web request with a string value that doesn't match one of our enum values,
     * Spring will fail to convert it to the specified enum type.
     * In this case, we'll get a ConversionFailedException.
     *
     * @param registry - Provides registry of field formatting logic.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToColumnNameConverter());
        registry.addConverter(new StringToSqlSortOperatorConverter());
        registry.addConverter(new StringToRequestParameterConverter());
    }
}
