package com.cibertec.projectefsrt.controllers;

import java.util.*;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.projectefsrt.entities.Cliente;
import com.cibertec.projectefsrt.entities.Empleado;
import com.cibertec.projectefsrt.services.EmpleadoService;
import com.cibertec.projectefsrt.utilidad.DateUtil;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

	@Autowired
	EmpleadoService empleadoService;

	@GetMapping
	public String listarEmpleados(Model model) {
		
		List<Empleado> empleados;
		
		empleados = empleadoService.readEmpleados();
		
		model.addAttribute("empleados", empleados);
		
		return "empleados";
	}

	@GetMapping("/{id}")
	@ResponseBody
	public Map<String, Object> obtenerEmpleado(@PathVariable Integer id){
		Empleado empleado = empleadoService.getById(id).orElse(null);
		Map<String, Object> response = new HashMap<>();
		if (empleado != null) {
			response.put("id", empleado.getId());
			response.put("codEmpleado", empleado.getCodEmpleado());
			response.put("nomEmpleado", empleado.getNomEmpleado());
			response.put("dirEmpleado", empleado.getDirEmpleado());
			response.put("telEmpleado", empleado.getTelEmpleado());
			response.put("fechaingEmp", DateUtil.formatDate(empleado.getFechaingEmp()));
		}
		return response;
	}

	@PostMapping
	public String createEmpleado(@ModelAttribute Empleado empleado) {
		
		empleadoService.createEmpleado(empleado);
		
		return "redirect:/empleados";
	}
	
	@DeleteMapping("/eliminar/{id}")
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
	
	@GetMapping("/generarCodigo")
    @ResponseBody
    public Map<String, String> generarCodigo() {
		
        String codigo = empleadoService.generarSigCodEmpleado();
        
        Map<String, String> response = new HashMap<>();
        
        response.put("codigo", codigo);
        
        return response;
    }
}
