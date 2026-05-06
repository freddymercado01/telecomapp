package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.exception.ResourceInUseException;
import com.telecom.telecom_app.model.Barrio;
import com.telecom.telecom_app.service.BarrioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/barrios")
@Tag(name = "Barrios API", description = "API REST para gestionar barrios y validar cobertura")
public class BarrioRestController {

    private final BarrioService barrioService;

    public BarrioRestController(BarrioService barrioService) {
        this.barrioService = barrioService;
    }

    @GetMapping
    @Operation(summary = "Listar barrios", description = "Obtiene todos los barrios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de barrios obtenida exitosamente")
    public ResponseEntity<List<Barrio>> listarTodos() {
        return ResponseEntity.ok(barrioService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener barrio", description = "Busca un barrio por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Barrio encontrado"),
            @ApiResponse(responseCode = "404", description = "Barrio no encontrado")
    })
    public ResponseEntity<Barrio> obtenerPorId(@PathVariable @Parameter(description = "ID del barrio") Long id) {
        try {
            return ResponseEntity.ok(barrioService.obtenerPorId(id));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/cobertura")
    @Operation(summary = "Validar cobertura", description = "Retorna si el barrio tiene cobertura disponible")
    public ResponseEntity<Map<String, Object>> validarCobertura(@PathVariable Long id) {
        try {
            Barrio barrio = barrioService.obtenerPorId(id);
            return ResponseEntity.ok(Map.of(
                    "idBarrio", barrio.getIdBarrio(),
                    "nombre", barrio.getNombre(),
                    "cobertura", barrio.isCobertura(),
                    "descripcion", barrio.getDescripcion() == null ? "" : barrio.getDescripcion()
            ));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear barrio", description = "Crea un barrio")
    public ResponseEntity<Barrio> crear(@RequestBody Barrio barrio) {
        return ResponseEntity.status(201).body(barrioService.guardar(barrio));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar barrio", description = "Actualiza un barrio existente")
    public ResponseEntity<Barrio> actualizar(@PathVariable Long id, @RequestBody Barrio barrio) {
        try {
            return ResponseEntity.ok(barrioService.actualizar(id, barrio));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar barrio", description = "Elimina un barrio")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            barrioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (ResourceInUseException ex) {
            return ResponseEntity.status(409).build();
        }
    }
}
