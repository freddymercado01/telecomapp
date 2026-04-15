package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.*;
import com.telecom.telecom_app.service.ClienteService;
import com.telecom.telecom_app.service.ContratoService;
import com.telecom.telecom_app.service.PlanService;
import com.telecom.telecom_app.service.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/contratos")
@Tag(name = "Contratos", description = "Operaciones CRUD para contratos de telecomunicaciones")
public class ContratoController {

    private final ContratoService contratoService;
    private final ClienteService clienteService;
    private final PlanService planService;
    private final VendedorService vendedorService;

    public ContratoController(ContratoService contratoService,
                              ClienteService clienteService,
                              PlanService planService,
                              VendedorService vendedorService) {
        this.contratoService = contratoService;
        this.clienteService = clienteService;
        this.planService = planService;
        this.vendedorService = vendedorService;
    }

    @Operation(summary = "Listar todos los contratos", description = "Obtiene la lista de todos los contratos")
    @ApiResponse(responseCode = "200", description = "Lista de contratos obtenida exitosamente")
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("contratos", contratoService.listarTodos());
        return "contratos/list";
    }

    @Operation(summary = "Mostrar formulario nuevo", description = "Retorna el formulario para crear un nuevo contrato")
    @ApiResponse(responseCode = "200", description = "Formulario cargado exitosamente")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Contrato contrato = new Contrato();
        contrato.setFechaInicio(LocalDate.now());
        contrato.setEstado(EstadoContrato.ACTIVO);
        contrato.setInfraestructura(new Infraestructura());

        model.addAttribute("contrato", contrato);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("planes", planService.listarTodos());
        model.addAttribute("vendedores", vendedorService.listarTodos());
        model.addAttribute("estados", EstadoContrato.values());
        return "contratos/form";
    }

    @Operation(summary = "Guardar contrato", description = "Crea o actualiza un contrato con cliente, plan y vendedor")
    @ApiResponse(responseCode = "200", description = "Contrato guardado exitosamente")
    @PostMapping
    public String guardar(@ModelAttribute Contrato contrato,
                          @RequestParam @Parameter(description = "ID del cliente") Long clienteId,
                          @RequestParam @Parameter(description = "ID del plan") Long planId,
                          @RequestParam @Parameter(description = "ID del vendedor") Long vendedorId) {
        // Si no enviaron infraestructura, crea una vacía
        if (contrato.getInfraestructura() == null) {
            contrato.setInfraestructura(new Infraestructura());
        }

        contratoService.guardarDesdeFormulario(contrato, clienteId, planId, vendedorId);
        return "redirect:/contratos";
    }

    @Operation(summary = "Editar contrato", description = "Carga el formulario con los datos del contrato para editar")
    @ApiResponse(responseCode = "200", description = "Contrato cargado para edición")
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable @Parameter(description = "ID del contrato") Long id, Model model) {
        Contrato contrato = contratoService.obtenerPorId(id);
        if (contrato.getInfraestructura() == null) contrato.setInfraestructura(new Infraestructura());

        model.addAttribute("contrato", contrato);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("planes", planService.listarTodos());
        model.addAttribute("vendedores", vendedorService.listarTodos());
        model.addAttribute("estados", EstadoContrato.values());
        return "contratos/form";
    }

    @Operation(summary = "Eliminar contrato", description = "Elimina un contrato por su ID")
    @ApiResponse(responseCode = "200", description = "Contrato eliminado exitosamente")
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable @Parameter(description = "ID del contrato") Long id) {
        contratoService.eliminar(id);
        return "redirect:/contratos";
    }
}
