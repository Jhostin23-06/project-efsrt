package com.cibertec.projectefsrt.controllers;

import java.time.LocalDate;
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

import com.cibertec.projectefsrt.entities.Empleado;
import com.cibertec.projectefsrt.services.EmpleadoService;
import com.cibertec.projectefsrt.utilidad.DateUtil;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

	@Autowired
	EmpleadoService empleadoService;

	@PostMapping
	public String createEmpleado(@ModelAttribute Empleado empleado) {
		
		if (empleado.getFechaingEmp() == null) {
			empleado.setFechaingEmp(LocalDate.now());
        }
		
		empleadoService.createEmpleado(empleado);
		
		return "redirect:/empleados";
	}
	
	@GetMapping
	public String listarEmpleados(Model model) {
		
		List<Empleado> empleados;
		
		empleados = empleadoService.readEmpleados();
		
		model.addAttribute("empleados", empleados);
		
		return "empleados";
	}
	
	@PostMapping("/editar")
    public String editarEmpleado(@ModelAttribute Empleado empleado){
		
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
			response.put("fechaingEmp", empleado.getFechaingEmp());
		}
		return response;
	}
	
	@GetMapping("/generarCodigo")
    @ResponseBody
    public Map<String, String> generarCodigo() {
		
        String codigo = empleadoService.generarSigCodEmpleado();
        
        Map<String, String> response = new HashMap<>();
        
        response.put("codigo", codigo);
        
        return response;
    }
	
	@GetMapping("/buscar")
    public String buscarClientes(@RequestParam String query, Model model){
		
        List<Empleado> empleados = empleadoService.buscarEmpleadosActivos(query);
        
        List<Map<String, Object>> empleadosFormateados = new ArrayList<>();

        for (Empleado empleado : empleados) {
        	
            Map<String, Object> empleadoMap = new HashMap<>();
            empleadoMap.put("id", empleado.getId());
            empleadoMap.put("codEmpleado", empleado.getCodEmpleado());
            empleadoMap.put("nomEmpleado", empleado.getNomEmpleado());
            empleadoMap.put("dirEmpleado", empleado.getDirEmpleado());
            empleadoMap.put("telEmpleado", empleado.getTelEmpleado());
            empleadoMap.put("fechaingEmp", DateUtil.formatDate(empleado.getFechaingEmp()));
            
            empleadosFormateados.add(empleadoMap);
        }
        model.addAttribute("empleados", empleadosFormateados);
        return "empleados";
    }
}
