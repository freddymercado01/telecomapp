package com.telecom.telecom_app.controller;

/* CRUD BASICO DE CLIENTE*/

import com.telecom.telecom_app.model.Cliente;
import com.telecom.telecom_app.service.BarrioService;
import com.telecom.telecom_app.service.ClienteService;
import com.telecom.telecom_app.service.PlanService;
import com.telecom.telecom_app.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operaciones CRUD para clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final PlanService planService;
    private final BarrioService barrioService;
    private final VentaService ventaService;

    public ClienteController(ClienteService clienteService,
                             PlanService planService,
                             BarrioService barrioService,
                             VentaService ventaService) {
        this.clienteService = clienteService;
        this.planService = planService;
        this.barrioService = barrioService;
        this.ventaService = ventaService;
    }

    @Operation(summary = "Listar todos los clientes", description = "Obtiene la lista de todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes/list";
    }

    @Operation(summary = "Mostrar formulario nuevo", description = "Retorna el formulario para crear un nuevo cliente")
    @ApiResponse(responseCode = "200", description = "Formulario cargado exitosamente")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("barrios", barrioService.listarTodos());
        return "clientes/form";
    }

    @Operation(summary = "Guardar cliente", description = "Crea o actualiza un cliente")
    @ApiResponse(responseCode = "200", description = "Cliente guardado exitosamente")
    @PostMapping
    public String guardar(@ModelAttribute Cliente cliente,
                          @RequestParam(required = false) Long barrioId) {
        if (barrioId != null) {
            cliente.setBarrio(barrioService.obtenerPorId(barrioId));
        } else if (cliente.getIdCliente() != null) {
            Cliente existente = clienteService.obtenerPorId(cliente.getIdCliente());
            cliente.setBarrio(existente.getBarrio());
        }

        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }

    @Operation(summary = "Mostrar formulario de venta", description = "Retorna el formulario para vender un servicio de internet a un cliente nuevo")
    @ApiResponse(responseCode = "200", description = "Formulario de venta cargado exitosamente")
    @GetMapping("/vender")
    public String venta(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("planes", planService.listarActivos());
        model.addAttribute("barrios", barrioService.listarTodos());
        return "clientes/venta";
    }

    @Operation(summary = "Vender servicio", description = "Crea un cliente, valida la cobertura, asigna un plan y genera contrato y factura")
    @ApiResponse(responseCode = "200", description = "Venta procesada exitosamente")
    @PostMapping("/vender")
    public String vender(@ModelAttribute Cliente cliente,
                         @RequestParam @Parameter(description = "ID del barrio") Long barrioId,
                         @RequestParam @Parameter(description = "ID del plan") Long planId,
                         Model model) {
        try {
            ventaService.venderServicio(cliente, barrioId, planId);
            return "redirect:/contratos";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("cliente", cliente);
            model.addAttribute("planes", planService.listarActivos());
            model.addAttribute("barrios", barrioService.listarTodos());
            model.addAttribute("error", ex.getMessage());
            return "clientes/venta";
        }
    }

    @Operation(summary = "Editar cliente", description = "Carga el formulario con los datos del cliente para editar")
    @ApiResponse(responseCode = "200", description = "Cliente cargado para edición")
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable @Parameter(description = "ID del cliente") Long id, Model model) {
        model.addAttribute("cliente", clienteService.obtenerPorId(id));
        model.addAttribute("barrios", barrioService.listarTodos());
        return "clientes/form";
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su ID")
    @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente")
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable @Parameter(description = "ID del cliente") Long id) {
        clienteService.eliminar(id);
        return "redirect:/clientes";
    }
}
