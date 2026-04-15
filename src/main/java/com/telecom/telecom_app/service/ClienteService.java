package com.telecom.telecom_app.service;

import com.telecom.telecom_app.exception.ResourceInUseException;
import com.telecom.telecom_app.model.Cliente;
import com.telecom.telecom_app.repository.ClienteRepository;
import com.telecom.telecom_app.repository.ContratoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ContratoRepository contratoRepository;

    public ClienteService(ClienteRepository clienteRepository, ContratoRepository contratoRepository) {
        this.clienteRepository = clienteRepository;
        this.contratoRepository = contratoRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow();
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        Cliente cliente = obtenerPorId(id);
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setDireccion(clienteActualizado.getDireccion());
        cliente.setTelefono(clienteActualizado.getTelefono());
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new java.util.NoSuchElementException();
        }
        if (contratoRepository.existsByCliente_IdCliente(id)) {
            throw new ResourceInUseException("El cliente esta asociado a contratos");
        }
        clienteRepository.deleteById(id);
    }
}
