package com.telecom.telecom_app.controller;

/* CRUD BASICO DE CLIENTE*/

import com.telecom.telecom_app.model.Cliente;
import com.telecom.telecom_app.repository.ClienteRepository;
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

    private final ClienteRepository repo;

    public ClienteController(ClienteRepository repo) {
        this.repo = repo;
    }

    @Operation(summary = "Listar todos los clientes", description = "Obtiene la lista de todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", repo.findAll());
        return "clientes/list";
    }

    @Operation(summary = "Mostrar formulario nuevo", description = "Retorna el formulario para crear un nuevo cliente")
    @ApiResponse(responseCode = "200", description = "Formulario cargado exitosamente")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @Operation(summary = "Guardar cliente", description = "Crea o actualiza un cliente")
    @ApiResponse(responseCode = "200", description = "Cliente guardado exitosamente")
    @PostMapping
    public String guardar(@ModelAttribute Cliente cliente) {
        repo.save(cliente);
        return "redirect:/clientes";
    }

    @Operation(summary = "Editar cliente", description = "Carga el formulario con los datos del cliente para editar")
    @ApiResponse(responseCode = "200", description = "Cliente cargado para edición")
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable @Parameter(description = "ID del cliente") Long id, Model model) {
        model.addAttribute("cliente", repo.findById(id).orElseThrow());
        return "clientes/form";
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su ID")
    @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente")
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable @Parameter(description = "ID del cliente") Long id) {
        repo.deleteById(id);
        return "redirect:/clientes";
    }
}