package com.cibertec.projectefsrt.converters;

import com.cibertec.projectefsrt.entities.Genero;
import com.cibertec.projectefsrt.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGeneroConverter implements Converter<String, Genero> {
    @Autowired
    private GeneroService generoService;

    @Override
    public Genero convert(String source) {
        return generoService.findByName(source);
    }

}
