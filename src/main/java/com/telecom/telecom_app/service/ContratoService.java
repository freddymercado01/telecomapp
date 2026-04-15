package com.telecom.telecom_app.service;

import com.telecom.telecom_app.model.Cliente;
import com.telecom.telecom_app.model.Contrato;
import com.telecom.telecom_app.model.Plan;
import com.telecom.telecom_app.model.Vendedor;
import com.telecom.telecom_app.repository.ContratoRepository;
import com.telecom.telecom_app.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final FacturaRepository facturaRepository;
    private final ClienteService clienteService;
    private final PlanService planService;
    private final VendedorService vendedorService;

    public ContratoService(ContratoRepository contratoRepository,
                           FacturaRepository facturaRepository,
                           ClienteService clienteService,
                           PlanService planService,
                           VendedorService vendedorService) {
        this.contratoRepository = contratoRepository;
        this.facturaRepository = facturaRepository;
        this.clienteService = clienteService;
        this.planService = planService;
        this.vendedorService = vendedorService;
    }

    public List<Contrato> listarTodos() {
        return contratoRepository.findAll();
    }

    public Contrato obtenerPorId(Long id) {
        return contratoRepository.findById(id).orElseThrow();
    }

    public Contrato crear(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    public Contrato guardarDesdeFormulario(Contrato contrato, Long clienteId, Long planId, Long vendedorId) {
        Cliente cliente = clienteService.obtenerPorId(clienteId);
        Plan plan = planService.obtenerPorId(planId);
        Vendedor vendedor = vendedorService.obtenerPorId(vendedorId);

        contrato.setCliente(cliente);
        contrato.setPlan(plan);
        contrato.setVendedor(vendedor);
        return contratoRepository.save(contrato);
    }

    public Contrato actualizar(Long id, Contrato contratoActualizado) {
        Contrato contrato = obtenerPorId(id);
        contrato.setFechaInicio(contratoActualizado.getFechaInicio());
        contrato.setFechaFin(contratoActualizado.getFechaFin());
        contrato.setEstado(contratoActualizado.getEstado());
        contrato.setCliente(contratoActualizado.getCliente());
        contrato.setPlan(contratoActualizado.getPlan());
        contrato.setVendedor(contratoActualizado.getVendedor());
        return contratoRepository.save(contrato);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new java.util.NoSuchElementException();
        }
        facturaRepository.deleteByContrato_IdContrato(id);
        contratoRepository.deleteById(id);
    }
}
