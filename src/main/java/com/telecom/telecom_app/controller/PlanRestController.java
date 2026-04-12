package com.telecom.telecom_app.controller;

import com.telecom.telecom_app.model.Plan;
import com.telecom.telecom_app.repository.PlanRepository;
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

    private final PlanRepository planRepository;

    public PlanRestController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todos los planes", description = "Obtiene la lista completa de todos los planes de telecomunicaciones")
    @ApiResponse(responseCode = "200", description = "Lista de planes obtenida exitosamente")
    public ResponseEntity<List<Plan>> listarTodos() {
        List<Plan> planes = planRepository.findAll();
        return ResponseEntity.ok(planes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener plan por ID", description = "Busca un plan específico utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan encontrado"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    public ResponseEntity<Plan> obtenerPorId(
            @PathVariable @Parameter(description = "ID del plan a buscar") Long id) {
        return planRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo plan", description = "Crea un nuevo plan de telecomunicaciones en la base de datos")
    @ApiResponse(responseCode = "201", description = "Plan creado exitosamente")
    public ResponseEntity<Plan> crear(@RequestBody Plan plan) {
        Plan planGuardado = planRepository.save(plan);
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
        return planRepository.findById(id)
                .map(plan -> {
                    plan.setNombre(planActualizado.getNombre());
                    plan.setTipoServicio(planActualizado.getTipoServicio());
                    plan.setPrecioMensual(planActualizado.getPrecioMensual());
                    plan.setDescripcion(planActualizado.getDescripcion());
                    plan.setActivo(planActualizado.isActivo());
                    return ResponseEntity.ok(planRepository.save(plan));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar plan", description = "Elimina un plan de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plan eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable @Parameter(description = "ID del plan a eliminar") Long id) {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
