package com.cibertec.projectefsrt.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cibertec.projectefsrt.entities.Usuario;
import com.cibertec.projectefsrt.repositories.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
    	// Obtener el usuario autenticado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername(); // Obtener el nombre de usuario
        }
        
     // Buscar el usuario en la base de datos
        Optional<Usuario> usuarioOptional = userRepository.findByUsuario(username);
        
     // Pasar el nombre del usuario a la vista
        String nombreUsuario = usuarioOptional.map(Usuario::getNombre).orElse("Invitado");
        model.addAttribute("nombreUsuario", nombreUsuario);
        model.addAttribute("title", "Inicio");
        return "index";
    }
}
