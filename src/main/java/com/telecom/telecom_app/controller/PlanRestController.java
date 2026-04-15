package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.exception.ResourceInUseException;
import com.telecom.telecom_app.model.Plan;
import com.telecom.telecom_app.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
@Tag(name = "Planes API", description = "API REST para gestionar planes de telecomunicaciones")
public class PlanRestController {

    private final PlanService planService;

    public PlanRestController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los planes", description = "Obtiene la lista completa de todos los planes de telecomunicaciones")
    @ApiResponse(responseCode = "200", description = "Lista de planes obtenida exitosamente")
    public ResponseEntity<List<Plan>> listarTodos() {
        return ResponseEntity.ok(planService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener plan por ID", description = "Busca un plan específico utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan encontrado"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    public ResponseEntity<Plan> obtenerPorId(
            @PathVariable @Parameter(description = "ID del plan a buscar") Long id) {
        try {
            return ResponseEntity.ok(planService.obtenerPorId(id));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nuevo plan", description = "Crea un nuevo plan de telecomunicaciones en la base de datos")
    @ApiResponse(responseCode = "201", description = "Plan creado exitosamente")
    public ResponseEntity<Plan> crear(@RequestBody Plan plan) {
        Plan planGuardado = planService.guardar(plan);
        return ResponseEntity.status(201).body(planGuardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar plan", description = "Actualiza los datos de un plan existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    public ResponseEntity<Plan> actualizar(
            @PathVariable @Parameter(description = "ID del plan a actualizar") Long id,
            @RequestBody Plan planActualizado) {
        try {
            return ResponseEntity.ok(planService.actualizar(id, planActualizado));
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar plan", description = "Elimina un plan de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plan eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable @Parameter(description = "ID del plan a eliminar") Long id) {
        try {
            planService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (java.util.NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (ResourceInUseException ex) {
            return ResponseEntity.status(409).build();
        }
    }
}
