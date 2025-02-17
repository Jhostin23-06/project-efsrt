package com.cibertec.projectefsrt.services.impl;

import com.cibertec.projectefsrt.entities.Genero;
import com.cibertec.projectefsrt.repositories.GeneroRepository;
import com.cibertec.projectefsrt.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public Genero findByName(String name) {
        return generoRepository.findByNomGenero(name);
    }

}
