package com.cibertec.projectefsrt.controllers;

import com.cibertec.projectefsrt.entities.Pelicula;
import com.cibertec.projectefsrt.services.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping("generarCodigo")
    @ResponseBody
    public Map<String, String> generarCodigo(){
        String codigo = peliculaService.generarSigCodPelicula();
        Map<String, String> response = new HashMap<>();
        response.put("codigo", codigo);
        return response;
    }

    @GetMapping
    public String listarPeliculas(Model model){
        List<Pelicula> peliculas = peliculaService.findAllActive();
        List<Map<String, Object>> peliculasFormateadas = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
            Map<String, Object> peliculaMap = new HashMap<>();
            peliculaMap.put("id", pelicula.getId());
            peliculaMap.put("codPelicula", pelicula.getCodPelicula());
            peliculaMap.put("nomPelicula", pelicula.getNomPelicula());
            peliculaMap.put("idGenero", pelicula.getIdGenero().getNomGenero());
            peliculaMap.put("duracion", pelicula.getDuracion());
            peliculaMap.put("anio", pelicula.getAnio());
            peliculaMap.put("copias", pelicula.getCopias());
            peliculasFormateadas.add(peliculaMap);
        }
        model.addAttribute("peliculas", peliculasFormateadas);
        return "peliculas";
    }

    @PostMapping
    public String registrarPelicula(Pelicula pelicula){
        peliculaService.guardarPelicula(pelicula);
        return "redirect:/peliculas";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Map<String, Object> obtenerPelicula(@PathVariable Integer id){
       Pelicula pelicula = peliculaService.obtenerPelicula(id).orElse(null);
       Map<String, Object> response = new HashMap<>();
       if (pelicula != null) {
           response.put("id", pelicula.getId());
           response.put("codPelicula", pelicula.getCodPelicula());
           response.put("nomPelicula", pelicula.getNomPelicula());
           response.put("idGenero", pelicula.getIdGenero().getNomGenero());
           response.put("duracion", pelicula.getDuracion());
           response.put("anio", pelicula.getAnio());
           response.put("copias", pelicula.getCopias());
       }
       return response;
    }

    @PostMapping("/editar")
    public String editarPelicula(@ModelAttribute Pelicula pelicula){
        peliculaService.guardarPelicula(pelicula);
        return "redirect:/peliculas";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable Integer id) {
        peliculaService.eliminarPelicula(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPeliculaLogicamente(@PathVariable Integer id) {
        Pelicula pelicula = peliculaService.obtenerPelicula(id).orElse(null);
        if (pelicula != null) {
            pelicula.setEstadoPel(0); // Cambia el estado a 0 para marcarlo como eliminado
            peliculaService.guardarPelicula(pelicula);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public String buscarPeliculas(@RequestParam String query, Model model){
        List<Pelicula> peliculas = peliculaService.buscarPeliculasActivas(query);
        List<Map<String, Object>> peliculasFormateadas = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
            Map<String, Object> peliculaMap = new HashMap<>();
            peliculaMap.put("id", pelicula.getId());
            peliculaMap.put("codPelicula", pelicula.getCodPelicula());
            peliculaMap.put("nomPelicula", pelicula.getNomPelicula());
            peliculaMap.put("idGenero", pelicula.getIdGenero().getNomGenero());
            peliculaMap.put("duracion", pelicula.getDuracion());
            peliculaMap.put("anio", pelicula.getAnio());
            peliculaMap.put("copias", pelicula.getCopias());
            peliculasFormateadas.add(peliculaMap);
        }
        model.addAttribute("peliculas", peliculasFormateadas);
        return "peliculas";
    }

}
