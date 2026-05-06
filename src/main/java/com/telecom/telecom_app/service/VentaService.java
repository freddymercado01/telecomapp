package com.telecom.telecom_app.service;

import com.telecom.telecom_app.model.Barrio;
import com.telecom.telecom_app.model.Cliente;
import com.telecom.telecom_app.model.Contrato;
import com.telecom.telecom_app.model.EstadoContrato;
import com.telecom.telecom_app.model.Infraestructura;
import com.telecom.telecom_app.model.Plan;
import com.telecom.telecom_app.model.Vendedor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService {

    private final ClienteService clienteService;
    private final BarrioService barrioService;
    private final PlanService planService;
    private final ContratoService contratoService;
    private final FacturaService facturaService;
    private final VendedorService vendedorService;

    public VentaService(ClienteService clienteService,
                        BarrioService barrioService,
                        PlanService planService,
                        ContratoService contratoService,
                        FacturaService facturaService,
                        VendedorService vendedorService) {
        this.clienteService = clienteService;
        this.barrioService = barrioService;
        this.planService = planService;
        this.contratoService = contratoService;
        this.facturaService = facturaService;
        this.vendedorService = vendedorService;
    }

    @Transactional
    public Contrato venderServicio(Cliente cliente, Long barrioId, Long planId) {
        Barrio barrio = barrioService.obtenerPorId(barrioId);
        if (!barrio.isCobertura()) {
            throw new IllegalArgumentException("El barrio seleccionado no tiene cobertura disponible.");
        }

        cliente.setBarrio(barrio);
        Cliente clienteGuardado = clienteService.guardar(cliente);

        Plan plan = planService.obtenerPorId(planId);
        if (!plan.isActivo()) {
            throw new IllegalArgumentException("El plan seleccionado no esta activo.");
        }

        List<Vendedor> vendedores = vendedorService.listarTodos();
        if (vendedores.isEmpty()) {
            throw new IllegalArgumentException("No hay vendedores disponibles para asignar el contrato.");
        }

        Contrato contrato = new Contrato();
        contrato.setFechaInicio(LocalDate.now());
        contrato.setEstado(EstadoContrato.ACTIVO);
        contrato.setCliente(clienteGuardado);
        contrato.setPlan(plan);
        contrato.setVendedor(vendedores.get(0));
        contrato.setInfraestructura(new Infraestructura());

        Contrato contratoGuardado = contratoService.crear(contrato);
        facturaService.generar(contratoGuardado.getIdContrato());

        return contratoGuardado;
    }
}
