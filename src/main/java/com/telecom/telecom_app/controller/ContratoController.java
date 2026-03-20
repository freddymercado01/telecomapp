package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.*;
import com.telecom.telecom_app.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/contratos")
public class ContratoController {

    private final ContratoRepository contratoRepo;
    private final ClienteRepository clienteRepo;
    private final PlanRepository planRepo;
    private final VendedorRepository vendedorRepo;

    public ContratoController(ContratoRepository contratoRepo,
                              ClienteRepository clienteRepo,
                              PlanRepository planRepo,
                              VendedorRepository vendedorRepo) {
        this.contratoRepo = contratoRepo;
        this.clienteRepo = clienteRepo;
        this.planRepo = planRepo;
        this.vendedorRepo = vendedorRepo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("contratos", contratoRepo.findAll());
        return "contratos/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Contrato contrato = new Contrato();
        contrato.setFechaInicio(LocalDate.now());
        contrato.setEstado(EstadoContrato.ACTIVO);
        contrato.setInfraestructura(new Infraestructura());

        model.addAttribute("contrato", contrato);
        model.addAttribute("clientes", clienteRepo.findAll());
        model.addAttribute("planes", planRepo.findAll());
        model.addAttribute("vendedores", vendedorRepo.findAll());
        model.addAttribute("estados", EstadoContrato.values());
        return "contratos/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Contrato contrato,
                          @RequestParam Long clienteId,
                          @RequestParam Long planId,
                          @RequestParam Long vendedorId) {

        Cliente cliente = clienteRepo.findById(clienteId).orElseThrow();
        Plan plan = planRepo.findById(planId).orElseThrow();
        Vendedor vendedor = vendedorRepo.findById(vendedorId).orElseThrow();

        contrato.setCliente(cliente);
        contrato.setPlan(plan);
        contrato.setVendedor(vendedor);

        // Si no enviaron infraestructura, crea una vacía
        if (contrato.getInfraestructura() == null) {
            contrato.setInfraestructura(new Infraestructura());
        }

        contratoRepo.save(contrato);
        return "redirect:/contratos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Contrato contrato = contratoRepo.findById(id).orElseThrow();
        if (contrato.getInfraestructura() == null) contrato.setInfraestructura(new Infraestructura());

        model.addAttribute("contrato", contrato);
        model.addAttribute("clientes", clienteRepo.findAll());
        model.addAttribute("planes", planRepo.findAll());
        model.addAttribute("vendedores", vendedorRepo.findAll());
        model.addAttribute("estados", EstadoContrato.values());
        return "contratos/form";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        contratoRepo.deleteById(id);
        return "redirect:/contratos";
    }
}