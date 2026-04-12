package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.*;
import com.telecom.telecom_app.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/facturas")
@Tag(name = "Facturas", description = "Operaciones CRUD para facturas de telecomunicaciones")
public class FacturaController {

    private final FacturaRepository facturaRepo;
    private final ContratoRepository contratoRepo;

    public FacturaController(FacturaRepository facturaRepo, ContratoRepository contratoRepo) {
        this.facturaRepo = facturaRepo;
        this.contratoRepo = contratoRepo;
    }

    @Operation(summary = "Listar todas las facturas", description = "Obtiene la lista de todas las facturas")
    @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida exitosamente")
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("facturas", facturaRepo.findAll());
        model.addAttribute("contratos", contratoRepo.findAll());
        model.addAttribute("estados", EstadoFactura.values());
        return "facturas/list";
    }

    @Operation(summary = "Generar factura", description = "Genera una nueva factura para un contrato con el valor del plan")
    @ApiResponse(responseCode = "200", description = "Factura generada exitosamente")
    @PostMapping("/generar")
    public String generar(@RequestParam @Parameter(description = "ID del contrato") Long contratoId) {
        Contrato contrato = contratoRepo.findById(contratoId).orElseThrow();

        Factura f = new Factura();
        f.setContrato(contrato);
        f.setFechaEmision(LocalDate.now());
        f.setTotal(contrato.getPlan().getPrecioMensual());
        f.setEstado(EstadoFactura.PENDIENTE);

        facturaRepo.save(f);
        return "redirect:/facturas";
    }

    @Operation(summary = "Cambiar estado de factura", description = "Actualiza el estado de una factura (PENDIENTE, PAGADA, CANCELADA)")
    @ApiResponse(responseCode = "200", description = "Estado de factura actualizado exitosamente")
    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable @Parameter(description = "ID de la factura") Long id, 
                                @RequestParam @Parameter(description = "Nuevo estado de la factura") EstadoFactura estado) {
        Factura f = facturaRepo.findById(id).orElseThrow();
        f.setEstado(estado);
        facturaRepo.save(f);
        return "redirect:/facturas";
    }

    @Operation(summary = "Eliminar factura", description = "Elimina una factura por su ID")
    @ApiResponse(responseCode = "200", description = "Factura eliminada exitosamente")
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable @Parameter(description = "ID de la factura") Long id) {
        facturaRepo.deleteById(id);
        return "redirect:/facturas";
    }
}