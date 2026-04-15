package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.exception.ResourceInUseException;
import com.telecom.telecom_app.model.Vendedor;
import com.telecom.telecom_app.service.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
@Tag(name = "Vendedores API", description = "API REST para gestionar vendedores")
public class VendedorRestController {

    private final VendedorService vendedorService;

    public VendedorRestController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los vendedores", description = "Obtiene la lista completa de todos los vendedores registrados")
    @ApiResponse(responseCode = "200", description = "Lista de vendedores obtenida exitosamente")
    public ResponseEntity<List<Vendedor>> listarTodos() {
        return ResponseEntity.ok(vendedorService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vendedor por ID", description = "Busca un vendedor específico utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    public ResponseEntity<Vendedor> obtenerPorId(
            @PathVariable @Parameter(description = "ID del vendedor a buscar") Long id) {
        try {
            return ResponseEntity.ok(vendedorService.obtenerPorId(id));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nuevo vendedor", description = "Crea un nuevo vendedor en la base de datos")
    @ApiResponse(responseCode = "201", description = "Vendedor creado exitosamente")
    public ResponseEntity<Vendedor> crear(@RequestBody Vendedor vendedor) {
        Vendedor vendedorGuardado = vendedorService.guardar(vendedor);
        return ResponseEntity.status(201).body(vendedorGuardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar vendedor", description = "Actualiza los datos de un vendedor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    public ResponseEntity<Vendedor> actualizar(
            @PathVariable @Parameter(description = "ID del vendedor a actualizar") Long id,
            @RequestBody Vendedor vendedorActualizado) {
        try {
            return ResponseEntity.ok(vendedorService.actualizar(id, vendedorActualizado));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar vendedor", description = "Elimina un vendedor de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vendedor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable @Parameter(description = "ID del vendedor a eliminar") Long id) {
        try {
            vendedorService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (ResourceInUseException ex) {
            return ResponseEntity.status(409).build();
        }
    }
}
