package com.cibertec.projectefsrt.controllers;

import com.cibertec.projectefsrt.entities.Cliente;
import com.cibertec.projectefsrt.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model){
        List<Cliente> clientes = clienteService.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes";
    }

    @PostMapping
    public String registrarCliente(@ModelAttribute Cliente cliente){
        // Asegúrate de que el campo fechaingCli tenga un valor
        if (cliente.getFechaingCli() == null) {
            cliente.setFechaingCli(LocalDate.now()); // Asigna la fecha actual si no se proporciona una
        }
        clienteService.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Cliente obtenerCliente(@PathVariable Integer id){
        return clienteService.findById(id).orElse(null);
    }

    @PostMapping("/editar")
    public String editarCliente(@ModelAttribute Cliente cliente){
        clienteService.save(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Integer id){
        clienteService.deleteById(id);
        return "redirect:/clientes";
    }

    @GetMapping("/buscar")
    public String buscarClientes(@RequestParam String query, Model model){
        List<Cliente> clientes = clienteService.buscarClientes(query);
        model.addAttribute("clientes", clientes);
        return "clientes";
    }

}
