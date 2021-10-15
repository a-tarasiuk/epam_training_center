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

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToColumnNameConverter());
        registry.addConverter(new StringToSqlSortOperatorConverter());
        registry.addConverter(new StringToRequestParameterConverter());
    }
}
