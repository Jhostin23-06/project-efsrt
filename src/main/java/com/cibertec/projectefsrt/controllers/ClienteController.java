package com.cibertec.projectefsrt.controllers;

import com.cibertec.projectefsrt.entities.Cliente;
import com.cibertec.projectefsrt.services.ClienteService;
import com.cibertec.projectefsrt.utilidad.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

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
        String codigo = clienteService.generarSigCodCliente();
        Map<String, String> response = new HashMap<>();
        response.put("codigo", codigo);
        return response;
    }

    @GetMapping
    public String listarClientes(Model model){
        List<Cliente> clientes = clienteService.findAllActive();
        List<Map<String, Object>> clientesFormateados = new ArrayList<>();

        for (Cliente cliente : clientes) {
            Map<String, Object> clienteMap = new HashMap<>();
            clienteMap.put("id", cliente.getId());
            clienteMap.put("codCliente", cliente.getCodCliente());
            clienteMap.put("nomCliente", cliente.getNomCliente());
            clienteMap.put("dirCliente", cliente.getDirCliente());
            clienteMap.put("telCliente", cliente.getTelCliente());
            clienteMap.put("fechaingCli", DateUtil.formatDate(cliente.getFechaingCli()));
            clientesFormateados.add(clienteMap);
        }
        model.addAttribute("clientes", clientesFormateados);
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
    public Map<String, Object> obtenerCliente(@PathVariable Integer id){
        Cliente cliente = clienteService.findById(id).orElse(null);
        Map<String, Object> response = new HashMap<>();
        if (cliente != null) {
            response.put("id", cliente.getId());
            response.put("codCliente", cliente.getCodCliente());
            response.put("nomCliente", cliente.getNomCliente());
            response.put("dirCliente", cliente.getDirCliente());
            response.put("telCliente", cliente.getTelCliente());
            response.put("fechaingCli", DateUtil.formatDate(cliente.getFechaingCli()));
        }
        return response;
    }

    @PostMapping("/editar")
    public String editarCliente(@ModelAttribute Cliente cliente){
        clienteService.save(cliente);
        return "redirect:/clientes";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        // Lógica para eliminar el cliente por id
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarClienteLogicamente(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id).orElse(null);
        if (cliente != null) {
            cliente.setEstadoCli(0); // Cambia el estado a 0 para marcarlo como eliminado
            clienteService.save(cliente);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public String buscarClientes(@RequestParam String query, Model model){
        List<Cliente> clientes = clienteService.buscarClientesActivos(query);
        List<Map<String, Object>> clientesFormateados = new ArrayList<>();

        for (Cliente cliente : clientes) {
            Map<String, Object> clienteMap = new HashMap<>();
            clienteMap.put("id", cliente.getId());
            clienteMap.put("codCliente", cliente.getCodCliente());
            clienteMap.put("nomCliente", cliente.getNomCliente());
            clienteMap.put("dirCliente", cliente.getDirCliente());
            clienteMap.put("telCliente", cliente.getTelCliente());
            clienteMap.put("fechaingCli", DateUtil.formatDate(cliente.getFechaingCli()));
            clientesFormateados.add(clienteMap);
        }
        model.addAttribute("clientes", clientesFormateados);
        return "clientes";
    }

}
