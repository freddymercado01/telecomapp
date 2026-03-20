package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.*;
import com.telecom.telecom_app.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaRepository facturaRepo;
    private final ContratoRepository contratoRepo;

    public FacturaController(FacturaRepository facturaRepo, ContratoRepository contratoRepo) {
        this.facturaRepo = facturaRepo;
        this.contratoRepo = contratoRepo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("facturas", facturaRepo.findAll());
        model.addAttribute("contratos", contratoRepo.findAll());
        model.addAttribute("estados", EstadoFactura.values());
        return "facturas/list";
    }

    // Generar factura para un contrato (total = precio mensual del plan)
    @PostMapping("/generar")
    public String generar(@RequestParam Long contratoId) {
        Contrato contrato = contratoRepo.findById(contratoId).orElseThrow();

        Factura f = new Factura();
        f.setContrato(contrato);
        f.setFechaEmision(LocalDate.now());
        f.setTotal(contrato.getPlan().getPrecioMensual());
        f.setEstado(EstadoFactura.PENDIENTE);

        facturaRepo.save(f);
        return "redirect:/facturas";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, @RequestParam EstadoFactura estado) {
        Factura f = facturaRepo.findById(id).orElseThrow();
        f.setEstado(estado);
        facturaRepo.save(f);
        return "redirect:/facturas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        facturaRepo.deleteById(id);
        return "redirect:/facturas";
    }
}