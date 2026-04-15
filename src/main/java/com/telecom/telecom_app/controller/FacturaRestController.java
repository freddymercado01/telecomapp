package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.Factura;
import com.telecom.telecom_app.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@Tag(name = "Facturas API", description = "API REST para gestionar facturas de clientes")
public class FacturaRestController {

    private final FacturaService facturaService;

    public FacturaRestController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las facturas", description = "Obtiene la lista completa de todas las facturas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida exitosamente")
    public ResponseEntity<List<Factura>> listarTodos() {
        return ResponseEntity.ok(facturaService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener factura por ID", description = "Busca una factura específica utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura encontrada"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<Factura> obtenerPorId(
            @PathVariable @Parameter(description = "ID de la factura a buscar") Long id) {
        try {
            return ResponseEntity.ok(facturaService.obtenerPorId(id));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nueva factura", description = "Crea una nueva factura en la base de datos")
    @ApiResponse(responseCode = "201", description = "Factura creada exitosamente")
    public ResponseEntity<Factura> crear(@RequestBody Factura factura) {
        Factura facturaGuardada = facturaService.crear(factura);
        return ResponseEntity.status(201).body(facturaGuardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar factura", description = "Actualiza los datos de una factura existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<Factura> actualizar(
            @PathVariable @Parameter(description = "ID de la factura a actualizar") Long id,
            @RequestBody Factura facturaActualizada) {
        try {
            return ResponseEntity.ok(facturaService.actualizar(id, facturaActualizada));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar factura", description = "Elimina una factura de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Factura eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable @Parameter(description = "ID de la factura a eliminar") Long id) {
        try {
            facturaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
