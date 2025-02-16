package com.cibertec.projectefsrt.controllers;

import com.cibertec.projectefsrt.entities.Alquiler;
import com.cibertec.projectefsrt.entities.Cliente;
import com.cibertec.projectefsrt.entities.Empleado;
import com.cibertec.projectefsrt.entities.Pelicula;
import com.cibertec.projectefsrt.services.AlquilerService;
import com.cibertec.projectefsrt.services.ClienteService;
import com.cibertec.projectefsrt.services.EmpleadoService;
import com.cibertec.projectefsrt.services.PeliculaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Controller
@RequestMapping("/alquileres")
public class AlquilerController {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(AlquilerController.class);

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private EmpleadoService empleadoService; // Suponiendo que tienes un servicio para los empleados

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PeliculaService peliculaService;

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

    @GetMapping("/generarCodigo")
    @ResponseBody
    public Map<String, String> generarCodigo() {
        String codigo = alquilerService.generarSigCodAlquiler();
        Map<String, String> response = new HashMap<>();
        response.put("codigo", codigo);
        return response;
    }

    @GetMapping
    public String listAlquileres(Model model) {
        List<Alquiler> alquileres = alquilerService.findAll();
        List<Map<String, Object>> alquileresMap = new ArrayList<>();

        // Cargar los clientes y empleados
        List<Cliente> clientes = clienteService.findAllActive();
        List<Empleado> empleados = empleadoService.readEmpleados();
        List<Pelicula> peliculas = peliculaService.listarPeliculas();


        for (Alquiler alquiler : alquileres) {
            Map<String, Object> alquilerMap = Map.of(
                "id", alquiler.getId(),
                "codAlquiler", alquiler.getCodAlquiler(),
                "fechaPrest", alquiler.getFechaPrest(),
                "fechaDev", alquiler.getFechaDev(),
                "idPelicula", alquiler.getIdPelicula().getNomPelicula(),
                "idEmpleado", alquiler.getIdEmpleado().getNomEmpleado(),
                "idCliente", alquiler.getIdCliente().getNomCliente()
            );
            alquileresMap.add(alquilerMap);
        }

        model.addAttribute("alquileres", alquileresMap);
        model.addAttribute("peliculas", peliculas);
        model.addAttribute("clientes", clientes);
        model.addAttribute("empleados", empleados);
        return "alquileres";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Alquiler> getAlquilerById(@PathVariable Integer id) {
        return alquilerService.findById(id);
    }

    @PostMapping("/actualizar")
    public String actualizarAlquiler(@RequestParam("fechaPrest") String fechaPrest,
                                     @RequestParam("fechaDev") String fechaDev,
                                     @ModelAttribute Alquiler alquiler) {
        try {
            // Verifica las fechas recibidas
            System.out.println("Fecha de préstamo recibida: " + fechaPrest);
            System.out.println("Fecha de devolución recibida: " + fechaDev);

            // Asegúrate de que las fechas son válidas y convierte a LocalDate
            if (fechaPrest == null || fechaPrest.isEmpty()) {
                fechaPrest = LocalDate.now().toString();  // Establece la fecha actual si es nula o vacía
            }
            if (fechaDev == null || fechaDev.isEmpty()) {
                fechaDev = LocalDate.now().toString();  // Establece la fecha actual si es nula o vacía
            }

            // Asignar las fechas convertidas a la entidad Alquiler
            alquiler.setFechaPrest(LocalDate.parse(fechaPrest));
            alquiler.setFechaDev(LocalDate.parse(fechaDev));

            // Guardar el alquiler
            alquiler.setEstadoAlq(1); // Establecer el estado activo
            alquilerService.save(alquiler);
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Maneja el error de la forma adecuada
        }
        return "redirect:/alquileres";
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
            alquilerMap.put("fechaPrest", alquiler.getFechaPrest());
            alquilerMap.put("fechaDev", alquiler.getFechaDev());
            alquilerMap.put("idPelicula", alquiler.getIdPelicula().getNomPelicula());
            alquilerMap.put("idEmpleado", alquiler.getIdEmpleado().getNomEmpleado());
            alquilerMap.put("idCliente", alquiler.getIdCliente().getNomCliente());
            alquileresMap.add(alquilerMap);
        }
        model.addAttribute("alquileres", alquileresMap);
        return "alquileres";
    }

    @PostMapping
    public String registrarAlquiler(@RequestParam("fechaPrest") String fechaPrest,
                                    @RequestParam("fechaDev") String fechaDev,
                                    @ModelAttribute Alquiler alquiler) {
        try {
            // Verifica las fechas recibidas
            System.out.println("Fecha de préstamo recibida: " + fechaPrest);
            System.out.println("Fecha de devolución recibida: " + fechaDev);

            // Asegúrate de que las fechas son válidas y convierte a LocalDate
            if (fechaPrest == null || fechaPrest.isEmpty()) {
                fechaPrest = LocalDate.now().toString();  // Establece la fecha actual si es nula o vacía
            }
            if (fechaDev == null || fechaDev.isEmpty()) {
                fechaDev = LocalDate.now().toString();  // Establece la fecha actual si es nula o vacía
            }

            // Asignar las fechas convertidas a la entidad Alquiler
            alquiler.setFechaPrest(LocalDate.parse(fechaPrest));
            alquiler.setFechaDev(LocalDate.parse(fechaDev));

            // Guardar el alquiler
            alquiler.setEstadoAlq(1); // Establecer el estado activo
            alquilerService.save(alquiler);
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Maneja el error de la forma adecuada
        }
        return "redirect:/alquileres";
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarAlquilerLogicamente(@PathVariable Integer id) {
        Optional<Alquiler> alquiler = alquilerService.findById(id);
        if (alquiler.isPresent()) {
            alquiler.get().setEstadoAlq(0);
            alquilerService.save(alquiler.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}