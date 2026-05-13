package com.telecom.telecom_app.service;

import com.telecom.telecom_app.model.Barrio;
import com.telecom.telecom_app.exception.ResourceInUseException;
import com.telecom.telecom_app.repository.BarrioRepository;
import com.telecom.telecom_app.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarrioService {

    private final BarrioRepository barrioRepository;
    private final ClienteRepository clienteRepository;

    public BarrioService(BarrioRepository barrioRepository, ClienteRepository clienteRepository) {
        this.barrioRepository = barrioRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Barrio> listarTodos() {
        return barrioRepository.findAll();
    }

    public Barrio obtenerPorId(Long id) {
        return barrioRepository.findById(id).orElseThrow();
    }

    public Barrio guardar(Barrio barrio) {
        return barrioRepository.save(barrio);
    }

    public Barrio actualizar(Long id, Barrio barrioActualizado) {
        Barrio barrio = obtenerPorId(id);
        barrio.setNombre(barrioActualizado.getNombre());
        barrio.setCobertura(barrioActualizado.isCobertura());
        barrio.setDescripcion(barrioActualizado.getDescripcion());
        return barrioRepository.save(barrio);
    }

    public void eliminar(Long id) {
        if (!barrioRepository.existsById(id)) {
            throw new java.util.NoSuchElementException();
        }
        if (clienteRepository.existsByBarrio_IdBarrio(id)) {
            throw new ResourceInUseException("El barrio esta asociado a clientes");
        }
        barrioRepository.deleteById(id);
    }
}
