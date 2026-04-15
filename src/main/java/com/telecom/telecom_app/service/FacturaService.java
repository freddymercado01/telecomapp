package com.telecom.telecom_app.service;

import com.telecom.telecom_app.model.Contrato;
import com.telecom.telecom_app.model.EstadoFactura;
import com.telecom.telecom_app.model.Factura;
import com.telecom.telecom_app.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ContratoService contratoService;

    public FacturaService(FacturaRepository facturaRepository, ContratoService contratoService) {
        this.facturaRepository = facturaRepository;
        this.contratoService = contratoService;
    }

    public List<Factura> listarTodos() {
        return facturaRepository.findAll();
    }

    public Factura obtenerPorId(Long id) {
        return facturaRepository.findById(id).orElseThrow();
    }

    public Factura crear(Factura factura) {
        return facturaRepository.save(factura);
    }

    public Factura actualizar(Long id, Factura facturaActualizada) {
        Factura factura = obtenerPorId(id);
        factura.setFechaEmision(facturaActualizada.getFechaEmision());
        factura.setTotal(facturaActualizada.getTotal());
        factura.setEstado(facturaActualizada.getEstado());
        factura.setContrato(facturaActualizada.getContrato());
        return facturaRepository.save(factura);
    }

    public Factura generar(Long contratoId) {
        Contrato contrato = contratoService.obtenerPorId(contratoId);

        Factura factura = new Factura();
        factura.setContrato(contrato);
        factura.setFechaEmision(LocalDate.now());
        factura.setTotal(contrato.getPlan().getPrecioMensual());
        factura.setEstado(EstadoFactura.PENDIENTE);

        return facturaRepository.save(factura);
    }

    public Factura cambiarEstado(Long id, EstadoFactura estado) {
        Factura factura = obtenerPorId(id);
        factura.setEstado(estado);
        return facturaRepository.save(factura);
    }

    public void eliminar(Long id) {
        if (!facturaRepository.existsById(id)) {
            throw new java.util.NoSuchElementException();
        }
        facturaRepository.deleteById(id);
    }
}
