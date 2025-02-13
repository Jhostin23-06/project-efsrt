package com.cibertec.projectefsrt.controllers;

import com.cibertec.projectefsrt.entities.Actor;
import com.cibertec.projectefsrt.entities.Alquiler;
import com.cibertec.projectefsrt.services.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Controller
@RequestMapping("/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                } catch (DateTimeParseException e) {
                    setValue(null);
                }
            }

            @Override
            public String getAsText() {
                LocalDate value = (LocalDate) getValue();
                return value != null ? value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
            }
        });
    }

    @GetMapping
    public String listAlquileres(Model model) {
        List<Alquiler> alquileres = alquilerService.findAll();
        List<Map<String, Object>> alquileresMap = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Alquiler alquiler : alquileres) {
            Map<String, Object> alquilerMap = Map.of(
                "id", alquiler.getId(),
                "codAlquiler", alquiler.getCodAlquiler(),
                "fechaPrest", alquiler.getFechaPrest().atZone(ZoneId.systemDefault()).format(formatter),
                "fechaDev", alquiler.getFechaDev().atZone(ZoneId.systemDefault()).format(formatter),
                "idEmpleado", alquiler.getIdEmpleado().getNomEmpleado(),
                "idCliente", alquiler.getIdCliente().getNomCliente()
            );
            alquileresMap.add(alquilerMap);
        }

        model.addAttribute("alquileres", alquileresMap);
        return "alquileres";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Alquiler> getAlquilerById(@PathVariable Integer id) {
        return alquilerService.findById(id);
    }

    @GetMapping("/buscar")
    public String buscarAlquiler(@RequestParam String query, Model model) {
        List<Alquiler> alquileres = alquilerService.buscarAlquileresActivos(query);
        List<Map<String, Object>> alquileresMap = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Alquiler alquiler : alquileres) {
            Map<String, Object> alquilerMap = new HashMap<>();
            alquilerMap.put("id", alquiler.getId());
            alquilerMap.put("codAlquiler", alquiler.getCodAlquiler());
            alquilerMap.put("fechaPrest", alquiler.getFechaPrest().atZone(ZoneId.systemDefault()).format(formatter));
            alquilerMap.put("fechaDev", alquiler.getFechaDev().atZone(ZoneId.systemDefault()).format(formatter));
            alquilerMap.put("idEmpleado", alquiler.getIdEmpleado().getNomEmpleado());
            alquilerMap.put("idCliente", alquiler.getIdCliente().getNomCliente());
            alquileresMap.add(alquilerMap);
        }
        model.addAttribute("alquileres", alquileresMap);
        return "alquileres";
    }

    @PostMapping("/guardar")
    public String saveAlquiler(@ModelAttribute Alquiler alquiler) {
        alquilerService.save(alquiler);
        return "redirect:/alquileres";
    }

    @PostMapping("/eliminar/{id}")
    public String deleteAlquiler(@PathVariable Integer id) {
        alquilerService.deleteById(id);
        return "redirect:/alquileres";
    }
}