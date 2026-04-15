package com.telecom.telecom_app.service;

import com.telecom.telecom_app.exception.ResourceInUseException;
import com.telecom.telecom_app.model.Vendedor;
import com.telecom.telecom_app.repository.ContratoRepository;
import com.telecom.telecom_app.repository.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {

    private final VendedorRepository vendedorRepository;
    private final ContratoRepository contratoRepository;

    public VendedorService(VendedorRepository vendedorRepository, ContratoRepository contratoRepository) {
        this.vendedorRepository = vendedorRepository;
        this.contratoRepository = contratoRepository;
    }

    public List<Vendedor> listarTodos() {
        return vendedorRepository.findAll();
    }

    public Vendedor obtenerPorId(Long id) {
        return vendedorRepository.findById(id).orElseThrow();
    }

    public Vendedor guardar(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public Vendedor actualizar(Long id, Vendedor vendedorActualizado) {
        Vendedor vendedor = obtenerPorId(id);
        vendedor.setNombre(vendedorActualizado.getNombre());
        vendedor.setTelefono(vendedorActualizado.getTelefono());
        vendedor.setEmail(vendedorActualizado.getEmail());
        vendedor.setUsuario(vendedorActualizado.getUsuario());
        return vendedorRepository.save(vendedor);
    }

    public void eliminar(Long id) {
        if (!vendedorRepository.existsById(id)) {
            throw new java.util.NoSuchElementException();
        }
        if (contratoRepository.existsByVendedor_IdVendedor(id)) {
            throw new ResourceInUseException("El vendedor esta asociado a contratos");
        }
        vendedorRepository.deleteById(id);
    }
}
