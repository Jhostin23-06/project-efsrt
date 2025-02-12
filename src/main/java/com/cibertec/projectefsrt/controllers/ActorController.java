package com.cibertec.projectefsrt.controllers;

import com.cibertec.projectefsrt.entities.Actor;
import com.cibertec.projectefsrt.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/actores")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/generarCodigo")
    @ResponseBody
    public Map<String, String> generarCodigo() {
        String codigo = actorService.generarSigCodActor();
        Map<String, String> response = new HashMap<>();
        response.put("codigo", codigo);
        return response;
    }

    @GetMapping
    public String listarActores(Model model){
        List<Actor> actores = actorService.findAllActive();
        List<Map<String, Object>> actoresMap = new ArrayList<>();

        for (Actor actor : actores) {
            Map<String, Object> actorMap = new HashMap<>();
            actorMap.put("id", actor.getId());
            actorMap.put("codActor", actor.getCodActor());
            actorMap.put("nombre", actor.getNomActor());
            actoresMap.add(actorMap);
        }
        model.addAttribute("actores", actoresMap);
        return "actores";
    }

    @PostMapping
    public String registrarActor(@ModelAttribute Actor actor){
        actor.setEstadoAct(1);
        actorService.save(actor);
        return "redirect:/actores";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Map<String, Object> obtenerActor(@PathVariable Integer id) {
        Actor actor = actorService.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (actor != null) {
            response.put("id", actor.getId());
            response.put("codActor", actor.getCodActor());
            response.put("nomActor", actor.getNomActor());
        }
        return response;
    }

    @PostMapping("/actualizar")
    public String actualizarActor(@ModelAttribute Actor actor) {
        actorService.save(actor);
        return "redirect:/actores";
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarActorLogicamente(@PathVariable Integer id) {
        Actor actor = actorService.findById(id);
        if (actor != null) {
            actor.setEstadoAct(0);
            actorService.save(actor);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public String buscarActores(@RequestParam String query, Model model) {
        List<Actor> actores = actorService.buscarActoresActivos(query);
        List<Map<String, Object>> actoresMap = new ArrayList<>();
        for (Actor actor : actores) {
            Map<String, Object> actorMap = new HashMap<>();
            actorMap.put("id", actor.getId());
            actorMap.put("codActor", actor.getCodActor());
            actorMap.put("nombre", actor.getNomActor());
            actoresMap.add(actorMap);
        }
        model.addAttribute("actores", actoresMap);
        return "actores";
    }
}
