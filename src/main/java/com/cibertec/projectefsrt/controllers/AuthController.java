package com.cibertec.projectefsrt.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.projectefsrt.entities.Rol;
import com.cibertec.projectefsrt.entities.Usuario;
import com.cibertec.projectefsrt.repositories.RolRepository;
import com.cibertec.projectefsrt.repositories.UserRepository;

@Controller
public class AuthController {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String username,
            @RequestParam String password) {
    	
    	Optional<Rol> optionalRol = rolRepository.findByNombre("USER");

    	Rol rol = optionalRol.orElseGet(() -> {
    	    Rol nuevoRol = new Rol();
    	    nuevoRol.setNombre("USER");
    	    return rolRepository.save(nuevoRol); // IMPORTANTE: Retornar el nuevo rol guardado
    	});

        // Crear el usuario y asignarle el rol
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setUsuario(username);
        usuario.setClave(passwordEncoder.encode(password));

        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        usuario.setRoles(roles);

        userRepository.save(usuario);
        return "redirect:/login";
    }

}
