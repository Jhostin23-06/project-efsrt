package com.cibertec.projectefsrt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.cibertec.projectefsrt.entities.Cliente;
import com.cibertec.projectefsrt.entities.Empleado;
import com.cibertec.projectefsrt.services.EmpleadoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

	@Autowired
	EmpleadoService empleadoService;

	@GetMapping
	public String listarEmpleados(Model model) {
		
		List<Empleado> empleados;
		
		empleados = empleadoService.readEmpleados();
		
		System.out.println(empleados);
		
		model.addAttribute("empleados", empleados);
		
		return "empleados";
	}
	
	@PostMapping
	public String createEmpleado(@ModelAttribute Empleado empleado) {
		
		System.out.println(empleado);
		
		empleadoService.createEmpleado(empleado);
		
		return "redirect:/empleados";
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer id) {
		
		try {
			
			empleadoService.deleteEmpleado(id);
			
			return ResponseEntity.noContent().build();
		} catch(IllegalArgumentException e) {
			
			return ResponseEntity.notFound().build();
		} catch(Exception e) {
			
			return ResponseEntity.internalServerError().build();
		}
	}
	
}
