package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.*;
import com.telecom.telecom_app.service.ContratoService;
import com.telecom.telecom_app.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/facturas")
@Tag(name = "Facturas", description = "Operaciones CRUD para facturas de telecomunicaciones")
public class FacturaController {

    private final FacturaService facturaService;
    private final ContratoService contratoService;

    public FacturaController(FacturaService facturaService, ContratoService contratoService) {
        this.facturaService = facturaService;
        this.contratoService = contratoService;
    }

    @Operation(summary = "Listar todas las facturas", description = "Obtiene la lista de todas las facturas")
    @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida exitosamente")
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("facturas", facturaService.listarTodos());
        model.addAttribute("contratos", contratoService.listarTodos());
        model.addAttribute("estados", EstadoFactura.values());
        return "facturas/list";
    }

    @Operation(summary = "Generar factura", description = "Genera una nueva factura para un contrato con el valor del plan")
    @ApiResponse(responseCode = "200", description = "Factura generada exitosamente")
    @PostMapping("/generar")
    public String generar(@RequestParam @Parameter(description = "ID del contrato") Long contratoId) {
        facturaService.generar(contratoId);
        return "redirect:/facturas";
    }

    @Operation(summary = "Cambiar estado de factura", description = "Actualiza el estado de una factura (PENDIENTE, PAGADA, CANCELADA)")
    @ApiResponse(responseCode = "200", description = "Estado de factura actualizado exitosamente")
    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable @Parameter(description = "ID de la factura") Long id, 
                                @RequestParam @Parameter(description = "Nuevo estado de la factura") EstadoFactura estado) {
        facturaService.cambiarEstado(id, estado);
        return "redirect:/facturas";
    }

    @Operation(summary = "Eliminar factura", description = "Elimina una factura por su ID")
    @ApiResponse(responseCode = "200", description = "Factura eliminada exitosamente")
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable @Parameter(description = "ID de la factura") Long id) {
        facturaService.eliminar(id);
        return "redirect:/facturas";
    }
}
