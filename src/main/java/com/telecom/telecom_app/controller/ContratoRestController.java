package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.Contrato;
import com.telecom.telecom_app.service.ContratoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
@Tag(name = "Contratos API", description = "API REST para gestionar contratos de clientes")
public class ContratoRestController {

    private final ContratoService contratoService;

    public ContratoRestController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    @Operation(summary = "Listar todos los contratos", description = "Obtiene la lista completa de todos los contratos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de contratos obtenida exitosamente")
    public ResponseEntity<List<Contrato>> listarTodos() {
        return ResponseEntity.ok(contratoService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    @Operation(summary = "Obtener contrato por ID", description = "Busca un contrato específico utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contrato encontrado"),
            @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
    public ResponseEntity<Contrato> obtenerPorId(
            @PathVariable @Parameter(description = "ID del contrato a buscar") Long id) {
        try {
            return ResponseEntity.ok(contratoService.obtenerPorId(id));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    @Operation(summary = "Crear nuevo contrato", description = "Crea un nuevo contrato en la base de datos")
    @ApiResponse(responseCode = "201", description = "Contrato creado exitosamente")
    public ResponseEntity<Contrato> crear(@RequestBody Contrato contrato) {
        contrato.setIdContrato(null);
        Contrato contratoGuardado = contratoService.crear(contrato);
        return ResponseEntity.status(201).body(contratoGuardado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    @Operation(summary = "Actualizar contrato", description = "Actualiza los datos de un contrato existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contrato actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
    public ResponseEntity<Contrato> actualizar(
            @PathVariable @Parameter(description = "ID del contrato a actualizar") Long id,
            @RequestBody Contrato contratoActualizado) {
        try {
            return ResponseEntity.ok(contratoService.actualizar(id, contratoActualizado));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar contrato", description = "Elimina un contrato de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contrato eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Contrato no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable @Parameter(description = "ID del contrato a eliminar") Long id) {
        try {
            contratoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
