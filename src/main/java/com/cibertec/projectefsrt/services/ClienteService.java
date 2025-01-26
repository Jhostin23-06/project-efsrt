package com.cibertec.projectefsrt.services;

import com.cibertec.projectefsrt.entities.Cliente;
import com.cibertec.projectefsrt.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(Integer id){
        return clienteRepository.findById(id);
    }

    public void deleteById(Integer id){
        clienteRepository.deleteById(id);
    }

    public List<Cliente> buscarClientes(String query){
        return clienteRepository.searchClientes(query);
    }
}
