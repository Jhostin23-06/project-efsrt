package com.cibertec.projectefsrt.services;

import com.cibertec.projectefsrt.entities.Genero;
import com.cibertec.projectefsrt.entities.Pelicula;
import com.cibertec.projectefsrt.repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<Pelicula> listarPeliculas(){
        return peliculaRepository.findByEstadoPel(1);
    }

    public Pelicula guardarPelicula(Pelicula pelicula){
        return peliculaRepository.save(pelicula);
    }

    public Optional<Pelicula> obtenerPelicula(Integer id){
        return peliculaRepository.findById(id);
    }

    public void eliminarPelicula(Integer id){
        peliculaRepository.deleteById(id);
    }

    public List<Pelicula> buscarPeliculasActivas(String query){
        return peliculaRepository.findByEstadoPelAndNomPeliculaContaining(1,query);
    }

    public String generarSigCodPelicula(){
        Optional<Pelicula> ultimaPelicula = peliculaRepository.findTopByOrderByCodPeliculaDesc();
        if (ultimaPelicula.isPresent()) {
            String ultimoCodigo = ultimaPelicula.get().getCodPelicula();
            int numero = Integer.parseInt(ultimoCodigo.substring(1)) + 1;
            return "P" + String.format("%04d", numero);
        } else {
            return "P0001";
        }
    }
    public List<Pelicula> findAllActive(){
        return peliculaRepository.findByEstadoPel(1);
    }

    public List<Pelicula> buscarPeliculasPorIdGenero(String nomGenero) {
        return peliculaRepository.findByIdGenero_NomGenero(nomGenero);
    }
}
