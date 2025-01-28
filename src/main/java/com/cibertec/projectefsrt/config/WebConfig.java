package com.cibertec.projectefsrt.config;

import com.cibertec.projectefsrt.converters.StringToGeneroConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private StringToGeneroConverter stringToGeneroConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToGeneroConverter);
    }

}
